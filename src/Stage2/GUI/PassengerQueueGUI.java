package Stage2.GUI;

import Stage1.modules.Baggage;
import Stage1.modules.Passenger;
import Stage2.Observer;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


/**
 * PassengerQueueGUI class represents the graphical user interface for displaying
 * passenger queues, including VIP and regular queues.
 */
public class PassengerQueueGUI extends JPanel implements Observer {

    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);

    // Member variables
    private final PassengerQueue vipQueue;
    private final PassengerQueue regularQueue;
    private JPanel vipPanel;
    private JPanel regularPanel;

    /**
     * Constructs a PassengerQueueGUI object with the specified VIP and regular queues.
     *
     * @param vipQueue     The VIP passenger queue to display.
     * @param regularQueue The regular passenger queue to display.
     */
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

    /**
     * Creates a panel to display the passenger queue with the specified title.
     *
     * @param queue The passenger queue to display.
     * @param title The title of the queue panel.
     * @return The created panel to display the passenger queue.
     */
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

        // Create the header panel
        JPanel headerPanel = createHeaderPanel();

        // Create the queue title label
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

    /**
     * Creates a header panel with column titles.
     *
     * @return The header panel with column titles.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // Use GridLayout to evenly distribute four labels
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

    /**
     * Update the interface while a customer enter the queue
     */
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

    /**
     * Loads passenger information into the specified panel from the given passenger queue.
     *
     * @param passengerQueue The passenger queue to load information from.
     * @param panel          The panel to display the passenger information.
     */
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

    /**
     * Creates a label with specified text and border.
     *
     * @param text         The text to display on the label.
     * @param leftPadding  The left padding of the label.
     * @param rightPadding The right padding of the label.
     * @return The created label.
     */
    private JLabel createLabelWithBorder(String text, int leftPadding, int rightPadding) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, rightPadding));
        return label;
    }
}
