package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {


    public Report(FlightList fl) {
        String filePath = "report.txt";
        Flight flight;
        if (fl.size() > 0) {
            for (int i = 0; i < fl.size(); i++) {
                flight = fl.get(i);
                //System.out.println(fl.get(i).getFlightCode());
                printReport(flight.getFlightCode(),
                        flight.getPassengerInFlight().checkInSize(),
                        flight.getBaggageInFlight().getTotalWeight(),
                        flight.getBaggageInFlight().getTotalVolume(),
                        !flight.isOverCapacity(),
                        flight.getBaggageInFlight().getTotalFee());
                writeReportToFile(flight.getFlightCode(),
                        flight.getPassengerInFlight().checkInSize(),
                        flight.getBaggageInFlight().getTotalWeight(),
                        flight.getBaggageInFlight().getTotalVolume(),
                        !flight.isOverCapacity(),
                        flight.getBaggageInFlight().getTotalFee(), filePath);
            }
        } else {
            System.out.println("No flight information today!");
        }
    }

    // create report content
    private String reportModel(String flightNumber, int checkIns, double luggageWeight, double luggageVolume, boolean takeOffStatus, double overFee) {
        String divider = "=========================================";
        return new StringBuilder()
                .append("=================Report==================").append("\n")
                .append("Flight number: ").append(flightNumber).append("\n")
                .append(divider).append("\n")
                .append("Check in: ").append(checkIns).append("\n")
                .append(divider).append("\n")
                .append("Baggage weight: ").append(luggageWeight).append("\n")
                .append(divider).append("\n")
                .append("Baggage volume: ").append(luggageVolume).append("\n")
                .append(divider).append("\n")
                .append("Take off: ").append(takeOffStatus).append("\n")
                .append(divider).append("\n")
                .append("Total excess baggage fees collected: ").append(overFee).append("\n")
                .append("==================END====================").append("\n")
                .toString();
    }

    // print report
    public void printReport(String flightNumber, int checkIns, double luggageWeight, double luggageVolume, boolean takeOffStatus, double overFee) {
        //add a loop for every flight
        System.out.print(reportModel(flightNumber, checkIns, luggageWeight, luggageVolume, takeOffStatus, overFee));
    }

    // write report file
    public void writeReportToFile(String flightNumber, int checkIns, double luggageWeight, double luggageVolume, boolean takeOffStatus, double overFee, String filePath) {
        String reportContent = reportModel(flightNumber, checkIns, luggageWeight, luggageVolume, takeOffStatus, overFee);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(reportContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // test
    /*

    public static void main(String[] args) {
        // create report instance
        Report report = new Report();
        FlightList fl = new FlightList();
        fl.loadFlightsFromTXT("FlightList.txt");
        // change later
        report.getReport(fl);


        Report r = new Report();
        r.printReport("abc", 1, 2, 3, false, 5);
        r.writeReportToFile("abc", 1, 2, 3, false, 5, "report.txt");


    }
    */

}
