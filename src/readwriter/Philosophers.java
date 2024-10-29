package readwriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Philosophers {
    private final ArrayList<Thread> philosophers;
    private static Random rand = new Random();

    private int numReaders = 0;
    private int numWriters = 0;

    public Philosophers() {
        philosophers = new ArrayList<>();
    }

    public void initRandom(Info info){
        Random rand = new Random();

        for(int i = 0; i < 100; i++){
            Thread v;
            if (rand.nextBoolean()){
                v = new Thread(new Reader(info));
                v.setName("ReadWriter.Reader " + numReaders++);

                philosophers.add(v);
            }
            else {
                v = new Thread(new Writer(info));
                v.setName("ReadWriter.Writer " + numWriters++);

                philosophers.add(v);
            }
        }
        Collections.shuffle(philosophers);
    }

    public void init(int qtd_readers, Info info){
        for(int i = 0; i < qtd_readers; i++){
            Thread v = new Thread(new Reader(info));
            philosophers.add(v);
        }

        for(int i = 0; i < 100-qtd_readers; i++){
            Thread v = new Thread(new Writer(info));
            philosophers.add(v);
        }
        Collections.shuffle(philosophers);
    }

    public static int getNexPos(){
        return rand.nextInt(36242);
    }

    public void runAll(){
        for(Thread r : philosophers){
            r.start();
        }
    }

    public void waitAll(){
        try {
            for (Thread r : philosophers) {
                r.join();
            }
        }
        catch(InterruptedException e){
        }
    }
}
