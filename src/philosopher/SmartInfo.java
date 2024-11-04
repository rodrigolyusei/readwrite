package philosopher;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SmartInfo {
    public final ArrayList<String> data;
    public int readers = 0;
    public final Semaphore semaphore = new Semaphore(1);

    public SmartInfo(ArrayList<String> data){
        this.data = new ArrayList<>(data);
    }

    public synchronized void acess() {
        readers++;

        try {
            if (readers == 1) {
                semaphore.acquire();
            }
        }
        catch(InterruptedException e) {}
    }

    public synchronized void leave(){
        readers--;
        if (readers == 0){
            semaphore.release();
        }
    }
}
