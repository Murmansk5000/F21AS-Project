import AllException.OverLoadException;

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
	 * Construct a new Flight with the specified details. All provided information is used
	 * to initialize the flight's state.
	 *
	 * @param flightCode       The unique code identifying the flight.
	 * @param destination      The flight's destination.
	 * @param carrier          The carrier operating the flight.
	 * @param maxPassengers    The maximum number of passengers allowed on the flight.
	 * @param maxBaggageVolume The maximum volume of baggage allowed on the flight.
	 * @param maxBaggageWeight The maximum weight of baggage allowed on the flight.
	 */
	public Flight(String flightCode, String destination, String carrier, int maxPassengers, double maxBaggageVolume, double maxBaggageWeight) {
		this.flightCode = flightCode.trim();
		this.destination = destination.trim();
		this.carrier = carrier.trim();
		this.maxPassengers = maxPassengers;
		this.maxBaggageVolume = maxBaggageVolume;
		this.maxBaggageWeight = maxBaggageWeight;
		this.baggageInFlight = new BaggageList();
		this.passengerInFlight = new PassengerList();
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
		boolean passengerOver = passengerInFlight.size() > this.maxPassengers;
		boolean baggageOverWeight = baggageInFlight.getTotalWeight() > this.maxBaggageWeight;
		boolean baggageOverVolume = baggageInFlight.getTotalVolume() > this.maxBaggageVolume;
		return passengerOver && baggageOverWeight && baggageOverVolume;
	}

	/**
	 * Adds a passenger to the flight if it has not reached its maximum passenger capacity.
	 * @param passenger The passenger to add to the flight.
	 */
	public void addPassenger(Passenger passenger) throws OverLoadException{
		if (passengerInFlight.size() < maxPassengers) {
			passengerInFlight.addPassenger(passenger);
		} else{
			throw new OverLoadException();
		}
	}

	/**
	 * Adds baggage to the flight if the total volume and weight do not exceed the flight's limits.
	 * @param baggage The baggage to add to the flight.
	 */
	public void addBaggage(Baggage baggage) throws OverLoadException{
		if(baggageInFlight.getTotalVolume() < getMaxBaggageVolume() && baggageInFlight.getTotalWeight() < getMaxBaggageWeight()){
			baggageInFlight.addBaggage(baggage);
		}else{
			throw new OverLoadException();
		}
	}

	@Override
	public int compareTo(Flight other) {
			return this.flightCode.compareTo(other.flightCode);
	}

}
