import java.util.Random;

public class SkladMonitor {

    private int plast = 10000;
    private int vlasy = 1000;
    private int karton = 600;

    private int pocetTrup = 0;
    private int pocetRuce = 0;
    private int pocetNohy = 0;
    private int pocetHlava = 0;

    private int vyrobenoCelkem = 0;
    private int zabalenoCelkem = 0;
    private int vadneKusy = 0;
    private final int cilovyPocet;

    private int[] statistikaSestavitelu = new int[2];

    public SkladMonitor(int cil) {
        this.cilovyPocet = cil;
    }

    public synchronized void zabalHracku(int spotrebaKartonu) {
        while (karton < spotrebaKartonu) {
            try {
                System.out.println("Nedostatek kartonu.");
                wait(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            wait(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        karton -= spotrebaKartonu;

        System.out.println("Hračka č. " + vyrobenoCelkem + " byla zabalena.");
        zabalenoCelkem++;
        notifyAll();
        try {
            wait(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Balič si dává pauzu");
    }

public synchronized void kontrolaManagera(){
        if(pocetTrup==2*pocetRuce&&pocetTrup==2*pocetHlava&&pocetTrup==2*pocetNohy){
            System.out.println("Výrobce trupu dej si vajgl pauzu jsi napřed");
        } else if (pocetHlava==2*pocetRuce&&pocetHlava==2*pocetTrup&&pocetHlava==2*pocetNohy){
            System.out.println("Výrobce hlav dej si vajgl pauzu jsi napřed");
        } else if (pocetNohy==2*pocetRuce&&pocetNohy==2*pocetTrup&&pocetNohy==2*pocetHlava){
            System.out.println("Výrobce nohou dej si vajgl pauzu jsi napřed");
        } else if (pocetRuce==2*pocetNohy&&pocetRuce==2*pocetTrup&&pocetRuce==2*pocetHlava){
            System.out.println("Výrobce rukou dej si vajgl pauzu jsi napřed");
    }
}

    public synchronized void pridejSoucastku(String typ, int spotrebaPlastu, int spotrebaVlasu) {

        while (plast < spotrebaPlastu || vlasy < spotrebaVlasu) {
            try {
                System.out.println("Nedostatek materiálu pro " + typ);
                wait(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        plast -= spotrebaPlastu;
        vlasy -= spotrebaVlasu;


        switch (typ) {
            case "trup" -> pocetTrup++;
            case "ruce" -> pocetRuce++;
            case "nohy" -> pocetNohy++;
            case "hlava" -> pocetHlava++;
        }

        System.out.println("Dělník vyrobil " + typ + ". (P:" + plast + "g, V:" + vlasy + "ks)");
        notifyAll();
        try {
            wait(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Dělník si dává pauzu");
    }

    public synchronized boolean zkusSestavit(int idSestavitele) {
        if (vyrobenoCelkem >= cilovyPocet) return false;

        while (pocetTrup < 1 || pocetRuce < 1 || pocetNohy < 1 || pocetHlava < 1) {
            if (vyrobenoCelkem >= cilovyPocet) return false;
            try {
                wait(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        pocetTrup--;
        pocetRuce--;
        pocetNohy--;
        pocetHlava--;

        if (new Random().nextInt(100) < 25) {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            vadneKusy++;
            System.out.println("Sestavitel " + idSestavitele + ": Hračka byla vadná a vyřazena.");
        } else {
            try {
                wait(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            vyrobenoCelkem++;
            statistikaSestavitelu[idSestavitele - 1]++;
            System.out.println("Hračka č. " + vyrobenoCelkem + " dokončena");
            try {
                wait(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Hračka č. " + vyrobenoCelkem + " byla zabalena.");
            zabalenoCelkem++;
            try {
                wait(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Sestavitel "+idSestavitele+" si dává pauzu");
            System.out.println("Balič si dává pauzu");
        }

        notifyAll();
        return true;
    }

    public synchronized boolean jeHotovo() {
        return vyrobenoCelkem >= cilovyPocet;
    }

    public synchronized void doplnMateriál(int p, int v) {
        this.plast += p;
        this.vlasy += v;
        notifyAll();
    }

    public void tiskStatistik() {
        System.out.println("------------------------------------------------");
        System.out.println("Vyrobeno hraček: " + vyrobenoCelkem);
        System.out.println("Vyřazeno defektů: " + vadneKusy);
        System.out.println("Sestavitel 1: " + statistikaSestavitelu[0] + " ks");
        System.out.println("Sestavitel 2: " + statistikaSestavitelu[1] + " ks");
        System.out.println("Balič celkově zabalil: "+zabalenoCelkem);
        if(statistikaSestavitelu[0] > statistikaSestavitelu[1]) {
            System.out.println("Sestavitel 2 sestavil o "+ (statistikaSestavitelu[0]-statistikaSestavitelu[1]) + " méně a tím pádem dostává varování.");
        }else{
            System.out.println("Sestavitel 1 sestavil o "+ (statistikaSestavitelu[1]-statistikaSestavitelu[0]) + " méně a tím pádem dostává varování.");
        }
    }
}