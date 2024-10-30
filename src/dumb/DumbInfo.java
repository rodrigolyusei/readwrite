package dumb;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class DumbInfo {
    public final ArrayList<String> data;
    public final Semaphore semaphore = new Semaphore(1);

    public DumbInfo(ArrayList<String> data) {
        this.data = new ArrayList<>(data);
    }
}
