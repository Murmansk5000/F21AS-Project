package Stage2;

import Stage1.Passenger;
import Stage1.PassengerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CheckInCounterManager {
    private final int OPEN_THRESHOLD = 15; // Setting the threshold for adding counters
    private final int CLOSE_THRESHOLD = 10; //Setting the threshold for deleting counters
    private final int MAX_COUNTER = 8; // Maximum number of counters
    private final int MIN_COUNTER = 2; // Maximum number of counters
    private List<CheckInCounter> counters; // all counters
    private PassengerQueue vipQueue;
    private PassengerQueue regularQueue;

    public CheckInCounterManager(PassengerList passengerList) {
        this.counters = new ArrayList<>();
        this.vipQueue = new PassengerQueue();
        this.regularQueue = new PassengerQueue();
        this.addCounter(true);
        this.addCounter(false);
        startMonitoring();
    }

    private void startMonitoring() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkAndAdjustCounters();
            }
        }, 0, 5000); // 每5秒检查一次队列长度并调整柜台数量
    }

    private boolean counterIdExists(int counterId) {
        return counters.stream().anyMatch(counter -> counter.getCounterId() == counterId);
    }

    /**
     * Adds and starts a new counter if allowed. VIP counters have IDs as multiples of 5.
     * Regular counter IDs are unique and not multiples of 5. The method checks if a new counter can be opened,
     * calculates an appropriate ID, creates the counter, adds it to the list, and starts it.
     *
     * @param isVIP True to add a VIP counter, false for a regular counter.
     */

    public void addCounter(boolean isVIP) {
        if (canOpenNewCounter()) {
            int counterId;
            if (isVIP) {
                // Calculate the next VIP counter ID based on the number of VIP counters
                counterId = getVIPNumber() * 5;
            } else {
                // Calculate the next regular counter ID. It should not be a multiple of 5,
                // and should be higher than the highest regular counter ID.
                counterId = getRegularNumber();
                while (counterId % 5 == 0 || counterIdExists(counterId)) {
                    counterId++;
                }
            }

            // Create a new counter with the determined ID
            CheckInCounter counter = new CheckInCounter(counterId, isVIP ? vipQueue : regularQueue, isVIP);
            counters.add(counter);
            counter.start();
            System.out.println("Added and started a new " + (isVIP ? "VIP" : "Regular") + " counter with ID: " + counterId);
        }
    }

    /**
     * Removes a VIP or regular counter if conditions allow.
     * VIP counters have IDs as multiples of 5. Only removes if canCloseNewCounter() returns true.
     *
     * @param isVIP True to remove a VIP counter, false for a regular counter.
     */

    public synchronized void removeCounter(boolean isVIP) {
        if (canCloseNewCounter()) {

            CheckInCounter counterToRemove = null;
            for (int i = counters.size() - 1; i >= 0; i--) {
                CheckInCounter counter = counters.get(i);
                if ((counter.getCounterId() % 5 == 0) == isVIP) {
                    counterToRemove = counter;
                    break;
                }
            }
            if (counterToRemove != null) {
                counterToRemove.close(); //Close the counter thread
                counters.remove(counterToRemove); // Remove from list
                System.out.println("Removed a " + (isVIP ? "VIP" : "Regular") + " counter with ID: " + counterToRemove.getCounterId());
            } else {
                System.out.println("No " + (isVIP ? "VIP" : "Regular") + " counters to remove.");
            }
        }
    }


    private synchronized void checkAndAdjustCounters() {
        int numPreVIPCounterCount = vipQueue.size() / getVIPNumber();
        int numPreRegularCounterCount = regularQueue.size() / getRegularNumber();

        if (numPreVIPCounterCount >= OPEN_THRESHOLD) {
            addCounter(true);
        } else if (numPreVIPCounterCount <= CLOSE_THRESHOLD) {
            removeCounter(true);
        }

        if (numPreRegularCounterCount >= OPEN_THRESHOLD) {
            addCounter(false);
        } else if (numPreRegularCounterCount <= CLOSE_THRESHOLD) {
            removeCounter(false);
        }
        System.out.println("Regular queue: " + regularQueue.size() + ". VIP queue: " + vipQueue.size() + ".");
    }

    private boolean canOpenNewCounter() {
        return counters.size() < MAX_COUNTER;
    }

    private boolean canCloseNewCounter() {
        return counters.size() > MIN_COUNTER;
    }

    public void addPassengerToQueue(Passenger passenger) {
        if (passenger.isVIP()) {
            vipQueue.enqueue(passenger);
        } else {
            regularQueue.enqueue(passenger);
        }
    }

    public void stopAllCounters() {
        for (CheckInCounter counter : counters) {
            counter.close();
        }
        counters.clear();
    }

    public int getVIPNumber() {
        int vipCount = 0;
        for (CheckInCounter counter : counters) {
            if (counter.isVIP()) {
                vipCount++;
            }
        }
        return vipCount;
    }


    public int getRegularNumber() {
        int regularCount = 0;
        for (CheckInCounter counter : counters) {
            if (!counter.isVIP()) {
                regularCount++;
            }
        }
        return regularCount;
    }

}
