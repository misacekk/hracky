import java.util.Random;

public class Skladnik extends Thread {
    private final SkladMonitor sklad;
    private final Random rand = new Random();

    public Skladnik(SkladMonitor s) {
        this.sklad = s;
    }
}