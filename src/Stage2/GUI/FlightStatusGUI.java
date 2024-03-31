package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage2.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

/**
 * FlightStatusGUI class represents the graphical user interface for displaying flight statuses.
 * It observes changes in the FlightList and updates the display accordingly.
 */
public class FlightStatusGUI extends JPanel implements Observer {
    // Constants
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 15);

    // Member variables
    private final JPanel flightPanels;
    private final FlightList flightList;

    /**
     * Constructs a FlightStatusGUI object with the specified FlightList.
     *
     * @param flightList The FlightList to observe for changes.
     */
    public FlightStatusGUI(FlightList flightList) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);

        // Set layout and size
        setLayout(new BorderLayout());
        setSize(1300, 400);

        // Create and configure header label
        JLabel headerLabel = new JLabel("Flight Status", SwingConstants.CENTER);
        headerLabel.setFont(LABEL_FONT);
        headerLabel.setForeground(Color.BLACK);

        // Create header panel and add header label
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.add(headerLabel);

        // Create flight panels to display flight information
        flightPanels = new JPanel(new GridLayout(2, 5, 5, 5));

        // Add components to the layout
        add(headerPanel, BorderLayout.NORTH);
        add(flightPanels, BorderLayout.CENTER);

        // Update the GUI
        update();
    }

    /**
     * Updates the GUI to reflect changes in the FlightList.
     */
    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            flightPanels.removeAll();
            flightList.iterator().forEachRemaining(this::createAndAddFlightPanel);
            revalidate();
            repaint();
        });
    }

    /**
     * Creates a panel to display information about a flight and adds it to the flightPanels.
     *
     * @param flight The flight to display information about.
     */
    private void createAndAddFlightPanel(Flight flight) {
        // Create a panel for the flight
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 2));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create and configure flight code label
        JLabel flightCode = new JLabel(flight.getFlightCode() + ": " + flight.getCarrier(), SwingConstants.CENTER);
        flightCode.setFont(BOLD_FONT);

        // Create and configure check-in label
        JLabel checkIn = new JLabel(flight.getPassengerInFlight().checkInSize() + " checked in of " + flight.getPassengerInFlight().size(), SwingConstants.CENTER);

        // Calculate and display hold percentage
        double percentage = flight.getBaggageInFlight().getTotalVolume() / flight.getMaxBaggageVolume() * 100;
        JLabel percentageLabel = new JLabel("Hold is " + String.format("%.2f", percentage) + "% full", SwingConstants.CENTER);

        // Generate and display departure text
        JLabel departureLabel = new JLabel(generateDepartureText(flight), SwingConstants.CENTER);

        // Add components to the panel
        panel.add(flightCode);
        panel.add(checkIn);
        panel.add(percentageLabel);
        panel.add(departureLabel);

        // Add the panel to the flightPanels
        flightPanels.add(panel);
    }

    /**
     * Generates text indicating the departure status of the flight.
     *
     * @param flight The flight to generate departure text for.
     * @return The departure text.
     */
    private String generateDepartureText(Flight flight) {
        if (flight.getIsTakenOff()) {
            return "Flight has taken off.";
        } else {
            long secondsUntilTakeoff = Duration.between(Instant.now(), flight.getTakeOffInstant()).getSeconds();
            if (secondsUntilTakeoff < 0) {
                return "Flight was delayed or overloaded.";
            } else {
                long timeUntilTakeoffInMinutes = (long) Math.ceil(secondsUntilTakeoff / 60.0);
                return "Flight will take off in " + timeUntilTakeoffInMinutes + " minutes.";
            }
        }
    }
}
