package Stage2.GUI;

import Stage1.Passenger;
import Stage2.CheckInCounter;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckInCounterGUI extends JFrame implements Observer {
    private JPanel mainPanel;
    private JPanel allVipPanel;
    private JPanel allRegularPanel;
    private List<CheckInCounter> counters;

    public CheckInCounterGUI(List<CheckInCounter> counters) {
        this.counters = counters;
        setTitle("Check-in Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 200);

        mainPanel = new JPanel(new GridLayout(1, 2));

        allVipPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        allRegularPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        initCounterPanels();

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initCounterPanels() {
        JPanel vipMainPanel = createMainPanel("VIP Counter");
        JPanel regularMainPanel = createMainPanel("Regular Counter");

        vipMainPanel.add(new JScrollPane(allVipPanel), BorderLayout.CENTER);
        regularMainPanel.add(new JScrollPane(allRegularPanel), BorderLayout.CENTER);

        mainPanel.add(vipMainPanel);
        mainPanel.add(regularMainPanel);
    }

    private JPanel createMainPanel(String title) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 20));
        mainPanel.add(label, BorderLayout.NORTH);
        return mainPanel;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            updateCounterDisplay(allVipPanel, true);
            updateCounterDisplay(allRegularPanel, false);

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    private void updateCounterDisplay(JPanel parentPanel, boolean isVip) {
        parentPanel.removeAll();

        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVip) {
                JPanel counterPanel = createCounterPanel(counter);
                parentPanel.add(counterPanel);
            }
        }
    }

    private JPanel createCounterPanel(CheckInCounter counter) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel counterIdLabel = new JLabel("Counter ID: " + counter.getCounterId());
        panel.add(counterIdLabel);

        Passenger passenger = counter.getCurrentPassenger();
        if (passenger != null) {
            JLabel passengerNameLabel = new JLabel("Passenger: " + passenger.getName());
            panel.add(passengerNameLabel);
        } else {
            JLabel noPassengerLabel = new JLabel("No current passenger");
            panel.add(noPassengerLabel);
        }

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

}
