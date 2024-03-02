package models;

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
	 * Constructor to initialize the models.PassengerList.
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
	 * Look up a reference code and last name, then return the corresponding models.Passenger object.
	 *
	 * @param referenceCode The reference code to be looked up.
	 * @return The models.Passenger corresponding to the reference code, null if none found.
	 */
	public Passenger findByRefCode(String referenceCode) throws AllExceptions.NoMatchingRefException {
		for (Passenger p : passengerList) {
			if (p.getRefCode().equals(referenceCode)) {
				return p;
			}
		}
		throw new AllExceptions.NoMatchingRefException(referenceCode);
	}

	/**
	 * Add a new models.Passenger object to the list.
	 *
	 * @param passenger The models.Passenger to be added.
	 */
	public void addPassenger(Passenger passenger) {
		passengerList.add(passenger);
	}

	/**
	 * Remove the models.Passenger object identified by the given reference code.
	 *
	 * @param referenceCode the reference code identifying the models.Passenger to be removed.
	 */
	public void removeDetails(String referenceCode) {
		int index = findIndex(referenceCode);
		if (index != -1) {
			passengerList.remove(index);
		}
	}

	/**
	 * Look up a reference code and return the index of the corresponding models.Passenger in the list.
	 *
	 * @param referenceCode The reference code to be looked up.
	 * @return The index of the models.Passenger, -1 if not found.
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
	 * List all models.Passenger details sorted by name.
	 * @return All models.Passenger details in name order as a String.
	 */
	public String listByName() {
		Collections.sort(passengerList, Comparator.comparing(Passenger::getLastName)
				.thenComparing(Passenger::getFirstName));
		return listDetails();
	}


	/**
	 * List all models.Passenger details sorted by reference code.
	 * @return All models.Passenger details in reference code order as a String.
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
		return this.passengerList.size();
	}

	public int checkInSize() {
		int checkInSize = 0;
		for(int i = 0;i < passengerList.size();i++){
			if(passengerList.get(i).getIfCheck()){
				checkInSize++;
			}
		}
		return checkInSize;
	}
}
