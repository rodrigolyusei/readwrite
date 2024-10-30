package dumb;

import main.Thinker;


import java.util.Collections;

public class Sophists extends Thinker {

    public Sophists(){ super(); }

    public void init(int qtd_readers, DumbInfo info){
        for(int i = 0; i < qtd_readers; i++){
            Thread v = new Thread(new DumbReader(info));
            thinkers.add(v);
        }

        for(int i = 0; i < 100-qtd_readers; i++){
            Thread v = new Thread(new DumbWriter(info));
            thinkers.add(v);
        }
        Collections.shuffle(thinkers);
    }
}
