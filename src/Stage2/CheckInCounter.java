package Stage2;

import Stage1.AllExceptions;
import Stage1.Baggage;
import Stage1.BaggageList;
import Stage1.Passenger;

import static Stage1.GenerateData.random;

public class CheckInCounter extends Thread {
    private final int counterId;
    private final PassengerQueue queue;
    private final boolean isVIP;
    private boolean isOpen;

    /**
     * Constructs a CheckInCounter with specified ID, passenger queue, and VIP status.
     *
     * @param counterId Unique ID for the counter.
     * @param queue     Queue of passengers for this counter.
     * @param isVIP     True if it's a VIP counter, false otherwise.
     */
    public CheckInCounter(int counterId, PassengerQueue queue, boolean isVIP) {
        this.counterId = counterId;
        this.queue = queue;
        this.isVIP = isVIP;
        this.isOpen = false;
    }

    public boolean isVIP() {
        return isVIP;
    }

    @Override
    public void run() {
        while (isOpen) {
            try {
                Passenger passenger = queue.dequeue();
                if (passenger != null) {
//                    System.out.println((isVIP ? "VIP" : "Regular") + " Counter " + counterId + " is processing passenger " + passenger.getRefCode());
                    processPassenger(passenger);

                    // random time for process
                    int processTime = 5000 + random.nextInt(9000);
                    Thread.sleep(processTime);

//                    System.out.println("Passenger " + passenger.getRefCode() + " has been processed by " + (isVIP ? "VIP" : "Regular") + " Counter " + counterId);
                } else {
                    // wait for passengers
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Counter " + counterId + " interrupted.");
            } catch (AllExceptions.NumberErrorException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Processes a passenger's check-in, including baggage handling and setting their check-in status.
     * Verifies the passenger first; if verification passes, handles their baggage and marks them as checked-in.
     * If verification fails, logs a message indicating failure.
     *
     * @param passenger The passenger to be processed.
     * @throws AllExceptions.NumberErrorException If an error occurs during baggage processing.
     */

    public void processPassenger(Passenger passenger) throws AllExceptions.NumberErrorException {
//        System.out.println("Processing check-in for passenger: " + passenger.getRefCode());
        if (verifyPassenger(passenger)) {
            handleBaggage(passenger.getHisBaggageList());
            passenger.setIfCheck(true);
//            System.out.println((passenger.isVIP() ? "VIP " : "Regular ") + "passenger " +
//                    passenger.getRefCode() + " with the baggage of " + passenger.getHisBaggageList().toString() +
//                    "has successfully checked in at counter "+this.counterId+".");
        } else {
            System.out.println("Passenger verification failed for: " + passenger.getRefCode());
        }
    }

    private boolean verifyPassenger(Passenger passenger) {
        return passenger.getFlightCode() != null;
    }

    /**
     * Checks all baggage in the list.
     *
     * @param baggageList List of baggage to check.
     * @throws AllExceptions.NumberErrorException if any baggage fails the check.
     */

    private void handleBaggage(BaggageList baggageList) throws AllExceptions.NumberErrorException {
        for (Baggage baggage : baggageList.getBaggageList()) {
            baggage.checkBaggage();
        }
    }


    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public boolean getStatus() {
        return this.isOpen;
    }

    public int getCounterId() {
        return this.counterId;
    }
}
