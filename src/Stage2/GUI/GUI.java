package Stage2.GUI;

import Stage1.modules.FlightList;
import Stage2.CheckInCounter;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * GUI class represents the main graphical user interface window for the application.
 * It combines and displays the PassengerQueueGUI, CheckInCounterGUI, and FlightStatusGUI components.
 */
public class GUI extends JFrame {
    // Member variables
    final PassengerQueueGUI passengerQueueGUI;
    final CheckInCounterGUI checkInCounterGUI;
    final FlightStatusGUI flightStatusGUI;

    /**
     * Constructs a GUI object with the specified components.
     *
     * @param vipQueue       The passenger queue for VIP passengers.
     * @param regularQueue   The passenger queue for regular passengers.
     * @param checkInCounter The list of check-in counters.
     * @param flightList     The list of flights.
     */
    public GUI(PassengerQueue vipQueue, PassengerQueue regularQueue, List<CheckInCounter> checkInCounter, FlightList flightList) {
        setTitle("Merged GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLayout(new GridBagLayout());

        // Initialize GUI components
        passengerQueueGUI = new PassengerQueueGUI(vipQueue, regularQueue);
        checkInCounterGUI = new CheckInCounterGUI(checkInCounter);
        flightStatusGUI = new FlightStatusGUI(flightList);

        // Align components to the left
        passengerQueueGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkInCounterGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        flightStatusGUI.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Configure GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Add components to the layout
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        add(passengerQueueGUI, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        add(checkInCounterGUI, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        add(flightStatusGUI, gbc);

        // Set window location and visibility
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Updates the GUI components.
     */
    public void update() {
        // Update each GUI component if not null
        if (passengerQueueGUI != null) {
            SwingUtilities.invokeLater(passengerQueueGUI::update);
        }
        if (checkInCounterGUI != null) {
            SwingUtilities.invokeLater(checkInCounterGUI::update);
        }
        if (flightStatusGUI != null) {
            SwingUtilities.invokeLater(flightStatusGUI::update);
        }
    }
}
