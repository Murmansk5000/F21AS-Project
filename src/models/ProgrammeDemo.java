package models;

import GUI.GUI;

public class ProgrammeDemo {
	private static PassengerList paxList;
	private static FlightList fltList;
	// private PassengerListInterface interaction;
	// private PassengerListGUI gui;

	public ProgrammeDemo() {
		// Initialize empty list of passengers and flights
		// Load passengers from TXT file directly into paxList
		// Load flights from TXT file directly into fltList
		paxList = new PassengerList();
		paxList.loadPassengersFromTXT("PassengerList.txt");
		fltList = new FlightList();
		fltList.loadFlightsFromTXT("FlightList.txt");
	}


	/**
	 * Allow the user to interact with the models.Passenger list.
	 */
	public void showInterface() {
		// interaction.run();
	}

	/**
	 * Show GUI
	 *
	 */
	public void showGUI(PassengerList passengerList, FlightList flightList) {
		GUI gui = new GUI(passengerList,flightList);
		gui.FlightCheckInGUI();
		gui.setVisible(true);


	}

	public static void main(String[] args) {

		// creates demo object, with a populated models.Passenger list
		ProgrammeDemo sld = new ProgrammeDemo();

		// allow user to interact using a GUI
		sld.showGUI(paxList, fltList);

//		PassengerList updatedPassengerList = gui.getPassengerList();
//		FlightList updatedFlightList = gui.getFlightList();

		// allow user to interact with this list
		// using text interface
		sld.showInterface();

	}

}
