package Stage2.GUI;

import javax.swing.*;
import java.awt.*;

public class FlightStatusGUI extends JFrame {
    private JPanel mainPanel;

    public FlightStatusGUI() {
        setTitle("Flight Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);

        mainPanel = new JPanel(new GridLayout(1, 3)); // Arrange flight panels horizontally

        // Create flight panels
        JPanel flightPanel1 = createFlightPanel("Flight 1");
        JPanel flightPanel2 = createFlightPanel("Flight 2");
        JPanel flightPanel3 = createFlightPanel("Flight 3");

        // Add flight panels to main panel
        mainPanel.add(flightPanel1);
        mainPanel.add(flightPanel2);
        mainPanel.add(flightPanel3);

        // Add main panel to frame
        add(mainPanel);

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JPanel createFlightPanel(String flightName) {
        JPanel flightPanel = new JPanel(new GridLayout(4, 1)); // 4 rows for displaying flight information
        flightPanel.setBorder(BorderFactory.createTitledBorder(flightName)); // Add border with flight name as title

        // Add labels for flight information
        JLabel checkInLabel = new JLabel("Checked-in Passengers: 0");
        JLabel baggageWeightLabel = new JLabel("Total Baggage Weight: 0 kg");
        JLabel statusLabel = new JLabel("Status: On Time");
        JLabel spacerLabel = new JLabel(); // Spacer label for spacing

        // Set alignment for labels
        checkInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        baggageWeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add labels to flight panel
        flightPanel.add(checkInLabel);
        flightPanel.add(baggageWeightLabel);
        flightPanel.add(statusLabel);
        flightPanel.add(spacerLabel);

        return flightPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlightStatusGUI());
    }
}
