package readwriter;

public class Writer implements Runnable {
    public Info info;
    public Writer(Info info){
        this.info = info;
    }
    @Override
    public void run() {
        //System.out.println("Filósofo " + Thread.currentThread().getName() + " iniciando.");
        try{
            synchronized (info.semaphore){
                info.semaphore.acquire();
                for(int i = 0; i < 100; i++) {
                    int pos = Philosophers.getNexPos();
                    //System.out.println("MODIFICADO");
                    info.data.add(pos, "MODIFICADO");
                }
                Thread.sleep(1);
                info.semaphore.release();
            }
        }
        catch(InterruptedException _){
        }
        //System.out.println("Filósofo " + Thread.currentThread().getName() + " terminando.");
    }
}