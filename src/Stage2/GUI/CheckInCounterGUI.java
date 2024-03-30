package Stage2.GUI;

import Stage1.Passenger;
import Stage2.CheckInCounter;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckInCounterGUI extends JPanel implements Observer {
    private JPanel allVipPanel;
    private JPanel allRegularPanel;
    private List<CheckInCounter> counters;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);

    public CheckInCounterGUI(List<CheckInCounter> counters) {
        this.counters = counters;
        this.setLayout(new GridBagLayout());
        this.setSize(1300, 120);

        allVipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        allRegularPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        initCounterPanels();
    }

    private void initCounterPanels() {
        GridBagConstraints gbc = new GridBagConstraints();
        // gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        addCounterPanel(allRegularPanel, "Regular Counter", 0, 0.7);
        addCounterPanel(allVipPanel, "VIP Counter", 1, 0.3);
    }

    private void addCounterPanel(JPanel panel, String title, int gridx, double weightx) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.weightx = weightx;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(createMainPanel(title, panel), gbc);
    }

    private JPanel createMainPanel(String title, JPanel panel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(LABEL_FONT);
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return mainPanel;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            updateCounterDisplay(allVipPanel, true);
            updateCounterDisplay(allRegularPanel, false);

            this.revalidate();
            this.repaint();
        });
    }

    private void updateCounterDisplay(JPanel parentPanel, boolean isVip) {
        parentPanel.removeAll();

        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVip && counter.getStatus()) {
                JPanel counterPanel = createCounterPanel(counter);
                parentPanel.add(counterPanel);
            }
        }
    }

    private JPanel createCounterPanel(CheckInCounter counter) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(145, 100));

        JLabel counterIdLabel = new JLabel("Counter ID: " + counter.getCounterId());
        panel.add(counterIdLabel);

        Passenger passenger = counter.getCurrentPassenger();
        if (passenger != null) {
            JLabel passengerNameLabel = new JLabel("Passenger: " + passenger.getName());
            String weight = String.format("%.2f", passenger.getHisBaggageList().getTotalWeight());
            JLabel passengerBaggage = new JLabel("Baggage weight: " + weight);
            String fee = String.format("%.2f", passenger.getHisBaggageList().getTotalFee());
            JLabel baggageFee = new JLabel("Fee: " + fee);
            panel.add(passengerNameLabel);
            panel.add(passengerBaggage);
            panel.add(baggageFee);
        } else {
            JLabel noPassengerLabel = new JLabel("No current passenger");
            panel.add(noPassengerLabel);
        }

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }
}
