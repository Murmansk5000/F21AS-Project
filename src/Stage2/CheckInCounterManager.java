package Stage2;

import Stage1.Flight;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage1.PassengerList;
import Stage2.GUI.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CheckInCounterManager implements Observer {
    private final int OPEN_THRESHOLD = 15; // Setting the threshold for adding counters
    private final int CLOSE_THRESHOLD = 10; //Setting the threshold for deleting counters
    private final int MAX_VIP_COUNTER = 3;
    private final int MAX_REGULAR_COUNTER = 5;
    private final int MIN_VIP_COUNTER = 1;
    private final int MIN_REGULAR_COUNTER = 1;
    private List<CheckInCounter> counters; // all counters
    private PassengerQueue vipQueue;
    private PassengerQueue regularQueue;
    private FlightList flightList;
    private Flight flight;
    private List<Observer> observers;


    private GUI gui;

    public CheckInCounterManager(PassengerList passengerList, FlightList flightList) {
        this.counters = new ArrayList<>();
        this.vipQueue = new PassengerQueue();
        this.regularQueue = new PassengerQueue();
        this.flightList = flightList;
        this.observers = new ArrayList<>();
//        this.createNewCounter(true);  // Id: 0
//        this.createNewCounter(false); // Id: 1
//        this.createNewCounter(false); // Id: 2
        this.gui = new GUI(vipQueue, regularQueue, counters, flightList);
        startMonitoring();
        Log.generateLog("A check-in counter system has been crated.");
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void update() {
        if (gui != null) {
            SwingUtilities.invokeLater(() -> {
                gui.update();
            });
        }
    }


    private void startMonitoring() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkAndAdjustCounters();
            }
        }, 0, 500); // Checks queue length every 5 seconds and adjusts the number of counters
    }

    private boolean counterIdExists(int counterId) {
        for (CheckInCounter counter : counters) {
            if (counter.getCounterId() == counterId) {
                return true;
            }
        }
        return false;
    }


    private void createNewCounter(boolean isVIP) {
        if (!canCreateCounter(isVIP)) {
//            System.out.println("Cannot create new " + (isVIP ? "VIP" : "Regular") + " counter.");
            return;
        }
        int counterId = calculateCounterId(isVIP);
        CheckInCounter newCounter = new CheckInCounter(counterId, isVIP ? vipQueue : regularQueue, isVIP, flightList);
        newCounter.registerObserver(this);
        newCounter.start();
        counters.add(newCounter);
        Log.generateLog("A " + (isVIP ? "VIP" : "regular") + "new counter with id " + +counterId + "open.");
//        System.out.println("Open and started a new " + (isVIP ? "VIP" : "Regular") + " counter with ID: " + counterId);
    }


    private int calculateCounterId(boolean isVIP) {
        int counterId;
        if (isVIP) {
            counterId = getOpenCount(isVIP) * 5;
        } else {
            counterId = getOpenCount(isVIP);
            while (counterId % 5 == 0 || counterIdExists(counterId)) {
                counterId++;
            }
        }
        return counterId;
    }


    /**
     * Removes a VIP or regular counter if conditions allow.
     * VIP counters have IDs as multiples of 5. Only removes if canCloseNewCounter() returns true.
     *
     * @param isVIP True to remove a VIP counter, false for a regular counter.
     */

    public synchronized void closeCounter(boolean isVIP) {
        if (!canCloseCounter(isVIP)) {
            // System.out.println("Cannot close " + (isVIP ? "VIP" : "Regular") + " counter.");
            return;
        }
        CheckInCounter counterToClose = null;
        for (int i = counters.size() - 1; i >= 0; i--) {
            CheckInCounter counter = counters.get(i);
            if (counter.isVIP() == isVIP && counter.getStatus()) {
                counterToClose = counter;
                break;
            }
        }
        if (counterToClose != null) {
            counterToClose.shutdown();
            System.out.println("Close a " + (isVIP ? "VIP" : "Regular") + " counter with ID: " + counterToClose.getCounterId());
        } else {
            System.out.println("No " + (isVIP ? "VIP" : "Regular") + " counters to remove.");
        }

    }

    private synchronized void checkAndAdjustCounters() {
        // Calculate the excess or deficit for VIP and regular queues.
        int vipExcess = Math.max(0, vipQueue.size() - (getOpenCount(true) * OPEN_THRESHOLD));
        int regularExcess = Math.max(0, regularQueue.size() - (getOpenCount(false) * OPEN_THRESHOLD));

        int vipDeficit = Math.max(0, (getOpenCount(true) * CLOSE_THRESHOLD) - vipQueue.size());
        int regularDeficit = Math.max(0, (getOpenCount(false) * CLOSE_THRESHOLD) - regularQueue.size());

        // Determine the number of counters to adjust based on thresholds.
        int vipCountersToAdjust = vipExcess / OPEN_THRESHOLD - vipDeficit / CLOSE_THRESHOLD;
        int regularCountersToAdjust = regularExcess / OPEN_THRESHOLD - regularDeficit / CLOSE_THRESHOLD;

        // Adjust VIP counters.
        adjustCounters(vipCountersToAdjust, true);

        // Adjust regular counters.
        adjustCounters(regularCountersToAdjust, false);

        System.out.println("Regular queue: " + regularQueue.size() + ". VIP queue: " + vipQueue.size() + ".");
    }

    private void adjustCounters(int countersToAdjust, boolean isVIP) {
        if (countersToAdjust > 0) {
            // Need more counters.
            for (int i = 0; i < countersToAdjust; i++) {
                createNewCounter(isVIP);
            }
        } else if (countersToAdjust < 0) {
            // Too many counters are open; try to close some.
            for (int i = countersToAdjust; i < 0; i++) {
                closeCounter(isVIP);
            }
        }
        if (getOpenCount(true) == 0 && !vipQueue.isEmpty()) {
            createNewCounter(true);
        }
        if (getOpenCount(false) == 0 && !regularQueue.isEmpty()) {
            createNewCounter(false);
        }
        update();
    }


    private boolean canCreateCounter(boolean isVIP) {
        if (isVIP) {
            return getOpenCount(true) < MAX_VIP_COUNTER;
        } else {
            return getOpenCount(false) < MAX_REGULAR_COUNTER;
        }
    }

    private boolean canCloseCounter(boolean isVIP) {
        if (isVIP) {
            return getOpenCount(true) > MIN_VIP_COUNTER;
        } else {
            return getOpenCount(false) > MIN_REGULAR_COUNTER;
        }
    }

    public synchronized void addPassengerToQueue(Passenger passenger) {
        if (passenger.isVIP()) {
            vipQueue.enqueue(passenger);
        } else {
            regularQueue.enqueue(passenger);
        }
    }

    public void stopAllCounters() {
        for (CheckInCounter counter : counters) {
            counter.shutdown();
        }
        counters.clear();
    }

    public int getOpenCount(boolean isVIP) {
        int count = 0;
        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVIP && counter.getStatus()) {
                count++;
            }
        }
        return count;
    }
}
