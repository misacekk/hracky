public class VyrobceNohou extends Thread {
    private final SkladMonitor sklad;

    public VyrobceNohou(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            sklad.pridejSoucastku("nohy", 30, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}