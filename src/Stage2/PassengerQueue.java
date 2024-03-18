package Stage2;

import Stage1.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PassengerQueue implements Subject{
    private final ConcurrentLinkedQueue<Passenger> queue;
    private List<Observer> observers = new ArrayList<>();

    // 实现 Subject 接口的方法
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public PassengerQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Adds a passenger to the queue.
     *
     * @param passenger The passenger to be added.
     */
    public void enqueue(Passenger passenger) {
        queue.offer(passenger);
        notifyObservers();
    }

    /**
     * Removes and returns the passenger at the front of the queue.
     *
     * @return The passenger at the front of the queue, or null if the queue is empty.
     */
    public Passenger dequeue() {
        Passenger passenger = queue.poll();
        notifyObservers();
        return passenger;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Gets the number of passengers in the queue.
     *
     * @return The number of passengers in the queue.
     */
    public int size() {
        return queue.size();
    }

    /**
     * Simply return the internal queue object.
     */
    public ConcurrentLinkedQueue<Passenger> getQueue() {
        return queue;
    }


    public Iterator<Passenger> iterator() {
        return this.getQueue().iterator();
    }

}
