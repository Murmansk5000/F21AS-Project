package Stage2.GUI;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel mainPanel;

    public GUI(PassengerQueueGUI passengerQueueGUI, CheckInCounterGUI checkInCounterGUI, FlightStatusGUI flightStatusGUI) {
        setTitle("Merged GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 600);

        mainPanel = new JPanel(new GridLayout(3, 1));

        // Get the main panels of each GUI
        JPanel passengerQueuePanel = passengerQueueGUI.getMainPanel();
        JPanel checkInCounterPanel = checkInCounterGUI.getMainPanel();
        JPanel flightStatusPanel = flightStatusGUI.getMainPanel();

        // Add the main panels of each GUI to the merged GUI
        mainPanel.add(passengerQueuePanel);
        mainPanel.add(checkInCounterPanel);
        mainPanel.add(flightStatusPanel);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
