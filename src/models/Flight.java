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
	public Flight(String flightCode, String destination, String carrier, int maxPassengers, double maxBaggageVolume, double maxBaggageWeight) {
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

	public double getMaxPassengers() {
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
	public void addBaggage(Baggage baggage) throws AllExceptions.OverloadException {
		if (baggageInFlight.getTotalVolume() < getMaxBaggageVolume() && baggageInFlight.getTotalWeight() < getMaxBaggageWeight()) {
			baggageInFlight.addBaggage(baggage);
		} else {
			throw new AllExceptions.OverloadException(flightCode);
		}
	}

	public void addAllPassengerBaggageToFlight() throws AllExceptions.OverloadException {
		// 遍历passengerInFlight中的每一个乘客
		for (Passenger passenger : passengerInFlight.getPassengers()) {
			// 假设每个乘客有一个getBaggageList()方法返回其所有行李的列表
			BaggageList passengerBaggageList = passenger.getBaggageList();

			// 然后遍历这个乘客的所有行李
			for (Baggage baggage : passengerBaggageList.getBaggageList()) {
				// 在添加每件行李之前检查是否会超过航班的最大容量
				if (this.baggageInFlight.getTotalVolume() + baggage.getVolume() <= this.maxBaggageVolume &&
						this.baggageInFlight.getTotalWeight() + baggage.getWeight() <= this.maxBaggageWeight) {
					// 如果不超过，添加行李到航班
					this.baggageInFlight.addBaggage(baggage);
				} else {
					// 如果添加这件行李会导致超过限制，抛出一个异常
					throw new AllExceptions.OverloadException("Adding this baggage would exceed the flight's baggage limits.");
				}
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
