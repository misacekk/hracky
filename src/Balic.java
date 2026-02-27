public class Balic extends Thread {
    private final SkladMonitor sklad;

    public Balic(SkladMonitor s) {
        this.sklad = s;
    }

    @Override
    public void run() {
        while (!sklad.jeHotovo()) {
            sklad.zabalHracku(10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
