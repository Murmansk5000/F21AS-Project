import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {

    // 生成报告
    private String generateReportString(String flightNumber, int soldTickets, int checkIns, double luggageWeight, double luggageVolume, String takeOffStatus, String reason, String departureTime) {
        String divider = "===============================";
        return new StringBuilder()
                .append(divider).append("\n")
                .append("============Report=============").append("\n")
                .append(divider).append("\n")
                .append("Flight number: ").append(flightNumber).append("\n")
                .append(divider).append("\n")
                .append("Sold Ticket: ").append(soldTickets).append("\n")
                .append(divider).append("\n")
                .append("Check in: ").append(checkIns).append("\n")
                .append(divider).append("\n")
                .append("Luggage weight: ").append(luggageWeight).append("\n")
                .append(divider).append("\n")
                .append("Luggage volume: ").append(luggageVolume).append("\n")
                .append(divider).append("\n")
                .append("Take off: ").append(takeOffStatus).append("\n")
                .append(divider).append("\n")
                .append("Reason (if can't): ").append(reason).append("\n")
                .append(divider).append("\n")
                .append("Departure time: ").append(departureTime).append(" (On time/Delay)").append("\n")
                .append("=============END===============").append("\n")
                .toString();
    }

    // 打印报告
    public void printReport(String flightNumber, int soldTickets, int checkIns, double luggageWeight, double luggageVolume, String takeOffStatus, String reason, String departureTime) {
        System.out.print(generateReportString(flightNumber, soldTickets, checkIns, luggageWeight, luggageVolume, takeOffStatus, reason, departureTime));
    }

    // 写报告到文件
    public void writeReportToFile(String flightNumber, int soldTickets, int checkIns, double luggageWeight, double luggageVolume, String takeOffStatus, String reason, String departureTime, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(generateReportString(flightNumber, soldTickets, checkIns, luggageWeight, luggageVolume, takeOffStatus, reason, departureTime));
            writer.close();
            System.out.println("Report has been written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试
    public static void main(String[] args) {
        // 创建Report实例
        Report report = new Report();

        // 后期修改
        String flightNumber = "AB1234";
        int soldTickets = 120;
        int checkIns = 110;
        double luggageWeight = 350.5;
        double luggageVolume = 75.3;
        String takeOffStatus = "Can";
        String reason = "N/A";
        String departureTime = "18:00";
        String filePath = "report.txt";

        // 打印报告
        report.printReport(flightNumber, soldTickets, checkIns, luggageWeight, luggageVolume, takeOffStatus, reason, departureTime);

        // 写报告文件
        report.writeReportToFile(flightNumber, soldTickets, checkIns, luggageWeight, luggageVolume, takeOffStatus, reason, departureTime, filePath);
    }
}
