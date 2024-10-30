package main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Info {
    public final ArrayList<String> data;

    public int readers = 0;

    public final Semaphore semaphore = new Semaphore(1);

    public Info(ArrayList<String> data){
        this.data = new ArrayList<>(data);
    }

    public synchronized void acess() {
        readers++;
        //System.out.println(readers);

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
        //System.out.println(readers);
    }

}
