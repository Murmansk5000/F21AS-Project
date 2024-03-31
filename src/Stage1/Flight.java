package Stage1;

import Stage2.Observer;
import Stage2.Subject;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Flight implements Comparable<Flight>, Subject {

    private final String flightCode;
    private final String destination;
    private final String carrier;
    private final int maxPassengers;
    private final double maxBaggageVolume;
    private final double maxBaggageWeight;
    private final BaggageList baggageInFlight;
    private final PassengerList passengerInFlight;
    private final List<Observer> observers;
    Instant startTime;
    Instant takeOffInstant;
    private boolean isTakenOff;
    private boolean timePassed;


    /**
     * Construct a new models.Flight with the specified details. All provided information is used
     * to initialize the flight's state.
     *
     * @param flightCode       The unique code identifying the flight.
     * @param destination      The flight's destination.
     * @param carrier          The carrier operating the flight.
     * @param maxPassengers    The maximum number of passengers allowed on the flight.
     * @param maxBaggageVolume The maximum volume of baggage allowed on the flight.
     * @param maxBaggageWeight The maximum weight of baggage allowed on the flight.
     */
    public Flight(String flightCode, String destination, String carrier, int maxPassengers, double maxBaggageWeight, double maxBaggageVolume, int takeOffTime) {
        this.flightCode = flightCode.trim();
        this.destination = destination.trim();
        this.carrier = carrier.trim();
        this.maxPassengers = maxPassengers;
        this.maxBaggageVolume = maxBaggageVolume;
        this.maxBaggageWeight = maxBaggageWeight;
        this.isTakenOff = false;
        this.passengerInFlight = new PassengerList();
        this.baggageInFlight = new BaggageList();
        this.observers = new ArrayList<>();
        this.startTime = Instant.now();
        this.takeOffInstant = startTime.plus(Duration.ofMinutes(takeOffTime));
        this.timePassed = false;
    }

    // Getter methods
    public String getFlightCode() {
        return flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public double getMaxBaggageVolume() {
        return maxBaggageVolume;
    }

    public double getMaxBaggageWeight() {
        return maxBaggageWeight;
    }

    public Instant getTakeOffInstant() {
        return takeOffInstant;
    }

    public boolean getIsTakenOff() {
        return isTakenOff;
    }

    public BaggageList getBaggageInFlight() {
        return baggageInFlight;
    }

    public PassengerList getPassengerInFlight() {
        notifyObservers();
        return passengerInFlight;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }


    /**
     * Determines if the flight can take off based on passengers and their baggage.
     * Checks if the number of checked-in passengers is less than the maximum allowed,
     * and if the total weight and volume of baggage are within their respective limits.
     *
     * @return true if all conditions for takeoff are met (passenger count, baggage weight, and baggage volume), false otherwise.
     */
    public boolean canTakeOff() {
        boolean checkPassengers = this.passengerInFlight.checkInSize() < this.maxPassengers;
        boolean checkBaggageWeight = this.baggageInFlight.getTotalWeight() < this.maxBaggageWeight;
        boolean checkBaggageVolume = this.baggageInFlight.getTotalVolume() < this.maxBaggageVolume;
        return checkPassengers && checkBaggageWeight && checkBaggageVolume;
    }

    /**
     * Adds a passenger to the flight according to his ticket.
     *
     * @param passenger The passenger to add to the flight.
     */
    public void addPassenger(Passenger passenger) {
        this.passengerInFlight.addPassenger(passenger);
    }

    /**
     * Adds baggage to the flight if the total volume and weight do not exceed the flight's limits.
     *
     * @param baggage The baggage to add to the flight.
     */
    public void addBaggageToFlight(Baggage baggage) throws AllExceptions.NumberErrorException {
        this.baggageInFlight.addBaggage(baggage);
    }

    /**
     * Adds baggage from all passengers in the flight to the flight's baggage list.
     * Iterates over each passenger, retrieves their baggage list, and adds each baggage to the flight.
     * Renew the weight, volume and fee.
     */
    public void addAllBaggageToFlight() throws AllExceptions.NumberErrorException {
        // Iterate over each passenger in the flight
        for (Passenger passenger : this.getPassengerInFlight().getPassengers()) {
            // Assume each passenger has a method to return their baggage list
            BaggageList passengerBaggageList = passenger.getHisBaggageList();
            // Iterate over each baggage of the passenger and add it to the flight
            for (Baggage baggage : passengerBaggageList.getBaggageList()) {
                this.addBaggageToFlight(baggage);
            }
            passenger.getHisBaggageList().clear();
        }
        this.getBaggageInFlight().renewBaggageList();
    }

    /**
     * Marks take-off time has passed..
     */
    public void setTimePassed() {
        this.timePassed = true;
    }

    /**
     * Checks if take-off time has passed.
     *
     * @return true if the time has passed, false otherwise.
     */
    public boolean getTimePassed() {
        return this.timePassed;
    }

    /**
     * Marks the flight as having taken off if it hasn't already.
     */
    public void takeOff() {
        if (!this.getIsTakenOff()) {
            this.isTakenOff = true;
            //System.out.println(this.flightCode + " take off.");
        }
    }

    @Override
    public int compareTo(Flight other) {
        return 0;
    }

    /**
     * Returns a string representation of this flight, including flight code, passenger counts,
     * total baggage weight and volume, flight status (ready or overload), and total baggage fee.
     *
     * @return Formatted string containing flight details.
     */
    @Override
    public String toString() {
        return String.format("%-15s\t%-12d\t%-10d\t%-15.2f\t%-15.2f\t%-15s\t%-20.2f",
                getFlightCode(),
                getPassengerInFlight().size(),
                getPassengerInFlight().checkInSize(),
                getBaggageInFlight().getTotalWeight(),
                getBaggageInFlight().getTotalVolume(),
                canTakeOff() ? "Ready" : "Overload",
                getBaggageInFlight().getTotalFee());
    }
}
