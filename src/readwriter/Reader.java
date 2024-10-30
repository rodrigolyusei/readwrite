package readwriter;

import main.Info;

public class Reader implements Runnable {
    public Info info;

    public Reader(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        try {
            info.acess();
            //System.out.println("Filósofo " + Thread.currentThread().getName() + " iniciando.");
            for (int i = 0; i < 100; i++) {
                int pos = Philosophers.getNexPos();
                String dataInfo = info.data.get(pos);
            }
            Thread.sleep(1);
            info.leave();
            //System.out.println("Filósofo " + Thread.currentThread().getName() + " terminando.");
        } catch (InterruptedException _) {
        }
    }
}
