package models;


public class Flight implements Comparable<Flight> {

    private String flightCode;
    private String destination;
    private String carrier;
    private int maxPassengers;
    private double maxBaggageVolume;
    private double maxBaggageWeight;
    private BaggageList baggageInFlight;
    private PassengerList passengerInFlight;

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
    public Flight(String flightCode, String destination, String carrier, int maxPassengers, double maxBaggageWeight, double maxBaggageVolume) {
        this.flightCode = flightCode.trim();
        this.destination = destination.trim();
        this.carrier = carrier.trim();
        this.maxPassengers = maxPassengers;
        this.maxBaggageVolume = maxBaggageVolume;
        this.maxBaggageWeight = maxBaggageWeight;

        this.passengerInFlight = new PassengerList();
        this.baggageInFlight = new BaggageList();
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

    public BaggageList getBaggageInFlight() {
        return baggageInFlight;
    }

    public PassengerList getPassengerInFlight() {
        return passengerInFlight;
    }


    public boolean canTakeOff() {
        boolean checkPassengers = passengerInFlight.checkInSize() < this.maxPassengers;
        boolean checkBaggageWeight = baggageInFlight.getTotalWeight() < this.maxBaggageWeight;
        boolean checkBaggageVolume = baggageInFlight.getTotalVolume() < this.maxBaggageVolume;
        return checkPassengers && checkBaggageWeight && checkBaggageVolume;
    }

    /**
     * Adds a passenger to the flight according to his ticket.
     *
     * @param passenger The passenger to add to the flight.
     */
    public void addPassenger(Passenger passenger) {
        passengerInFlight.addPassenger(passenger);
    }

    /**
     * Adds baggage to the flight if the total volume and weight do not exceed the flight's limits.
     *
     * @param baggage The baggage to add to the flight.
     */
    public void addBaggageToFlight(Baggage baggage) {
        baggageInFlight.addBaggage(baggage);
    }

    public void addAllPassengerBaggageToFlight() {
        // 遍历passengerInFlight中的每一个乘客
        for (Passenger passenger : passengerInFlight.getPassengers()) {
            // 假设每个乘客有一个getBaggageList()方法返回其所有行李的列表
            BaggageList passengerBaggageList = passenger.getBaggageList();
            // 然后遍历这个乘客的所有行李
            for (Baggage baggage : passengerBaggageList.getBaggageList()) {
                this.addBaggageToFlight(baggage);
            }
        }
    }

    @Override
    public int compareTo(Flight other) {
        return this.flightCode.compareTo(other.flightCode);
    }

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
