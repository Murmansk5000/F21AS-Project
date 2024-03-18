package Stage2;

import Stage1.AllExceptions;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage1.PassengerList;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private static final String PASSENGER_DATA_FILE = "passengerList.txt";
    private static final String FLIGHT_DATA_FILE = "FlightList.txt";
    private static PassengerList paxList;
    private static FlightList fltList;
    private static CheckInCounterManager counterManager;
    private List<Thread> threads;

    public Simulation() throws AllExceptions.NoMatchingFlightException {
        // Initialise passenger and flight lists
        paxList = new PassengerList();
        fltList = new FlightList();
        paxList.loadPassengersFromTXT(PASSENGER_DATA_FILE);
        fltList.loadFlightsFromTXT(FLIGHT_DATA_FILE);
        fltList.addPassengersToFlights(paxList);

        // Initialise the counter manager
        counterManager = new CheckInCounterManager(paxList);
        threads = new ArrayList<>();
    }

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException, AllExceptions.NumberErrorException, InterruptedException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }

    public void startSimulation() throws AllExceptions.NumberErrorException, InterruptedException {
        Random random = new Random();

        for (Passenger passenger : paxList.getPassengers()) {
            Thread thread = new Thread(() -> {
                try {
                    // Random time of arrival of simulated passengers at the airport
                    int arrivalDelay = random.nextInt(100) * 1000; // convert to milliseconds
                    Thread.sleep(arrivalDelay);

                    // Assign passengers to the appropriate queue
                    passenger.addRandomBaggage();
                    counterManager.addPassengerToQueue(passenger);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (AllExceptions.NumberErrorException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
            thread.start(); // Start the thread
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
