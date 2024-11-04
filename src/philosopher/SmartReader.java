package philosopher;

import main.Thinker;

public class SmartReader implements Runnable {
    public SmartInfo info;

    public SmartReader(SmartInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        try {
            info.acess();
            for(int i = 0; i < 100; i++) {
                int pos = Thinker.getNexPos();
                String dataInfo = info.data.get(pos);
            }
            Thread.sleep(1);
            info.leave();
        } catch(InterruptedException _) {
        }
    }
}
