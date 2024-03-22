package Stage2.GUI;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel mainPanel;

    public GUI(PassengerQueueGUI passengerQueueGUI, CheckInCounterGUI checkInCounterGUI, FlightStatusGUI flightStatusGUI) {
        setTitle("Merged GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // Add PassengerQueueGUI panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4; // 40% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(passengerQueueGUI.getMainPanel(), gbc);

        // Add CheckInCounterGUI panel
        gbc.gridy = 1;
        gbc.weighty = 0.2; // 20% of the vertical space
        mainPanel.add(checkInCounterGUI.getMainPanel(), gbc);

        // Add FlightStatusGUI panel
        gbc.gridy = 2;
        gbc.weighty = 0.4; // 40% of the vertical space
        mainPanel.add(flightStatusGUI.getMainPanel(), gbc);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);


        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
