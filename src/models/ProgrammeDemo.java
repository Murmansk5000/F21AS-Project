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
        addPassengersToFlights(paxList, fltList);
    }

    private static void addPassengersToFlights(PassengerList passengerList, FlightList flightList) {
        for (Passenger passenger : passengerList.getPassengers()) {
            String hisFlightCode = passenger.getFlightCode();
            Flight flight = flightList.findByCode(hisFlightCode);
            if (flight != null) {
                flight.addPassenger(passenger);
            }
        }
    }

    /**
     * Show GUI
     */
    public void showGUI(PassengerList passengerList, FlightList flightList) {
        GUI gui = new GUI(passengerList, flightList);
        gui.FlightCheckInGUI();
        gui.setVisible(true);


    }

    public static void main(String[] args) {
        ProgrammeDemo sld = new ProgrammeDemo();
        sld.showGUI(paxList, fltList);
    }




}
