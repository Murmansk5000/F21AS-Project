package Stage2;

import Stage1.AllExceptions;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage1.PassengerList;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Simulation {
    private static final String PASSENGER_DATA_FILE = "passengerList.txt";
    private static final String FLIGHT_DATA_FILE = "FlightList.txt";
    private static PassengerList paxList;
    private static FlightList fltList;
    private static CheckInCounterManager counterManager;
    private ExecutorService executor;

    public Simulation() throws AllExceptions.NoMatchingFlightException {
        // Initialise passenger and flight lists
        paxList = new PassengerList();
        fltList = new FlightList();
        paxList.loadPassengersFromTXT(PASSENGER_DATA_FILE);
        fltList.loadFlightsFromTXT(FLIGHT_DATA_FILE);
        fltList.addPassengersToFlights(paxList);

        // Initialise the counter manager
        counterManager = new CheckInCounterManager(paxList);
        // Create a new thread pool
        executor = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException, AllExceptions.NumberErrorException, InterruptedException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }

    public void startSimulation() throws InterruptedException {


        Random random = new Random();

        for (Passenger passenger : paxList.getPassengers()) {
            executor.submit(() -> {
                try {
                    // Random time of arrival of simulated passengers at the airport
                    int arrivalDelay = random.nextInt(100);
                    TimeUnit.SECONDS.sleep(arrivalDelay);

                    // Assign passengers to the appropriate queue
                    //System.out.println( (passenger.isVIP() ? "VIP" : "Regular") + " passenger " + passenger.getRefCode() + " arrived and is being added to the queue.");
                    counterManager.addPassengerToQueue(passenger);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS); // Wait for all tasks to be completed
    }
}
