package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateData {
    private static final String[] firstNames = {"John", "Emily", "Michael", "Susan", "David", "Lisa", "Karen", "Robert", "Linda", "William"};
    private static final String[] lastNames = {"Smith", "Johnson", "Brown", "Davis", "Lee", "Wilson", "Martinez", "Miller", "Taylor", "Anderson"};
    private static final String[] carriers = {"American Airlines", "Delta Air Lines", "United Airlines", "British Airways"};
    private static final Random random = new Random();
    private static final String directoryPath = ""; // 文件夹路径

    private static String generateFirstName() {
        return firstNames[random.nextInt(firstNames.length)];
    }

    private static String generateLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    private static String generateFlightCode() {
        return "" + (char) ('A' + random.nextInt(26)) + (char) ('A' + random.nextInt(26)) +
                random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
    }

    public static void generateData() throws IOException {
        // 确保txt目录存在
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 如果目录不存在，则创建它
        }

        BufferedWriter passengersWriter = new BufferedWriter(new FileWriter(directoryPath + "PassengerList.txt"));
        BufferedWriter flightsWriter = new BufferedWriter(new FileWriter(directoryPath + "FlightList.txt"));

        passengersWriter.write("Reference Code,FirstName,LastName,FlightCode,CheckInStatus\n");
        flightsWriter.write("FlightCode,DestinationAirport,Carrier,MaxPassengerCapacity,MaxBaggageWeight,MaxBaggageVolume\n");

        // 首先生成所有航班号
        List<String> flightCodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String flightCode = generateFlightCode();
            flightCodes.add(flightCode);
            String destinationAirport = "Airport " + i;
            String carrier = carriers[random.nextInt(carriers.length)];
            int maxPassengerCapacity = 100 + random.nextInt(151); // 100 to 250
            int maxBaggageWeight = 1000 + random.nextInt(4001); // 1000 to 5000 lbs
            int maxBaggageVolume = 100 + random.nextInt(201); // 100 to 300 cubic inches

            flightsWriter.write(flightCode + "," + destinationAirport + "," + carrier + "," + maxPassengerCapacity + "," +
                    maxBaggageWeight + " lbs," + maxBaggageVolume + " cubic inches\n");
        }
        flightsWriter.close();

        // 然后为每个乘客分配航班号
        for (int i = 1; i <= 500; i++) {
            String bookingReference = "B" + String.format("%04d", i);
            String firstName = generateFirstName();
            String lastName = generateLastName();
            String flightCode = flightCodes.get(random.nextInt(flightCodes.size()));
            String checkInStatus = "NotCheckedIn";

            passengersWriter.write(bookingReference + "," + firstName + "," + lastName + "," + flightCode + "," + checkInStatus + "\n");
        }
        passengersWriter.close();

        System.out.println("Data generation completed.");
    }

    public static void main(String[] args) {
        try {
            generateData();
        } catch (IOException e) {
            System.err.println("An error occurred during data generation.");
            e.printStackTrace();
        }
    }
}
