package Stage2;

import Stage1.AllExceptions;
import Stage1.Flight;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage1.PassengerList;

import java.time.Instant;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private static final String PASSENGER_DATA_FILE = "passengerList.txt";
    private static final String FLIGHT_DATA_FILE = "FlightList.txt";
    private static PassengerList paxList;
    private static FlightList fltList;
    private static CheckInCounterManager counterManager;
    private Instant startTime; // Record the time when the programme starts running
    // private List<Thread> threads;

    public Simulation() throws AllExceptions.NoMatchingFlightException {
        // Initialise passenger and flight lists
        paxList = new PassengerList();
        fltList = new FlightList();
        paxList.loadPassengersFromTXT(PASSENGER_DATA_FILE);
        fltList.loadFlightsFromTXT(FLIGHT_DATA_FILE);
        fltList.addPassengersToFlights(paxList);

        this.startTime = Instant.now();
        // Initialise the counter manager
        counterManager = new CheckInCounterManager(paxList,fltList);
        // threads = new ArrayList<>();
    }

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException, AllExceptions.NumberErrorException, InterruptedException {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }

    private void passengerProcessing(){
        new Thread(() -> {
            // System.out.println(Thread.currentThread().getName() + " is now running.");
            Random random = new Random();
            for (Passenger passenger : paxList.getPassengers()) {
                try {
                    int arrivalDelay = random.nextInt(100) * 10; // Random time of arrival at the airport in milliseconds
                    Thread.sleep(arrivalDelay);
                    passenger.addRandomBaggage();
                    counterManager.addPassengerToQueue(passenger);
                    // System.out.println(Thread.currentThread().getName() + " is performing work.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (AllExceptions.NumberErrorException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void monitorFlightTakeoff() {
        new Thread(() -> {
            boolean allFlightsTakenOff = false;
            while (!allFlightsTakenOff) {
                updateFlightTakeoffStatus();
                // Check if all flights have departed
                allFlightsTakenOff = fltList.getFlightList().stream().allMatch(Flight::getIsTakenOff);
                if (allFlightsTakenOff) {
                    System.out.println("All flights have taken off.");
                    break;
                }
                try {
                    Thread.sleep(1000); // Check flight departure status every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }

    private void updateFlightTakeoffStatus() {
        long elapsedTimeInMinutes = Duration.between(startTime, Instant.now()).toMinutes();
        for (Flight flight : fltList.getFlightList()) {
            if (!flight.getIsTakenOff() && elapsedTimeInMinutes >= flight.getTakeOffTime()) {
                flight.fly();
                System.out.println("Flight " + flight.getFlightCode() + " has now taken off.");
            }
        }
    }

    public void startSimulation() throws AllExceptions.NumberErrorException, InterruptedException {
        passengerProcessing();
        monitorFlightTakeoff();
    }
}
