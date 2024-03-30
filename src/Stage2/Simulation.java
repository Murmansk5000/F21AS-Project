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
 * @version 1.7
 * @since 2024-03-29
 */

public class Simulation {
    private static final String PASSENGER_DATA_FILE = "passengerList.txt";
    private static final String FLIGHT_DATA_FILE = "FlightList.txt";
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

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException, AllExceptions.NumberErrorException, InterruptedException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }

    /**
     * Processes passengers asynchronously by simulating their arrival and check-in.
     * Each passenger is given random baggage, then added to the check-in queue.
     */
    private void passengerProcessing() {
        new Thread(() -> {
            Random random = new Random();
            // Create a copy of the passenger list to work with
            List<Passenger> passengerListCopy = new ArrayList<>(paxList.getPassengers());
            while (!passengerListCopy.isEmpty()) {
                try {
                    int randomIndex = random.nextInt(passengerListCopy.size());
                    // Get the passenger at the random index
                    Passenger passenger = passengerListCopy.get(randomIndex);
                    int arrivalDelay = random.nextInt(10) * 10;
                    Thread.sleep(arrivalDelay);
                    passenger.addRandomBaggage();
                    Log.generateLog("");//TODO
                    counterManager.addPassengerToQueue(passenger);
                    Log.generateLog("");//TODO
                    // Remove the processed passenger from the list to avoid reprocessing
                    passengerListCopy.remove(randomIndex);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (AllExceptions.NumberErrorException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * Monitors flight takeoff status asynchronously and logs when all flights have taken off.
     */
    private void monitorFlightTakeoff() {
        new Thread(() -> {
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

            Log.generateLog("All flights have now taken off.");
        }).start();

    }

    /**
     * Updates the takeoff status of flights based on the current time.
     */
    private void updateFlightTakeoffStatus() {
        Instant now = Instant.now();
        for (Flight flight : fltList.getFlightList()) {
            if (!flight.getIsTakenOff() && !now.isBefore(flight.getTakeOffInstant())) {
                flight.takeOff();
                Log.generateLog("");//TODO
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
}
