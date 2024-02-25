public class ProgrammeDemo {
	private PassengerList paxList;
	private FlightList fltList;
	// private PassengerListInterface interaction;
	// private PassengerListGUI gui;

	public ProgrammeDemo() {
		// Initialize empty list of passengers and flights
		// Load passengers from TXT file directly into paxList
		// Load flights from TXT file directly into fltList
		paxList = new PassengerList();
		paxList.loadPassengersFromTXT("PassengerList.txt");
		fltList = new FlightList("FlightList.txt");
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
