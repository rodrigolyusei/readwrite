package dumb;

import main.Info;
import readwriter.Philosophers;

public class DumbReader implements Runnable {
    public DumbInfo info;

    public DumbReader(DumbInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        try {
            info.semaphore.acquire();
            for (int i = 0; i < 100; i++) {
                    int pos = Philosophers.getNexPos();
                    String dataInfo = info.data.get(pos);
                }
            Thread.sleep(1);
            info.semaphore.release();
        }
        catch(InterruptedException _){}
    }
}

