import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Zadejte cílový počet hraček k výrobě: ");
        int cil = scanner.nextInt();

        SkladMonitor sklad = new SkladMonitor(cil);

        VyrobceTrupu vTrup = new VyrobceTrupu(sklad);
        VyrobceRukou vRuce = new VyrobceRukou(sklad);
        VyrobceNohou vNohy = new VyrobceNohou(sklad);
        VyrobceHlav vHlava = new VyrobceHlav(sklad);

        Sestavitel s1 = new Sestavitel(sklad, 1);
        Sestavitel s2 = new Sestavitel(sklad, 2);

        Skladnik skladnik = new Skladnik(sklad);

        vTrup.start();
        vRuce.start();
        vNohy.start();
        vHlava.start();
        s1.start();
        s2.start();
        skladnik.start();

        try {
            vTrup.join();
            vRuce.join();
            vNohy.join();
            vHlava.join();
            s1.join();
            s2.join();
            skladnik.join();
        } catch (InterruptedException e) {
            System.err.println("Hlavní vlákno bylo přerušeno.");
        }

        System.out.println("Výroba dokončena");
        System.out.println("Výrobce hlav vyrobil " +cil+" hlav.");
        System.out.println("Výrobce těl vyrobil " +cil+" těl.");
        System.out.println("Výrobce ruk vyrobil " +cil+" ruk.");
        System.out.println("Výrobce noh vyrobil " +cil+" noh.");

        sklad.tiskStatistik();
    }
}