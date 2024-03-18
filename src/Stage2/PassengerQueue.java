package Stage2;

import Stage1.Passenger;

import java.util.*;
import java.util.List;

public class PassengerQueue implements Subject {
    private final Queue<Passenger> queue = new LinkedList<>();
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        synchronized (observers) {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }

    public PassengerQueue() {
    }

    /**
     * Adds a passenger to the queue.
     *
     * @param passenger The passenger to be added.
     */
    public void enqueue(Passenger passenger) {
        synchronized (this.queue) {
            queue.offer(passenger);
            notifyObservers();
        }
    }

    /**
     * Removes and returns the passenger at the front of the queue.
     *
     * @return The passenger at the front of the queue, or null if the queue is empty.
     */
    public Passenger dequeue() {
        synchronized (this.queue) {
            Passenger passenger = queue.poll();
            notifyObservers();
            return passenger;
        }
    }

    /**
     * Checks if the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        synchronized (this.queue) {
            return queue.isEmpty();
        }
    }

    /**
     * Gets the number of passengers in the queue.
     *
     * @return The number of passengers in the queue.
     */
    public int size() {
        synchronized (this.queue) {
            return queue.size();
        }
    }

    public Iterable<Passenger> getQueue() {
        synchronized (this.queue) {
            return new LinkedList<>(queue); // Return a copy to avoid concurrent modification
        }
    }
    public Iterator<Passenger> iterator() {
        return this.getQueue().iterator();
    }
}
