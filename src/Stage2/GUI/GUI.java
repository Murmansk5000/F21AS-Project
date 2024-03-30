package Stage2.GUI;

import Stage1.FlightList;
import Stage2.CheckInCounter;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    PassengerQueueGUI passengerQueueGUI;
    CheckInCounterGUI checkInCounterGUI;
    FlightStatusGUI flightStatusGUI;
    private JPanel mainPanel;

    public GUI(PassengerQueue vipQueue, PassengerQueue regularQueue, List<CheckInCounter> checkInCounter, FlightList flightList) {
        setTitle("Merged GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);

        passengerQueueGUI = new PassengerQueueGUI(vipQueue, regularQueue);
        checkInCounterGUI = new CheckInCounterGUI(checkInCounter);
        flightStatusGUI = new FlightStatusGUI(flightList);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Ensure that components stay at their preferred size
        passengerQueueGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkInCounterGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        flightStatusGUI.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add the components to the main panel
        mainPanel.add(passengerQueueGUI);
        mainPanel.add(Box.createVerticalStrut(10)); // Creates space between the components
        mainPanel.add(checkInCounterGUI);
        mainPanel.add(Box.createVerticalStrut(10)); // Creates space between the components
        mainPanel.add(flightStatusGUI);

        // Add the main panel to the frame
        getContentPane().add(new JScrollPane(mainPanel)); // Add scrolling to the entire panel

        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Make the window visible

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Add PassengerQueueGUI panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2; // 40% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(passengerQueueGUI, gbc);

        // Add CheckInCounterGUI panel
        gbc.gridy = 1;
        gbc.weighty = 0.2; // 20% of the vertical space
        mainPanel.add(checkInCounterGUI, gbc);

        // Add FlightStatusGUI panel
        gbc.gridy = 2;
        gbc.weighty = 0.6; // 60% of the vertical space
        mainPanel.add(flightStatusGUI, gbc);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);


        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update() {
        if (passengerQueueGUI != null) {
            SwingUtilities.invokeLater(() -> {
                passengerQueueGUI.update();
            });
        }
        if (checkInCounterGUI != null) {
            SwingUtilities.invokeLater(() -> {
                checkInCounterGUI.update();
            });
        }
        if (flightStatusGUI != null) {
            SwingUtilities.invokeLater(() -> {
                flightStatusGUI.update();
            });
        }


    }
}
