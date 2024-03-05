package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {


    public Report(FlightList fl) {
        String filePath = "report.txt";
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Flight flight;
        if (fl.size() > 0) {
            for (int i = 0; i < fl.size(); i++) {
                flight = fl.get(i);
                //System.out.println(fl.get(i).getFlightCode());
                printReport(flight);
                writeReportToFile(flight, filePath);
            }
        } else {
            System.out.println("No flight information today!");
        }
    }

    // create report content
    private String reportModel(Flight flight) {
        String divider = "=========================================";
        return new StringBuilder()
                .append("=================Report==================").append("\n")
                .append("Flight number: ").append(flight.getFlightCode()).append("\n")
                .append(divider).append("\n")
                .append("Tickets sold: ").append(flight.getPassengerInFlight().size())
                .append(divider).append("\n")
                .append("Check in: ").append(flight.getPassengerInFlight().checkInSize()).append("\n")
                .append(divider).append("\n")
                .append("Baggage weight: ").append(flight.getBaggageInFlight().getTotalWeight()).append("\n")
                .append(divider).append("\n")
                .append("Baggage volume: ").append(flight.getBaggageInFlight().getTotalVolume()).append("\n")
                .append(divider).append("\n")
                .append("Permission to take off: ").append(!flight.isOverCapacity()).append("\n")
                .append(divider).append("\n")
                .append("Total excess baggage fees collected: ").append(flight.getBaggageInFlight().getTotalFee()).append("\n")
                .append(divider).append("\n")
                .append("Note: If the flight is overloaded,").append("\n")
                .append("the take-off status will be false.").append("\n")
                .append("==================END====================").append("\n")
                .append("\n")
                .toString();
    }

    // print report
    public void printReport(Flight flight) {
        //add a loop for every flight
        System.out.print(reportModel(flight));
    }

    // write report file
    public void writeReportToFile(Flight flight, String filePath) {
        String reportContent = reportModel(flight);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
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
