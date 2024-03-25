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

        allVipPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        allRegularPanel = new JPanel(new GridLayout(1, 0, 10, 10));

        initCounterPanels();

        add(mainPanel);
        setLocationRelativeTo(null);
//        setVisible(true);
    }

    private void initCounterPanels() {
        JPanel regularMainPanel = createMainPanel("Regular Counter");
        JPanel vipMainPanel = createMainPanel("VIP Counter");

        regularMainPanel.add(new JScrollPane(allRegularPanel), BorderLayout.CENTER);
        vipMainPanel.add(new JScrollPane(allVipPanel), BorderLayout.CENTER);

        mainPanel.add(regularMainPanel);
        mainPanel.add(vipMainPanel);

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
            String weight = String.format("%.2f", passenger.getTheBaggageList().getTotalWeight());
            JLabel passengerBaggage = new JLabel("Baggage: " + weight);
            String fee = String.format("%.2f", passenger.getTheBaggageList().getTotalFee());
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
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
