package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {



    public Report(FlightList flightList) throws IOException {
        String filePath = "report.txt";
        new FileWriter(filePath, false).close();
        if (flightList.size() > 0) {
            writeReportToFile(flightList, filePath);
        } else {
            System.out.println("No flight information today!");
        }
    }


    // create report content
    private String reportModel(Flight flight) {
        return flight.toString();
    }


    // write report file
    public void writeReportToFile(FlightList flightList, String filePath) {
        String start =
                String.format("=============================================================Report============================================================%n");
        String header = String.format("%-5s\t%-15s\t%-12s\t%-10s\t%-15s\t%-15s\t%-15s\t%-20s%n",
                "#", "Flight Number", "Tickets Sold", "Check ins", "Baggage Weight", "Baggage Volume", "Flight Status", "Total Fees Collected");
        String end =
                String.format("==============================================================END==============================================================%n");
        String note =
                String.format("Note: If the number of passengers/ total weight of baggage/ total volume of baggage exceeds the corresponding capacity, the flight status will be overload.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.print(start);
            writer.write(start);
            System.out.print(header);
            writer.write(header);
            for (int i = 0; i < flightList.size(); i++) {
                Flight flight = flightList.get(i);
                String reportContent = String.format("%-5d\t%s%n", i + 1, reportModel(flight));
                System.out.print(reportContent);
                writer.write(reportContent);
            }
            writer.write(end);
            System.out.print(end);
            writer.write(note);
            System.out.print(note);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
