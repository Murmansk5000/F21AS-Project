package Stage2;

import Stage1.*;

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

    public Simulation() throws AllExceptions.NoMatchingFlightException {
        // Initialise passenger and flight lists
        paxList = new PassengerList();
        fltList = new FlightList();
        paxList.loadPassengersFromTXT(PASSENGER_DATA_FILE);
        fltList.loadFlightsFromTXT(FLIGHT_DATA_FILE);
        fltList.addPassengersToFlights(paxList);
        // Initialise the counter manager
        counterManager = new CheckInCounterManager(paxList, fltList);
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
                    int arrivalDelay = random.nextInt(10) * 10;
                    Thread.sleep(arrivalDelay);
                    passenger.addRandomBaggage();
                    counterManager.addPassengerToQueue(passenger);
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
            } while (!allFlightsTakenOff); // 只要有航班未起飞就继续循环

            System.out.println("All flights have now taken off.");
        }).start();

    }


    private void updateFlightTakeoffStatus() {
        Instant now = Instant.now();
        for (Flight flight : fltList.getFlightList()) {
            if (!flight.getIsTakenOff() && !now.isBefore(flight.getTakeOffInstant())) {
                flight.takeOff();
                System.out.println("Flight " + flight.getFlightCode() + " has now taken off.");
            }
        }
    }


    public void startSimulation() throws AllExceptions.NumberErrorException, InterruptedException {
        passengerProcessing();
        monitorFlightTakeoff();
    }
}
