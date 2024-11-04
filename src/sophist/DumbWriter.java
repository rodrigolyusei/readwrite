package sophist;

import main.Thinker;

public class DumbWriter implements Runnable {
    public DumbInfo info;

    public DumbWriter(DumbInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        try {
            info.semaphore.acquire();
            for(int i = 0; i < 100; i++) {
                int pos = Thinker.getNexPos();
                info.data.add(pos, "MODIFICADO");
            }
            Thread.sleep(1);
            info.semaphore.release();
        } catch(InterruptedException _) {
        }
    }
}
