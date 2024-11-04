package philosopher;

import main.Thinker;
import java.util.Collections;

public class Philosophers extends Thinker {
    public Philosophers() {
        super();
    }

    public void init(int qtd_readers, SmartInfo info){
        for(int i = 0; i < qtd_readers; i++){
            Thread v = new Thread(new SmartReader(info));
            thinkers.add(v);
        }

        for(int i = 0; i < 100-qtd_readers; i++){
            Thread v = new Thread(new SmartWriter(info));
            thinkers.add(v);
        }
        Collections.shuffle(thinkers);
    }
}
