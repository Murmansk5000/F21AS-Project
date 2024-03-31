package Stage2.GUI;

import Stage1.Passenger;
import Stage2.CheckInCounter;
import Stage2.CheckInCounterManager;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * CheckInCounterGUI class represents the graphical user interface for displaying
 * check-in counters, both regular counters and VIP counters.
 */
public class CheckInCounterGUI extends JPanel implements Observer {
    // Constants
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    // Member variables
    private final JPanel allVipPanel;
    private final JPanel allRegularPanel;
    private final List<CheckInCounter> counters;
    private final double percentage;

    /**
     * Constructs a CheckInCounterGUI object with the specified list of check-in counters.
     *
     * @param counters The list of check-in counters to display.
     */
    public CheckInCounterGUI(List<CheckInCounter> counters) {
        this.counters = counters;
        percentage = (double) CheckInCounterManager.getMAX_REGULAR_COUNTER() / (double) CheckInCounterManager.getMaxCounters();
        setLayout(new BorderLayout());
        this.setSize(1300, 120);

        allVipPanel = new JPanel();
        allVipPanel.setLayout(new BoxLayout(allVipPanel, BoxLayout.X_AXIS));
        allRegularPanel = new JPanel();
        allRegularPanel.setLayout(new BoxLayout(allRegularPanel, BoxLayout.X_AXIS));

        initCounterPanels();
    }

    private void initCounterPanels() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createMainPanel("Regular Counter", new JScrollPane(allRegularPanel)));
        splitPane.setRightComponent(createMainPanel("VIP Counter", new JScrollPane(allVipPanel)));
        splitPane.setResizeWeight(percentage);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Creates a main panel for displaying regular or VIP counters.
     *
     * @param title      The title of the panel.
     * @param scrollPane The scroll pane containing the counter panels.
     * @return The main panel.
     */
    private JPanel createMainPanel(String title, Component scrollPane) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(LABEL_FONT);

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return mainPanel;
    }

    /**
     * Updates the GUI to reflect changes in the check-in counters.
     */
    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            updateCounterDisplay(allVipPanel, true);
            updateCounterDisplay(allRegularPanel, false);
            revalidate();
            repaint();
        });
    }

    /**
     * Updates the display of counter panels based on the list of check-in counters.
     *
     * @param parentPanel The parent panel to which the counter panels will be added.
     * @param isVip       A boolean indicating whether the counter is VIP or not.
     */
    private void updateCounterDisplay(JPanel parentPanel, boolean isVip) {
        parentPanel.removeAll();
        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVip && counter.getStatus()) {
                JPanel counterPanel = createCounterPanel(counter);
                parentPanel.add(counterPanel);
            }
        }
    }

    /**
     * Creates a panel to display information about a check-in counter.
     *
     * @param counter The check-in counter.
     * @return The panel displaying information about the counter.
     */
    private JPanel createCounterPanel(CheckInCounter counter) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;

        JLabel counterIdLabel = new JLabel("Counter ID: " + counter.getCounterId());
        gbc.gridy = 0;
        panel.add(counterIdLabel, gbc);

        Passenger passenger = counter.getCurrentPassenger();
        if (passenger != null) {
            JLabel passengerNameLabel = new JLabel("Passenger: " + passenger.getName());
            gbc.gridy++;
            panel.add(passengerNameLabel, gbc);

            String weight = String.format("%.2f", passenger.getHisBaggageList().getTotalWeight());
            JLabel passengerBaggage = new JLabel("Baggage weight: " + weight);
            gbc.gridy++;
            panel.add(passengerBaggage, gbc);

            String fee = String.format("%.2f", passenger.getHisBaggageList().getTotalFee());
            JLabel baggageFee = new JLabel("Fee: " + fee);
            gbc.gridy++;
            panel.add(baggageFee, gbc);
        } else {
            JLabel noPassengerLabel = new JLabel("No current passenger");
            gbc.gridy++;
            panel.add(noPassengerLabel, gbc);
        }

        panel.setPreferredSize(new Dimension(150, 100));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }
}
