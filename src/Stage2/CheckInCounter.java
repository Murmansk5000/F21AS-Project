package Stage2;

import Stage1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckInCounter extends Thread implements Observer {
    private final int counterId;
    private final PassengerQueue queue;
    private final boolean isVIP;
    private final FlightList fltList;
    private final List<Observer> observers;
    private final String counterType;
    private Passenger currentPassenger;
    private volatile boolean running;

    /**
     * Constructs a CheckInCounter with specified ID, passenger queue, and VIP status.
     *
     * @param counterId Unique ID for the counter.
     * @param queue     Passengers queue for this counter.
     * @param isVIP     True if it's a VIP counter, false otherwise.
     */
    public CheckInCounter(int counterId, PassengerQueue queue, boolean isVIP, FlightList fltList) {
        this.counterId = counterId;
        this.queue = queue;
        this.isVIP = isVIP;
        this.fltList = fltList;
        running = true;
        observers = new ArrayList<>();
        currentPassenger = null;
        counterType = isVIP ? "VIP" : "regular";
    }

    /**
     * The main run method for the CheckInCounter thread. Continuously processes passengers from the queue,
     * handling their check-ins and sleeping when no passengers are present.
     */
    @Override
    public synchronized void run() {
        Random random = new Random();
        while (running) {
            try {
                // Synchronized block to ensure thread-safe access to the queue
                synchronized (queue) {
                    if (!queue.isEmpty()) {
                        currentPassenger = queue.dequeue(); // dequeue the next currentPassenger
                    } else {
                        currentPassenger = null;
                    }
                }
                synchronized (this) {
                    int processTime = 1000; //TODO Time can be changed
                    if (currentPassenger != null) {
                        String startMsg = String.format("Passenger %s will be processed by %s Counter %d.",
                                currentPassenger.getRefCode(), counterType, counterId);
                        Log.generateLog(startMsg);
                        processPassenger(currentPassenger);
                        // random time for process
                        Thread.sleep(processTime + random.nextInt(1000));
                    } else {
                        // wait for same time
                        Thread.sleep(processTime);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Counter " + counterId + " interrupted.");
                Thread.currentThread().interrupt();
            } catch (AllExceptions.NumberErrorException | AllExceptions.NoMatchingFlightException |
                     AllExceptions.NoMatchingRefException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update() {
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

    /**
     * Checks if this counter is VIP counter.
     *
     * @return True if this is a VIP counter, false otherwise.
     */
    public boolean isVIP() {
        return isVIP;
    }

    /**
     * Stops the counter.
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Processes a passenger's check-in by verifying eligibility, checking flight status, handling baggage, and updating check-in status.
     * Notifies observers upon successful check-in. Returns false if verification fails, flight is not found, or has already taken off.
     *
     * @param passenger The passenger to process.
     * @return True if the passenger is successfully checked in, false otherwise.
     * @throws AllExceptions.NumberErrorException      If an error occurs during baggage processing.
     * @throws AllExceptions.NoMatchingFlightException If the passenger's flight code does not match any flight.
     * @throws AllExceptions.NoMatchingRefException    If the passenger's reference code does not match.
     */

    public synchronized boolean processPassenger(Passenger passenger) throws AllExceptions.NumberErrorException, AllExceptions.NoMatchingFlightException, AllExceptions.NoMatchingRefException {
        if (!verifyPassenger(passenger)) {
            Log.generateLog(String.format("Passenger %s verification failed.", passenger.getRefCode()));
            return false;
        }

        String flightCode = passenger.getFlightCode();
        Flight flight = fltList.findByCode(flightCode);
        if (flight == null || flight.getIsTakenOff()) {
            Log.generateLog(String.format("Cannot check-in passenger %s: Flight has already taken off or flight info not found.", passenger.getRefCode()));
            return false;
        }

        Passenger passengerInFlight = flight.getPassengerInFlight().findByRefCode(passenger.getRefCode());
        handleBaggage(passenger.getHisBaggageList());
        Log.generateLog(passenger.pay());

        for (Baggage baggage : passenger.getHisBaggageList().getBaggageList()) {
            flight.getBaggageInFlight().addBaggage(baggage);
        }
        Log.generateLog(String.format("The baggage of Passenger %s has been placed on flight %s", passenger.getRefCode(), passenger.getFlightCode()));

        passengerInFlight.checkIn();
        Log.generateLog(String.format("Passenger %s has successfully checked in at counter %d.", passenger.getRefCode(), this.counterId));

        notifyObservers();
        return true;
    }

    /**
     * Verifies if a passenger has a valid reference code, indicating they are eligible for check-in.
     *
     * @param passenger The passenger to verify.
     * @return True if the passenger has a correct reference code, false if the reference code is null.
     */
    private synchronized boolean verifyPassenger(Passenger passenger) {
        return passenger.getRefCode() != null;
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

    public boolean getStatus() {
        return this.running;
    }

    public int getCounterId() {
        return this.counterId;
    }

    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }
}
