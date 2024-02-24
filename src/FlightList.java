import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FlightList {
    // Storage for an arbitrary number of Flight objects.
    private ArrayList<Flight> flightlist;

    /**
     * Loads flight data from a txt file.
     * @param fileName Path to the txt file.
     */
    public FlightList(String fileName) {
        flightlist = new ArrayList<Flight>();
    }


    public void loadFlightsFromTXT(String fileName) {
        this.flightlist.clear(); // Optional: clear existing flights before loading new ones
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines.subList(1, lines.size())) { // Skip title row
                String[] data = line.split(",");
                if (data.length < 6) { // Ensure there are enough data fields
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                Flight flight = new Flight(
                        data[0], // FlightCode
                        data[1], // Destination
                        data[2], // Carrier
                        Integer.parseInt(data[3]), // MaxPassengers
                        Double.parseDouble(data[4].replaceAll(" lbs", "").trim()), // MaxBaggageWeight, remove "lbs" suffix
                        Double.parseDouble(data[5].replaceAll(" cubic inches", "").trim()) // MaxBaggageVolume, remove "cubic inches" suffix
                );
                this.flightlist.add(flight);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Look up a flight code and return the corresponding Flight object.
     *
     * @param flightCode The flight code to be looked up.
     * @return The Flight object corresponding to the flight code, null if none found.
     */
    public Flight findByCode(String flightCode) {
        for (Flight f : flightlist) {
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
        flightlist.add(flight);
    }

    /**
     * Remove a Flight object identified by the given flight code.
     *
     * @param flightCode the flight code identifying the Flight to be removed.
     */
    public void removeFlight(String flightCode) {
        int index = findIndex(flightCode);
        if (index != -1) {
            flightlist.remove(index);
        }
    }

    /**
     * Look up a flight code and return the index of the corresponding Flight in the list.
     *
     * @param flightCode The flight code to be looked up.
     * @return The index of the Flight, -1 if not found.
     */
    private int findIndex(String flightCode) {

        for (int i = 0; i < flightlist.size(); i++) {
            if (flightlist.get(i).getFlightCode().equals(flightCode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return The number of Flight objects currently in the list.
     */
    public int size() {
        return flightlist.size();
    }

    /**
     * @return A String representation of all Flight details in the list.
     */
    public String listDetails() {
        StringBuilder allEntries = new StringBuilder();
        for (Flight flight : flightlist) {
            allEntries.append(flight.toString());
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
}
