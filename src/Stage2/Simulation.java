package Stage2;

import Stage1.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Simulation class orchestrates an airport check-in simulation, focusing on real-time passenger processing and flight management.
 * <p>
 * This class initiates the simulation by loading passengers and flight data from text files. It simulates passenger arrivals, check-in procedures, and manages flight takeoffs. Using multithreading, the simulation mirrors the dynamic and concurrent nature of airport operations. Key functionalities include simulating random passenger arrivals, assigning passengers to flights, and updating flight statuses upon takeoff.
 * <p>
 * Example of use:
 * <pre>
 * public static void main(String[] args) {
 *     Simulation simulation = new Simulation();
 *     simulation.startSimulation();
 * }
 * </pre>
 * <p>
 * This simulation aims to demonstrate concurrency concepts and the complexity of managing airport check-in systems in a real-time scenario.
 *
 * @version 1.9
 * @since 2024-03-31
 */

public class Simulation {
    private static final String PASSENGER_DATA_FILE = "file/PassengerList.txt";
    private static final String FLIGHT_DATA_FILE = "file/FlightList.txt";
    private static PassengerList paxList;
    private static FlightList fltList;
    private static CheckInCounterManager counterManager;


    /**
     * Constructs a Simulation instance by initializing passenger and flight lists from text files,
     * and associates passengers with their flights. Also initializes the check-in counter manager.
     *
     * @throws AllExceptions.NoMatchingFlightException If a passenger's flight cannot be found.
     */
    public Simulation() throws AllExceptions.NoMatchingFlightException {
        paxList = new PassengerList();
        fltList = new FlightList();
        paxList.loadPassengersFromTXT(PASSENGER_DATA_FILE);
        fltList.loadFlightsFromTXT(FLIGHT_DATA_FILE);
        fltList.addPassengersToFlights(paxList);
        counterManager = new CheckInCounterManager(fltList);
    }

    /**
     * Processes passengers in a daemon thread: assigns random baggage, adds them to the queue,
     * and logs actions. Works on a copy of the passenger list to avoid concurrency issues.
     */
    private void passengerProcessing() {
        Thread passengerThread = new Thread(() -> {
            Random random = new Random();
            // Create a copy of the passenger list to work with
            List<Passenger> passengerListCopy = new ArrayList<>(paxList.getPassengers());
            while (!passengerListCopy.isEmpty()) {
                try {
                    int randomIndex = random.nextInt(passengerListCopy.size());
                    // Get the passenger at the random index
                    Passenger passenger = passengerListCopy.get(randomIndex);
                    int arrivalDelay = random.nextInt(30) * 10;//TODO Time can be changed
                    Thread.sleep(arrivalDelay);
                    passenger.addRandomBaggage();
                    String addBaggageMsg = String.format("Passenger %s added %s.",
                            passenger.getRefCode(),
                            passenger.getHisBaggageList().toString());
                    Log.generateLog(addBaggageMsg);
                    counterManager.addPassengerToQueue(passenger);
                    Log.generateLog(String.format("Passenger %s is added into current queue.", passenger.getRefCode()));
                    // Remove the processed passenger from the list to avoid reprocessing
                    passengerListCopy.remove(randomIndex);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (AllExceptions.NumberErrorException e) {
                    throw new RuntimeException(e);
                }
            }
            Log.generateLog("All passengers have joined the queue.");
        });
        passengerThread.setDaemon(true);
        passengerThread.start();
    }

    /**
     * Monitors and logs flight takeoffs in a daemon thread. Checks flight statuses and updates them until all have taken off.
     */
    private void monitorFlightTakeoff() {
        Thread monitorThread = new Thread(() -> {
            boolean allFlightsTakenOff;
            do {
                updateFlightTakeoffStatus();
                allFlightsTakenOff = true;
                for (Flight flight : fltList.getFlightList()) {
                    if (!flight.getIsTakenOff()) {
                        allFlightsTakenOff = false;
                        break;
                    }
                }
                if (!allFlightsTakenOff) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            } while (!allFlightsTakenOff);
            counterManager.stopAllCounters();
            Log.generateLog("All flights have now taken off. Close all counters.");
        });
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    /**
     * Updates the takeoff status of flights based on the current time.
     */
    private void updateFlightTakeoffStatus() {
        Instant now = Instant.now();
        for (Flight flight : fltList.getFlightList()) {
            if (!now.isBefore(flight.getTakeOffInstant()) && !flight.getTimePassed()) {
                if (flight.canTakeOff()) {
                    flight.takeOff();
                    Log.generateLog(String.format("Flight %s has taken off.", flight.getFlightCode()));
                } else {
                    Log.generateLog(String.format("Flight %s can not take off because it's overloaded. ", flight.getFlightCode()));
                }
                flight.setTimePassed();
            }
        }
    }

    /**
     * Starts the simulation by processing passengers and monitoring flight takeoffs.
     */
    public void startSimulation() {
        passengerProcessing();
        monitorFlightTakeoff();
    }

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }
}
