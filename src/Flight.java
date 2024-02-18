//a simple class to contain and manage Flight details
//(id, name, hours)
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
	 * Set up the contact details. All details are trimmed to remove trailing white
	 * space.
	 * 
	 * @param name        The name.
	 * @param hoursWorked The hours worked
	 */
	public Flight(String flightCode, String destination, String carrier, int maxPassengers, double maxBaggageVolume, double maxBaggageWeight) {
		this.flightCode = flightCode;
		this.destination = destination;
		this.carrier = carrier;
		this.maxPassengers = maxPassengers;
		this.maxBaggageVolume = maxBaggageVolume;
		this.maxBaggageWeight = maxBaggageWeight;
		this.baggageInFlight = new BaggageList(); // Initialize as empty list
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

	/*
	// Additional methods
	public boolean isOverCapacity() {
		return passengerInFlight.size() > maxPassengers;
	}

	public void addPassenger(Passenger passenger) {
		if (passengerInFlight.size() < maxPassengers) {
			passengerInFlight.add(passenger);
		}
	}

	public void addBaggage(Baggage baggage) {
		// Implementation would depend on how you're calculating baggage volume and
		// weight
	}
	*/

	/**
	 * Compare this Staff object against another, for the purpose of sorting. The
	 * fields are compared by id.
	 * 
	 * @param otherDetails The details to be compared against.
	 * @return a negative integer if this id comes before the parameter's id, zero
	 *         if they are equal and a positive integer if this comes after the
	 *         other.
	 */
	@Override
	public int compareTo(Flight o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
