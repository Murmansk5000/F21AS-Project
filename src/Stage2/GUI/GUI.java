package Stage2.GUI;

import Stage1.FlightList;
import Stage2.CheckInCounter;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    final PassengerQueueGUI passengerQueueGUI;
    final CheckInCounterGUI checkInCounterGUI;
    final FlightStatusGUI flightStatusGUI;

    public GUI(PassengerQueue vipQueue, PassengerQueue regularQueue, List<CheckInCounter> checkInCounter, FlightList flightList) {
        setTitle("Merged GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLayout(new GridBagLayout());

        passengerQueueGUI = new PassengerQueueGUI(vipQueue, regularQueue);
        checkInCounterGUI = new CheckInCounterGUI(checkInCounter);
        flightStatusGUI = new FlightStatusGUI(flightList);


        passengerQueueGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkInCounterGUI.setAlignmentX(Component.LEFT_ALIGNMENT);
        flightStatusGUI.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        add(passengerQueueGUI, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        add(checkInCounterGUI, gbc);

        gbc.gridy = 2;
        add(flightStatusGUI, gbc);

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
