package Stage2.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CheckInSimulation extends JFrame {
    private JPanel counter1Panel;
    private JPanel counter2Panel;
    private JLabel counter1Label;
    private JLabel counter2Label;
    private Queue<String> passengerQueue; // Store passenger information as strings
    private Timer processTimer; // Timer to process passengers
    private int remainingPassengers; // Number of remaining passengers in the queue

    public CheckInSimulation(Queue<String> passengerQueue) {
        setTitle("Check-in Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);

        // Initialize passenger queue
        this.passengerQueue = passengerQueue;
        this.remainingPassengers = passengerQueue.size();

        // Create counter panels
        counter1Panel = new JPanel(new BorderLayout());
        counter2Panel = new JPanel(new BorderLayout());
        counter1Label = new JLabel("Counter 1: No passenger", SwingConstants.CENTER);
        counter2Label = new JLabel("Counter 2: No passenger", SwingConstants.CENTER);
        counter1Panel.add(counter1Label, BorderLayout.CENTER);
        counter2Panel.add(counter2Label, BorderLayout.CENTER);

        // Add counter panels to main frame
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(counter1Panel);
        mainPanel.add(counter2Panel);
        add(mainPanel);

        // Start processing passengers
        processPassengers();

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void processPassengers() {
        processTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there are remaining passengers
                if (!passengerQueue.isEmpty()) {
                    String passengerInfo = passengerQueue.poll();
                    if (counter1Label.getText().equals("Counter 1: No passenger")) {
                        processPassenger(counter1Label, passengerInfo);
                    } else if (counter2Label.getText().equals("Counter 2: No passenger")) {
                        processPassenger(counter2Label, passengerInfo);
                    }
                    remainingPassengers--; // Decrement remaining passengers
                }

                // Check if all passengers have finished queuing
                if (remainingPassengers == 0) {
                    // No more passengers, display "Closed"
                    counter1Label.setText("Counter 1: Closed");
                    counter2Label.setText("Counter 2: Closed");
                    processTimer.stop(); // Stop the timer
                } else {
                    processTimer.restart(); // Restart the timer
                }
            }
        });
        processTimer.setInitialDelay(0); // Start immediately
        processTimer.setRepeats(false);
        processTimer.start();
    }

    private void processPassenger(JLabel counterLabel, String passengerInfo) {
        String[] parts = passengerInfo.split(",");
        String passengerName = parts[0];
        String luggageWeight = parts[1];

        counterLabel.setText("<html>" +
                "Counter: " + passengerName + "<br>" +
                "Luggage weight: " + luggageWeight + " kg" + "<br>" +
                "</html>");
    }

    public static void main(String[] args) {
        Queue<String> passengerQueue = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("passenger_baggage.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                passengerQueue.offer(line); // Add passenger info to the queue
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new CheckInSimulation(passengerQueue); // Ensure CheckInSimulation is instantiated with passengerQueue
    }

}
