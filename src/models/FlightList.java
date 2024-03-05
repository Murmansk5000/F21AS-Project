package models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FlightList {
    // Storage for an arbitrary number of models.Flight objects.
    private ArrayList<Flight> flightList;


    public FlightList() {
        flightList = new ArrayList<Flight>();
    }

    /**
     * Loads flight data from a txt file.
     *
     * @param fileName Path to the txt file.
     */
    public void loadFlightsFromTXT(String fileName) {
        this.flightList.clear(); // Optional: clear existing flights before loading new ones
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
                        Double.parseDouble(data[4].trim()), // MaxBaggageWeight
                        Double.parseDouble(data[5].trim()) // MaxBaggageVolume
                );
                this.flightList.add(flight);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Look up a flight code and return the corresponding models.Flight object.
     *
     * @param flightCode The flight code to be looked up.
     * @return The models.Flight object corresponding to the flight code, null if none found.
     */
    public Flight findByCode(String flightCode) {
        for (Flight f : flightList) {
            if (f.getFlightCode().equals(flightCode)) {
                return f;
            }
        }
        return null;//TODO 这里抛出异常
    }

    /**
     * Add a new models.Flight object to the list.
     *
     * @param flight The models.Flight object to be added.
     */
    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    /**
     * Remove a models.Flight object identified by the given flight code.
     *
     * @param flightCode the flight code identifying the models.Flight to be removed.
     */
    public void removeFlight(String flightCode) {
        int index = findIndex(flightCode);
        if (index != -1) {
            flightList.remove(index);
        }
    }

    /**
     * Look up a flight code and return the index of the corresponding models.Flight in the list.
     *
     * @param flightCode The flight code to be looked up.
     * @return The index of the models.Flight, -1 if not found.
     */
    private int findIndex(String flightCode) {

        for (int i = 0; i < flightList.size(); i++) {
            if (flightList.get(i).getFlightCode().equals(flightCode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return The number of models.Flight objects currently in the list.
     */
    public int size() {
        return this.flightList.size();
    }

    /**
     * @return A String representation of all models.Flight details in the list.
     */
    public String listDetails() {
        StringBuilder allEntries = new StringBuilder();
        for (Flight flight : flightList) {
            allEntries.append(flight.toString());
            allEntries.append('\n');
        }
        return allEntries.toString();
    }

    public Flight get(int i) {
        return flightList.get(i);
    }

    public void renewBaggageInFlight() {
        for (int i = 0; i < this.flightList.size(); i++) {
            flightList.get(i).addAllPassengerBaggageToFlight();
            flightList.get(i).getBaggageInFlight().calculateTotalWeight();
            flightList.get(i).getBaggageInFlight().calculateTotalVolume();
            flightList.get(i).isOverCapacity();
        }
    }


}
