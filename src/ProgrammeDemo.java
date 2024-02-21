public class ProgrammeDemo {
	private PassengerList paxList;
	private FlightList fltList;
	// private PassengerListInterface interaction;
	// private PassengerListGUI gui;

	public ProgrammeDemo() {
		// Initialize empty list of passengers and flights
		paxList = new PassengerList();
		fltList = new FlightList();

		// Load passengers from CSV file directly into paxList
		paxList.loadPassengersFromTXT("PassengerList.txt");

		// Load flights from CSV file directly into fltList
		fltList.loadFlightsFromTXT("FlightList.txt");
	}


	/**
	 * Allow the user to interact with the Passenger list.
	 */
	public void showInterface() {
		// interaction.run();
	}

	/**
	 * Show GUI
	 * 
	 * public void showGUI() { gui = new PassengerListGUI(entries);
	 * gui.setVisible(true); }
	 */

	public static void main(String[] args) {
		// creates demo object, with a populated Passenger list
		ProgrammeDemo sld = new ProgrammeDemo();

		// allow user to interact using a GUI
		// sld.showGUI();

		// allow user to interact with this list
		// using text interface
		sld.showInterface();

	}

}
