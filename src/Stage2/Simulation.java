package Stage2;

import Stage1.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
                    int arrivalDelay = random.nextInt(100) * 1;
                    Thread.sleep(arrivalDelay);
                    passenger.addRandomBaggage();
                    counterManager.addPassengerToQueue(passenger);
                    // Remove the processed passenger from the list to avoid reprocessing
                    passengerListCopy.remove(randomIndex);
                    Log.getLog("New passenger has been added to a queue.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
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
                    Log.getLog("No flight waiting.");
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
