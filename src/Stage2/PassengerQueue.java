package Stage2;

import Stage1.Passenger;

import java.util.*;

public class PassengerQueue implements Subject {
    private final Queue<Passenger> queue;
    private final List<Observer> observers;

    public PassengerQueue() {
        queue = new LinkedList<>();
        observers = new ArrayList<>();
    }

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

    /**
     * Adds a passenger to the queue.
     *
     * @param passenger The passenger to be added.
     */
    public void enqueue(Passenger passenger) {
        synchronized (this.queue) {
            queue.add(passenger);
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
            if (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }
        Passenger passenger = queue.remove();
        notifyObservers();
        return passenger;
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