import java.util.ArrayList;

public class FlightList {
    // Storage for an arbitrary number of Flight objects.
    private ArrayList<Flight> flights;

    /**
     * Constructor to initialize the FlightList.
     */
    public FlightList() {
        flights = new ArrayList<Flight>();
    }

    /**
     * Look up a flight code and return the corresponding Flight object.
     *
     * @param flightCode The flight code to be looked up.
     * @return The Flight object corresponding to the flight code, null if none found.
     */
    public Flight findByCode(String flightCode) {
        for (Flight f : flights) {
            if (f.getFlightCode().equals(flightCode)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Add a new Flight object to the list.
     *
     * @param flight The Flight object to be added.
     */
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * Remove a Flight object identified by the given flight code.
     *
     * @param flightCode the flight code identifying the Flight to be removed.
     */
    public void removeFlight(String flightCode) {
        int index = findIndex(flightCode);
        if (index != -1) {
            flights.remove(index);
        }
    }

    /**
     * Look up a flight code and return the index of the corresponding Flight in the list.
     *
     * @param flightCode The flight code to be looked up.
     * @return The index of the Flight, -1 if not found.
     */
    private int findIndex(String flightCode) {

        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightCode().equals(flightCode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return The number of Flight objects currently in the list.
     */
    public int size() {
        return flights.size();
    }

    /**
     * @return A String representation of all Flight details in the list.
     */
    public String listDetails() {
        StringBuilder allEntries = new StringBuilder();
        for (Flight flight : flights) {
            allEntries.append(flight.toString());
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
}
