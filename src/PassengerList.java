import AllException.NoMatchingRefException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PassengerList {
	// Storage for an arbitrary number of details.
	private ArrayList<Passenger> passengerList;

	/**
	 * Constructor to initialize the PassengerList.
	 */
	public PassengerList() {
		this.passengerList = new ArrayList<Passenger>();
		// Load passengers from TXT file directly into paxList
	}

	/**
	 * Loads passengers from a txt file.
	 * Clears existing list, then adds each passenger from the file, skipping malformed lines.
	 * @param fileName Path to the txt file.
	 */

	public void loadPassengersFromTXT(String fileName) {
		this.passengerList.clear();
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			for (String line : lines.subList(1, lines.size())) {
				String[] data = line.split(",");
				if (data.length < 5) {
					System.out.println("Skipping malformed line: " + line);
					continue;
				}
				Passenger passenger = new Passenger(data[0], data[1], data[2], data[3], Boolean.parseBoolean(data[4]));
				this.passengerList.add(passenger);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace(); // Consider more sophisticated error handling or logging
		}
	}


	/**
	 * Look up a reference code and return the corresponding Passenger object.
	 *
	 * @param referenceCode The reference code to be looked up.
	 * @return The Passenger corresponding to the reference code, null if none found.
	 */
	public Passenger findByRefCode(String referenceCode) throws NoMatchingRefException{
		for (Passenger p : passengerList) {
			if (p.getRefCode().equals(referenceCode)) {
				return p;
			}else{
				throw new NoMatchingRefException(referenceCode);
			}
		}
		return null;
	}

	/**
	 * Add a new Passenger object to the list.
	 *
	 * @param passenger The Passenger to be added.
	 */
	public void addPassenger(Passenger passenger) {
		passengerList.add(passenger);
	}

	/**
	 * Remove the Passenger object identified by the given reference code.
	 *
	 * @param referenceCode the reference code identifying the Passenger to be removed.
	 */
	public void removeDetails(String referenceCode) {
		int index = findIndex(referenceCode);
		if (index != -1) {
			passengerList.remove(index);
		}
	}

	/**
	 * Look up a reference code and return the index of the corresponding Passenger in the list.
	 *
	 * @param referenceCode The reference code to be looked up.
	 * @return The index of the Passenger, -1 if not found.
	 */
	private int findIndex(String referenceCode) {

		int size = passengerList.size();
		for (int i = 0; i < size; i++) {
			Passenger p = passengerList.get(i);
			if (p.getRefCode().equals(referenceCode)) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * Lists all passenger details as currently ordered in the passenger list.
	 *
	 * @return A String representation of all passenger details, each on a new line.
	 */
	public String listDetails() {
		StringBuffer allEntries = new StringBuffer();
		for (Passenger details : passengerList) {
			allEntries.append(details);
			allEntries.append('\n');
		}
		return allEntries.toString();
	}

	/**
	 * List all Passenger details sorted by name.
	 * @return All Passenger details in name order as a String.
	 */
	public String listByName() {
		Collections.sort(passengerList, Comparator.comparing(Passenger::getLastName)
				.thenComparing(Passenger::getFirstName));
		return listDetails();
	}


	/**
	 * List all Passenger details sorted by reference code.
	 * @return All Passenger details in reference code order as a String.
	 */
	public String listByReferenceCode() {
		Collections.sort(passengerList);
		return listDetails();
	}

	/**
	 * Get the size of the passenger list.
	 *
	 * @return The size of the passenger list.
	 */

	public int size(){
		return this.size();
	}
}
