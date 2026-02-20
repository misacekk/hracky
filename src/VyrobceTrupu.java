public class VyrobceTrupu extends Thread {
    private final SkladMonitor sklad;

    public VyrobceTrupu(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            sklad.pridejSoucastku("trup", 50, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
        }
    }
}