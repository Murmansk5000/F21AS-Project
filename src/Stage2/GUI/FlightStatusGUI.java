package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage2.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class FlightStatusGUI extends JPanel implements Observer {
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 15);
    private final JPanel flightPanels;
    private final FlightList flightList;

    public FlightStatusGUI(FlightList flightList) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);

        setLayout(new BorderLayout());
        setSize(1300, 400);

        JLabel headerLabel = new JLabel("Flight Status", SwingConstants.CENTER);
        headerLabel.setFont(LABEL_FONT);
        headerLabel.setForeground(Color.BLACK);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.add(headerLabel);

        flightPanels = new JPanel(new GridLayout(2, 5, 5, 5));

        add(headerPanel, BorderLayout.NORTH);
        add(flightPanels, BorderLayout.CENTER);

        update();
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            flightPanels.removeAll();
            flightList.iterator().forEachRemaining(this::createAndAddFlightPanel);
            revalidate();
            repaint();
        });
    }

    private void createAndAddFlightPanel(Flight flight) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 2));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel flightCode = new JLabel(flight.getFlightCode() + ": " + flight.getCarrier(), SwingConstants.CENTER);
        flightCode.setFont(BOLD_FONT);

        JLabel checkIn = new JLabel(flight.getPassengerInFlight().checkInSize() + " checked in of " + flight.getPassengerInFlight().size(), SwingConstants.CENTER);

        double percentage = flight.getBaggageInFlight().getTotalVolume() / flight.getMaxBaggageVolume() * 100;
        JLabel percentageLabel = new JLabel("Hold is " + String.format("%.2f", percentage) + "% full", SwingConstants.CENTER);
        JLabel departureLabel = new JLabel(generateDepartureText(flight), SwingConstants.CENTER);

        panel.add(flightCode);
        panel.add(checkIn);
        panel.add(percentageLabel);
        panel.add(departureLabel);

        flightPanels.add(panel);
    }

    private String generateDepartureText(Flight flight) {
        if (flight.getIsTakenOff()) {
            return "Flight has taken off.";
        } else {
            long secondsUntilTakeoff = Duration.between(Instant.now(), flight.getTakeOffInstant()).getSeconds();
            long timeUntilTakeoffInMinutes = (long) Math.ceil(secondsUntilTakeoff / 60.0);
            if (timeUntilTakeoffInMinutes < 0) {
                return "Flight delays.";
            } else {
                return "Flight will take off in " + timeUntilTakeoffInMinutes + " minutes.";
            }
        }
    }
}

