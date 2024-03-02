package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Report {


    public void getReport(FlightList fl){

        Report report = new Report();
        String filePath = "report.txt";
        if(fl.size() > 0){
            for(int i =0;i < fl.size();i++){
                //System.out.println(fl.get(i).getFlightCode());
                printReport(fl.get(i).getFlightCode(), fl.get(i).getPassengerInFlight().checkInSize(), fl.get(i).getBaggageInFlight().getTotalWeight(), fl.get(i).getBaggageInFlight().getTotalVolume(), fl.get(i).isOverCapacity(), fl.get(i).getBaggageInFlight().getTotalFee());
                writeReportToFile(fl.get(i).getFlightCode(), fl.get(i).getPassengerInFlight().checkInSize(), fl.get(i).getBaggageInFlight().getTotalWeight(), fl.get(i).getBaggageInFlight().getTotalVolume(), fl.get(i).isOverCapacity(), fl.get(i).getBaggageInFlight().getTotalFee(), filePath);
            }
        }
        else {
            System.out.println("No flight imformation today!");
        }

    }
    // create report content
    private String reportModual(String flightNumber, int checkIns, double luggageWeight, double luggageVolume, boolean takeOffStatus, double overFee) {
        String divider = "=========================================";
        return new StringBuilder()
                .append("=================Report==================").append("\n")
                .append("Flight number: ").append(flightNumber).append("\n")
                .append(divider).append("\n")
                .append("Check in: ").append(checkIns).append("\n")
                .append(divider).append("\n")
                .append("Luggage weight: ").append(luggageWeight).append("\n")
                .append(divider).append("\n")
                .append("Luggage volume: ").append(luggageVolume).append("\n")
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
        System.out.print(reportModual(flightNumber, checkIns, luggageWeight, luggageVolume, takeOffStatus, overFee));
    }

    // write report file
    public void writeReportToFile(String flightNumber, int checkIns, double luggageWeight, double luggageVolume, boolean takeOffStatus, double overFee, String filePath) {
        String reportContent = reportModual(flightNumber, checkIns, luggageWeight, luggageVolume, takeOffStatus, overFee);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(reportContent);
            //System.out.println("报告已追加到 " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // test
    public static void main(String[] args) {
        // create report instance
        Report report = new Report();
        FlightList fl = new FlightList();
        fl.loadFlightsFromTXT("FlightList.txt");
        // change later
        report.getReport(fl);

        /*
        Report r = new Report();
        r.printReport("abc", 1, 2, 3, false, 5);
        r.writeReportToFile("abc", 1, 2, 3, false, 5, "report.txt");

         */
    }
}
