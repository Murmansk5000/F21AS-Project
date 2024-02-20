import java.util.ArrayList;
import java.util.Collections;

public class PassengerList {
	// Storage for an arbitrary number of details.
	private ArrayList<Passenger> passengerList;

	/**
	 * Constructor to initialize the PassengerList.
	 */
	public PassengerList() {
		passengerList = new ArrayList<Passenger>();
	}

	/**
	 * Look up a reference code and return the corresponding Passenger object.
	 *
	 * @param referenceCode The reference code to be looked up.
	 * @return The Passenger corresponding to the reference code, null if none found.
	 */
	public Passenger findByRefCode(String referenceCode) {
		for (Passenger p : passengerList) {
			if (p.getRefCode().equals(referenceCode)) {
				return p;
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
	 * @return The number of Passenger objects currently in the list.
	 */
	public int getNumberOfEntries() {
		return passengerList.size();
	}

	/**
	 * List all Passenger details sorted by name.
	 * Note: This method assumes that Passenger class has implemented Comparable interface to compare names.
	 * @return All Passenger details in name order as a String.
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
	 * List all Passenger details sorted by reference code.
	 * @return All Passenger details in reference code order as a String.
	 */
	public String listByName() {
		// Collections.sort(passengerList, new StaffNameComparator());
		return listDetails();
	}

	/**
	 * Get the size of the passenger list.
	 *
	 * @return The size of the passenger list.
	 */
	public String listByID() {
		Collections.sort(passengerList);
		return listDetails();
	}

	public int size(){
		return this.size();
	}
}
