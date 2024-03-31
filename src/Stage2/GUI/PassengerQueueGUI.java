package Stage2.GUI;

import Stage1.Baggage;
import Stage1.Passenger;
import Stage2.Observer;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class PassengerQueueGUI extends JPanel implements Observer {
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private final PassengerQueue vipQueue;
    private final PassengerQueue regularQueue;
    private JPanel vipPanel;
    private JPanel regularPanel;

    public PassengerQueueGUI(PassengerQueue vipQueue, PassengerQueue regularQueue) {
        this.regularQueue = regularQueue;
        this.regularQueue.registerObserver(this);
        this.vipQueue = vipQueue;
        this.vipQueue.registerObserver(this);

        setSize(1300, 300);
        setLayout(new BorderLayout());

        JPanel regularMainPanel = createQueuePanel(regularQueue, "Regular Queue");
        JPanel vipMainPanel = createQueuePanel(vipQueue, "VIP Queue");

        add(regularMainPanel, BorderLayout.WEST);
        add(vipMainPanel, BorderLayout.EAST);
    }

    private JPanel createQueuePanel(PassengerQueue queue, String title) {
        JPanel queuePanel = new JPanel(new GridLayout(20, 1, 10, 10));
        // Assign the panel to the correct member variable based on the queue type
        if (queue.equals(vipQueue)) {
            vipPanel = queuePanel;
        } else {
            regularPanel = queuePanel;
        }
        // Create and configure the scroll pane
        JScrollPane queueScrollPane = new JScrollPane(queuePanel);
        queueScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        queueScrollPane.setPreferredSize(new Dimension(600, 150));

        // Create the header panel with the title "Flight Code", "Passenger Name", "Baggage Weight" and "Baggage Volume".
        JPanel headerPanel = createHeaderPanel();

        JLabel queueLabel = new JLabel(title);
        queueLabel.setFont(LABEL_FONT);
        queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        queueLabel.setForeground(Color.BLACK);

        // Create the main panel to hold everything
        JPanel queueMainPanel = new JPanel(new BorderLayout());
        queueMainPanel.add(queueLabel, BorderLayout.NORTH); // Add the queue title label at the top
        queueMainPanel.add(headerPanel, BorderLayout.CENTER); // Add the header panel with column titles
        queueMainPanel.add(queueScrollPane, BorderLayout.SOUTH); // Add the scroll pane with the queue information

        return queueMainPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // 用GridLayout将四个标签平均分布
        JLabel flightNumberLabel = new JLabel("Flight Code");
        JLabel passengerNameLabel = new JLabel("Passenger Name");
        JLabel BaggageWeightLabel = new JLabel("Baggage Weight");
        JLabel BaggageVolumeLabel = new JLabel("Baggage Volume");
        flightNumberLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        BaggageVolumeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        headerPanel.add(flightNumberLabel);
        headerPanel.add(passengerNameLabel);
        headerPanel.add(BaggageWeightLabel);
        headerPanel.add(BaggageVolumeLabel);
        return headerPanel;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            vipPanel.removeAll();
            regularPanel.removeAll();

            loadPassengerInfo(vipQueue, vipPanel);
            loadPassengerInfo(regularQueue, regularPanel);

            this.revalidate();
            this.repaint();
        });
    }

    private void loadPassengerInfo(PassengerQueue passengerQueue, JPanel panel) {
        Iterator<Passenger> iterator = passengerQueue.iterator();
        for (int i = 0; i < 20 && iterator.hasNext(); i++) {
            Passenger passenger = iterator.next();
            JPanel passengerPanel = new JPanel(new GridLayout(1, 4));

            passengerPanel.add(createLabelWithBorder(passenger.getFlightCode(), 25, 0));
            passengerPanel.add(createLabelWithBorder(passenger.getName(), 0, 0));

            if (!passenger.getHisBaggageList().getBaggageList().isEmpty()) {
                Baggage firstBaggage = passenger.getHisBaggageList().get(0);
                passengerPanel.add(createLabelWithBorder(firstBaggage.getWeightPrint(), 40, 0));
                passengerPanel.add(createLabelWithBorder(firstBaggage.getVolumePrint(), 18, 0));
            } else {
                passengerPanel.add(createLabelWithBorder("N/A", 40, 0));
                passengerPanel.add(createLabelWithBorder("N/A", 40, 0));
            }

            panel.add(passengerPanel);
        }
        panel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    }

    private JLabel createLabelWithBorder(String text, int leftPadding, int rightPadding) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, rightPadding));
        return label;
    }
}
