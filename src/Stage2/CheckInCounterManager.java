package Stage2;

import Stage1.FlightList;
import Stage1.Passenger;
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
    private List<Observer> observers;
    private GUI gui;

    /**
     * Constructs a CheckInCounterManager with the specified passenger and flight lists.
     * Initializes the system, creates necessary counters, and starts the queue monitoring.
     *
     * @param flightList The list of flights associated with the passengers.
     */
    public CheckInCounterManager(FlightList flightList) {
        this.counters = new ArrayList<>();
        this.vipQueue = new PassengerQueue();
        this.regularQueue = new PassengerQueue();
        this.flightList = flightList;
        this.observers = new ArrayList<>();
//        this.createNewCounter(true);  // Id: 0
//        this.createNewCounter(false); // Id: 1
//        this.createNewCounter(false); // Id: 2
        this.gui = new GUI(this.vipQueue, this.regularQueue, this.counters, this.flightList);
        startMonitoring();
        Log.generateLog("A check-in counter system has been crated.");
    }

    @Override
    public void update() {
        if (gui != null) {
            SwingUtilities.invokeLater(() -> {
                gui.update();
            });
        }
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Synchronously adds a passenger to either the VIP or regular queue based on their status.
     *
     * @param passenger the passenger to enqueue.
     */
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

    private void startMonitoring() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkAndAdjustCounters();
            }
        }, 0, 500); // Adjustment of the number of counters at regular intervals
    }

    /**
     * Periodically checks and adjusts the number of counters for both VIP and regular queues.
     */
    private synchronized void checkAndAdjustCounters() {
        adjustCountersBasedOnQueueSize(vipQueue, true);
        adjustCountersBasedOnQueueSize(regularQueue, false);
    }

    /**
     * Adjusts counters based on the current size of a specific queue.
     *
     * @param queue The queue to check.
     * @param isVIP Specifies if the queue is for VIP passengers.
     */
    private void adjustCountersBasedOnQueueSize(PassengerQueue queue, boolean isVIP) {
        int queueSize = queue.size();
        int openCount = getOpenCount(isVIP);
        int excess = Math.max(0, queueSize - (openCount * OPEN_THRESHOLD));
        int deficit = Math.max(0, (openCount * CLOSE_THRESHOLD) - queueSize);
        int countersToAdjust = excess / OPEN_THRESHOLD - deficit / CLOSE_THRESHOLD;

        adjustCounters(countersToAdjust, isVIP);
        Log.generateLog("There are " + queueSize + " people in the " + (isVIP ? "VIP" : "regular") +
                " queue, and is now open for" + openCount + "counters");//TODO Mimic the form here
    }

    /**
     * Opens or closes counters as needed based on the calculated adjustment.
     *
     * @param countersToAdjust The number of counters to adjust.
     * @param isVIP            Specifies if the adjustment is for VIP counters.
     */
    private void adjustCounters(int countersToAdjust, boolean isVIP) {
        for (int i = 0; i < Math.abs(countersToAdjust); i++) {
            if (countersToAdjust > 0) {
                createNewCounter(isVIP);
            } else {
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

    /**
     * Close a VIP or regular counter if conditions allow.
     *
     * @param isVIP True to close a VIP counter, false for a regular counter.
     */
    public synchronized void closeCounter(boolean isVIP) {
        if (!canCloseCounter(isVIP)) {
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
            Log.generateLog("Close a " + (isVIP ? "VIP" : "Regular") + " counter with ID: " + counterToClose.getCounterId() + ".");
        }

    }

    /**
     * Create a VIP or regular counter if conditions allow.
     *
     * @param isVIP True to create a VIP counter, false for a regular counter.
     */
    private void createNewCounter(boolean isVIP) {
        if (!canCreateCounter(isVIP)) {
            return;
        }
        int counterId = calculateCounterId(isVIP);
        CheckInCounter newCounter = new CheckInCounter(counterId, isVIP ? vipQueue : regularQueue, isVIP, flightList);
        newCounter.registerObserver(this);
        newCounter.start();
        counters.add(newCounter);
        Log.generateLog("Open a new " + (isVIP ? "VIP" : "regular") + " counter with ID: " + counterId + ".");
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Checks if a new counter can be opened based on VIP status and maximum counter limits.
     *
     * @param isVIP True for VIP counters, false for regular counters.
     * @return True if a new counter can be created, false otherwise.
     */
    private boolean canCreateCounter(boolean isVIP) {
        if (isVIP) {
            return getOpenCount(true) < MAX_VIP_COUNTER;
        } else {
            return getOpenCount(false) < MAX_REGULAR_COUNTER;
        }
    }

    /**
     * Determines if a counter can be closed, ensuring minimum service levels are maintained.
     *
     * @param isVIP True for VIP counters, false for regular counters.
     * @return True if a counter can be closed, false otherwise.
     */
    private boolean canCloseCounter(boolean isVIP) {
        if (isVIP) {
            return getOpenCount(true) > MIN_VIP_COUNTER;
        } else {
            return getOpenCount(false) > MIN_REGULAR_COUNTER;
        }
    }

    /**
     * Counts the number of open counters for either VIP or regular service.
     *
     * @param isVIP Specifies the type of counter to count.
     * @return The number of open counters for the specified type.
     */
    public int getOpenCount(boolean isVIP) {
        int count = 0;
        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVIP && counter.getStatus()) {
                count++;
            }
        }
        return count;
    }


    /**
     * Checks if a counter ID already exists to avoid duplicates.
     *
     * @param counterId The ID to check for existence.
     * @return True if the ID exists, false otherwise.
     */
    private boolean counterIdExists(int counterId) {
        for (CheckInCounter counter : counters) {
            if (counter.getCounterId() == counterId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates a unique ID for a new counter, avoiding conflicts and ensuring uniqueness.
     *
     * @param isVIP Specifies if the counter is for VIP service.
     * @return A unique counter ID.
     */
    private int calculateCounterId(boolean isVIP) {
        int counterId = 0;
        if (isVIP) {
            counterId = getOpenCount(true) * 5;
        } else {
            counterId = getOpenCount(false);
            while (counterId % 5 == 0 || counterIdExists(counterId)) {
                counterId++;
            }
        }
        return counterId;
    }
}
