public class VyrobceHlav extends Thread {
    private final SkladMonitor sklad;

    public VyrobceHlav(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            sklad.pridejSoucastku("hlava", 10, 2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
        }
    }
}