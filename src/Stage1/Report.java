package Stage1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {
    private static final String FILE_REPORT_TXT = "file/report.txt";
    /**
     * Constructs a Report object with the given flight list and generates a report file.
     * Prints a message if the flight list is empty.
     *
     * @param flightList The list of flights for which the report is generated.
     * @throws IOException If an I/O error occurs while writing the report file.
     */
    public Report(FlightList flightList) throws IOException {
        new FileWriter(FILE_REPORT_TXT, false).close();
        if (flightList.size() > 0) {
            writeReportToFile(flightList, FILE_REPORT_TXT);
        } else {
            System.out.println("No flight information today!");
        }
    }

    /**
     * Writes the report file containing flight information.
     *
     * @param flightList The list of flights to be included in the report.
     * @param filePath   The file path where the report will be written.
     */
    public void writeReportToFile(FlightList flightList, String filePath) {
        boolean ifPrint = true;
        String start =
                String.format("=============================================================Report============================================================%n");
        String header = String.format("%-5s\t%-15s\t%-12s\t%-10s\t%-15s\t%-15s\t%-15s\t%-20s%n",
                "#", "Flight Number", "Tickets Sold", "Check ins", "Baggage Weight", "Baggage Volume", "Flight Status", "Total Fees Collected");
        String end =
                String.format("==============================================================END==============================================================%n");
        String note =
                String.format("Note: If the number of passengers/ total weight of baggage/ total volume of baggage exceeds the corresponding capacity, the flight status will be overload.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (ifPrint) System.out.print(start);
            writer.write(start);
            if (ifPrint) System.out.print(header);
            writer.write(header);
            for (int i = 0; i < flightList.size(); i++) {
                Flight flight = flightList.get(i);
                String reportContent = String.format("%-5d\t%s%n", i + 1, flight.toString());
                if (ifPrint) System.out.print(reportContent);
                writer.write(reportContent);
            }
            writer.write(end);
            if (ifPrint) System.out.print(end);
            writer.write(note);
            if (ifPrint) System.out.print(note);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
