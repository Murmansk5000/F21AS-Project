package models;

import GUI.ShowGUI;

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
	 * Allow the user to interact with the models.Passenger list.
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
	public void showGUI(PassengerList passengerList, Baggage checkBaggage, BaggageList calculateTotalfee) {
		ShowGUI gui = new ShowGUI(passengerList,checkBaggage,calculateTotalfee);
		gui.FlightCheckInGUI();
		gui.setVisible(true);
	}

	public static void main(String[] args) {
		// Create a new PassengerList object
		PassengerList passengerList = new PassengerList();
		// Load passengers from TXT file directly into passengerList
		passengerList.loadPassengersFromTXT("PassengerList.txt");
		// Create a new Baggage object
		Baggage checkBaggage = new Baggage();
		//create a new BaggageList object
		BaggageList calculateTotalfee = new BaggageList();

		// creates demo object, with a populated models.Passenger list
		ProgrammeDemo sld = new ProgrammeDemo();

		// allow user to interact using a GUI
		sld.showGUI(passengerList,checkBaggage,calculateTotalfee);

		// allow user to interact with this list
		// using text interface
		sld.showInterface();

	}

}
