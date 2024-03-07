package models;

import GUI.GUI;

public class ProgrammeDemo {
    private static PassengerList paxList;
    private static FlightList fltList;


    public ProgrammeDemo() throws AllExceptions.NoMatchingFlightException {
        // Initialize empty list of passengers and flights
        // Load passengers from TXT file directly into paxList
        paxList = new PassengerList();
        paxList.loadPassengersFromTXT("PassengerList.txt");
        // Load flights from TXT file directly into fltList
        fltList = new FlightList();
        fltList.loadFlightsFromTXT("FlightList.txt");
        fltList.addPassengersToFlights(paxList);
    }



    /**
     * Show GUI
     */
    public void showGUI(PassengerList passengerList, FlightList flightList) {
        GUI gui = new GUI(passengerList, flightList);
        gui.FlightCheckInGUI();
        gui.setVisible(true);


    }

    public static void main(String[] args) throws AllExceptions.NoMatchingFlightException {
        ProgrammeDemo sld = new ProgrammeDemo();
        sld.showGUI(paxList, fltList);
    }




}
