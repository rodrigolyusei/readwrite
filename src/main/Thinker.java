package main;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Thinker {
    protected final ArrayList<Thread> thinkers;
    public Thinker(){
        thinkers = new ArrayList<>();
    }

    public void runAll(){
        for(Thread r : thinkers){
            r.start();
        }
    }

    public void waitAll(){
        try {
            for (Thread r : thinkers) {
                r.join();
            }
        }
        catch(InterruptedException _){
        }
    }

    public static int getNexPos(){
        return ThreadLocalRandom.current().nextInt(36242);
    }
}
