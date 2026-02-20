import java.util.Random;

public class SkladMonitor {

    private int plast = 10000;
    private int vlasy = 1000;

    private int pocetTrup = 0;
    private int pocetRuce = 0;
    private int pocetNohy = 0;
    private int pocetHlava = 0;

    private int vyrobenoCelkem = 0;
    private int vadneKusy = 0;
    private final int cilovyPocet;

    private int[] statistikaSestavitelu = new int[2];

    public SkladMonitor(int cil) {
        this.cilovyPocet = cil;
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
    }

    public synchronized boolean zkusSestavit(int idSestavitele) {
        if (vyrobenoCelkem >= cilovyPocet) return false;

        while (pocetTrup < 1 || pocetRuce < 1 || pocetNohy < 1 || pocetHlava < 1) {
            if (vyrobenoCelkem >= cilovyPocet) return false;
            System.out.println("Sestavitel " + idSestavitele + " čeká");
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
            vadneKusy++;
            System.out.println("Sestavitel " + idSestavitele + ": Hračka byla vadná a vyřazena.");
        } else {
            vyrobenoCelkem++;
            statistikaSestavitelu[idSestavitele - 1]++;
            System.out.println("Hračka č. " + vyrobenoCelkem + " dokončena");
        }

        notifyAll();
        return true;
    }

    /*public synchronized void doplnMateriál(int p, int v) {
        this.plast += p;
        this.vlasy += v;
        notifyAll();
    }*/

    public synchronized boolean jeHotovo() {
        return vyrobenoCelkem >= cilovyPocet;
    }

    public void tiskStatistik() {
        System.out.println("------------------------------------------------");
        System.out.println("Vyrobeno hraček: " + vyrobenoCelkem);
        System.out.println("Vyřazeno defektů: " + vadneKusy);
        System.out.println("Sestavitel 1: " + statistikaSestavitelu[0] + " ks");
        System.out.println("Sestavitel 2: " + statistikaSestavitelu[1] + " ks");
    }
}