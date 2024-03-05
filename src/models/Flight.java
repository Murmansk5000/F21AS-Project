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
        //this.addPaxToFlt("PassengerList.txt");


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


    public boolean isOverCapacity() {
        int passengerCheckIn = 0;
        for (int i = 0; i < passengerInFlight.size(); i++) {
            if (passengerInFlight.get(i).getIfCheck()) passengerCheckIn++;
        }
        boolean passengerOver = passengerCheckIn > this.maxPassengers;
        boolean baggageOverWeight = baggageInFlight.getTotalWeight() > this.maxBaggageWeight;
        boolean baggageOverVolume = baggageInFlight.getTotalVolume() > this.maxBaggageVolume;
        return passengerOver || baggageOverWeight || baggageOverVolume;
    }

    /**
     * Adds a passenger to the flight according to his ticket..
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
    public void addBaggage(Baggage baggage) {

        baggageInFlight.addBaggage(baggage);

    }

    public void addAllPassengerBaggageToFlight() {
        // 遍历passengerInFlight中的每一个乘客
        for (Passenger passenger : passengerInFlight.getPassengers()) {
            // 假设每个乘客有一个getBaggageList()方法返回其所有行李的列表
            BaggageList passengerBaggageList = passenger.getBaggageList();

            // 然后遍历这个乘客的所有行李
            for (Baggage baggage : passengerBaggageList.getBaggageList()) {

                this.baggageInFlight.addBaggage(baggage);

            }
        }
    }


    @Override
    public int compareTo(Flight other) {
        return this.flightCode.compareTo(other.flightCode);
    }

	/*public void addPaxToFlt(String fileName){
		PassengerList all = new PassengerList();
		PassengerList paxInFlt = new PassengerList();
		all.loadPassengersFromTXT(fileName);
		for(int i =0; i< all.size();i++){
			if(all.get(i).getFlightCode() == this.flightCode){
				paxInFlt.addPassenger(all.get(i));
			}
		}
	}*/

}
