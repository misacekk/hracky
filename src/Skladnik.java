import java.util.Random;

public class Skladnik extends Thread {
    private final SkladMonitor sklad;
    private final Random rand = new Random();

    public Skladnik(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            try {
                Thread.sleep(1000);

                int novehoPlastu = rand.nextInt(1000) + 1;
                int novychVlasu = rand.nextInt(100) + 1;

                System.out.println("Přivezl jsem " + novehoPlastu + "g plastu a " + novychVlasu + " sad vlasů.");
                sklad.doplnMateriál(novehoPlastu, novychVlasu);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}