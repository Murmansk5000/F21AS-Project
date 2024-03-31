package Stage2;

import Stage1.Flight;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage2.GUI.GUI;

import javax.swing.*;
import java.util.Timer;
import java.util.*;

public class CheckInCounterManager implements Observer {
    private static final int OPEN_THRESHOLD = 15; // Setting the threshold for adding counters
    private static final int CLOSE_THRESHOLD = 10; //Setting the threshold for deleting counters
    private static final int MAX_VIP_COUNTER = 3;
    private static final int MAX_REGULAR_COUNTER = 5;
    private static final int MIN_VIP_COUNTER = 1;
    private static final int MIN_REGULAR_COUNTER = 1;
    private final List<CheckInCounter> counters; // all counters
    private final PassengerQueue vipQueue;
    private final PassengerQueue regularQueue;
    private final FlightList flightList;
    private final List<Observer> observers;
    private final GUI gui;

    /**
     * Constructs a CheckInCounterManager with the specified passenger and flight lists.
     * Initializes the system, creates necessary counters, and starts the queue monitoring.
     *
     * @param flightList The list of flights associated with the passengers.
     */
    public CheckInCounterManager(FlightList flightList) {
        this.counters = new LinkedList<>();
        this.vipQueue = new PassengerQueue();
        this.regularQueue = new PassengerQueue();
        this.flightList = flightList;
        this.observers = new ArrayList<>();
        this.createNewCounter(true);  // Id: 0
        this.createNewCounter(false); // Id: 1
        this.gui = new GUI(this.vipQueue, this.regularQueue, this.counters, this.flightList);
        startMonitoring();
        Log.generateLog("A check-in counter system has been crated.");
    }

    public static int getMAX_REGULAR_COUNTER() {
        return MAX_REGULAR_COUNTER;
    }

    public static int getMAX_VIP_COUNTER() {
        return MAX_VIP_COUNTER;
    }

    public static int getMIN_REGULAR_COUNTER() {
        return MIN_REGULAR_COUNTER;
    }

    public static int getMIN_VIP_COUNTER() {
        return MIN_VIP_COUNTER;
    }

    public static int getMaxCounters() {
        return MAX_REGULAR_COUNTER + MAX_VIP_COUNTER;
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
        getQueueType(passenger.isVIP()).enqueue(passenger);
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
                removeTerminatedCounters();
            }// Adjustment of the number of counters at regular intervals
        }, 0, 500); //TODO Time can be changed
    }

    /**
     * Removes terminated counter threads to prevent memory leaks and keep the list current.
     */
    private synchronized void removeTerminatedCounters() {
        counters.removeIf(counter -> !counter.isAlive());
    }

    /**
     * Periodically checks and adjusts the number of counters for both VIP and regular queues.
     */
    private synchronized void checkAndAdjustCounters() {
        adjustCountersBasedOnQueueSize(true);
        adjustCountersBasedOnQueueSize(false);
    }

    /**
     * Adjusts counters based on the current size of a specific queue.
     *
     * @param isVIP Specifies if the queue is for VIP passengers.
     */
    private void adjustCountersBasedOnQueueSize(boolean isVIP) {
        int queueSize = getQueueType(isVIP).size();
        int openCount = getOpenCount(isVIP);
        int excess = Math.max(0, queueSize - (openCount * OPEN_THRESHOLD));
        int deficit = Math.max(0, (openCount * CLOSE_THRESHOLD) - queueSize);
        int countersToAdjust = excess / OPEN_THRESHOLD - deficit / CLOSE_THRESHOLD;

        adjustCounters(countersToAdjust, isVIP);
    }

    /**
     * Modifies counter availability for VIP or regular queues to meet demand, ensuring at least one counter is open when necessary.
     * Adjustments are based on queue presence and specified adjustment needs.
     *
     * @param countersToAdjust Suggested adjustment count, may be modified to ensure service availability.
     * @param isVIP            True for VIP counters, false for regular.
     */
    private void adjustCounters(int countersToAdjust, boolean isVIP) {
        PassengerQueue targetQueue = getQueueType(isVIP);
        if (getOpenCount(isVIP) == 0 && !targetQueue.isEmpty()) {
            countersToAdjust = 1;
        }
        if (countersToAdjust != 0) {
            for (int i = 0; i < Math.abs(countersToAdjust); i++) {
                if (countersToAdjust > 0) {
                    createNewCounter(isVIP);
                }
                if (countersToAdjust < 0) {
                    closeCounter(isVIP);
                }
            }
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
            int counterId = counterToClose.getCounterId();

            String closeMsg = String.format("Close a %s counter with ID: %d.", getType(isVIP), counterId);
            Log.generateLog(closeMsg);

            String counterMsg = String.format("There are %d people in the %s queue and a total of %d counter is now open.",
                    getQueueType(isVIP).size(), getType(isVIP), getOpenCount(isVIP));

            Log.generateLog(counterMsg);

        }
    }

    /**
     * Create a VIP or regular counter if conditions allow.
     *
     * @param isVIP True to create a VIP counter, false for a regular counter.
     */
    void createNewCounter(boolean isVIP) {
        if (!canCreateCounter(isVIP)) {
            return;
        }
        int counterId = calculateCounterId(isVIP);
        CheckInCounter newCounter = new CheckInCounter(counterId, getQueueType(isVIP), isVIP, flightList);
        newCounter.registerObserver(this);
        newCounter.start();
        counters.add(newCounter);

        String openMsg = String.format("Open a new %s counter with ID: %s.", getType(isVIP), counterId);
        Log.generateLog(openMsg);

        String counterMsg = String.format("There are %d people in the %s queue and a total of %d counter is now open.",
                getQueueType(isVIP).size(), getType(isVIP), getOpenCount(isVIP));

        Log.generateLog(counterMsg);

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
     * Checks if a counter can be closed, considering all flights' statuses and minimum counter requirements.
     * If all the flight take off then all the counters can be closed and even if new passengers come in they can't check in.
     *
     * @param isVIP True for VIP counters.
     * @return True if it can be closed, false otherwise.
     */

    private boolean canCloseCounter(boolean isVIP) {
        boolean allFlightsTakenOff = true;
        for (Flight flight : flightList.getFlightList()) {
            if (!flight.getIsTakenOff()) {
                allFlightsTakenOff = false;
                break;
            }
        }

        if (allFlightsTakenOff) {
            return true;
        }

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
        int counterId;
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

    private String getType(boolean isVIP) {
        return isVIP ? "VIP" : "regular";
    }

    /**
     * Returns the appropriate passenger queue based on VIP status.
     *
     * @param isVIP True to the VIP queue, false for the regular queue.
     * @return The selected PassengerQueue, either VIP or regular.
     */
    private PassengerQueue getQueueType(boolean isVIP) {
        return isVIP ? vipQueue : regularQueue;
    }
}
