public class VyrobceRukou extends Thread {
    private final SkladMonitor sklad;

    public VyrobceRukou(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            sklad.pridejSoucastku("ruce", 20, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
        }
    }
}