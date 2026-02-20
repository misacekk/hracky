public class Sestavitel extends Thread {
    private final SkladMonitor sklad;
    private final int idSestavitele;

    public Sestavitel(SkladMonitor s, int id) {
        this.sklad = s;
        this.idSestavitele = id;
    }

    @Override
    public void run() {

        while (sklad.zkusSestavit(idSestavitele)) {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}