package Stage2.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PassengerQueueGUI extends JFrame {
    private JPanel mainPanel;
    public PassengerQueueGUI() {
        setTitle("Passenger Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create header panel
        JPanel headerPanel = new JPanel(new GridLayout(0, 4));
        JLabel flightNumberLabel = new JLabel("Flight Number");
        JLabel passengerNameLabel = new JLabel("Passenger Name");
        JLabel luggageWeightLabel = new JLabel("Luggage Weight");
        JLabel luggageVolumeLabel = new JLabel("Luggage Volume");
        headerPanel.add(flightNumberLabel);
        headerPanel.add(passengerNameLabel);
        headerPanel.add(luggageWeightLabel);
        headerPanel.add(luggageVolumeLabel);
        mainPanel.add(headerPanel);

        // Read passenger information from text file and display
        try {
            BufferedReader reader = new BufferedReader(new FileReader("passenger_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    JPanel passengerPanel = new JPanel(new GridLayout(0, 4)); // Panel for each passenger

                    // Create labels for each piece of passenger information
                    JLabel flightNumber = new JLabel(parts[0]);
                    flightNumber.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
                    JLabel passengerName = new JLabel(parts[1]);
                    JLabel luggageWeight = new JLabel(parts[2]);
                    JLabel luggageVolume = new JLabel(parts[3]);

                    // Add labels to passenger panel
                    passengerPanel.add(flightNumber);
                    passengerPanel.add(passengerName);
                    passengerPanel.add(luggageWeight);
                    passengerPanel.add(luggageVolume);

                    mainPanel.add(passengerPanel);
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add main panel to scroll pane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PassengerQueueGUI::new);
    }
}
