package Stage2;

import java.util.LinkedList;
import java.util.List;

public class LogQueue {
    private final List<String> queue = new LinkedList<>();

    public synchronized void enqueue(String msg) {
        queue.add(msg);
        notify();
    }

    public synchronized String dequeue() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.remove(0);
    }
}

