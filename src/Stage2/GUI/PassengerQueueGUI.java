package Stage2.GUI;

import Stage1.PassengerList;
import Stage2.PassengerQueue;
import Stage1.Passenger;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class PassengerQueueGUI extends JFrame {
    private JPanel mainPanel;
    private PassengerQueue passengerQueue;
    public PassengerQueueGUI() {
        //TODO 获取队列信息
        this.passengerQueue = new PassengerQueue();
        PassengerQueue queue = new PassengerQueue();
        setTitle("Passenger Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create header panel
        JPanel headerPanel = new JPanel(new GridLayout(0, 4));
        JLabel flightNumberLabel = new JLabel("Flight Code");
        JLabel passengerNameLabel = new JLabel("Passenger Name");
        JLabel luggageWeightLabel = new JLabel("Baggage Weight");
        JLabel luggageVolumeLabel = new JLabel("Baggage Volume");
        headerPanel.add(flightNumberLabel);
        headerPanel.add(passengerNameLabel);
        headerPanel.add(luggageWeightLabel);
        headerPanel.add(luggageVolumeLabel);
        mainPanel.add(headerPanel);

//        // Read passenger information from text file and display
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("passenger_info.txt"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 4) {
//                    JPanel passengerPanel = new JPanel(new GridLayout(0, 4)); // Panel for each passenger
//
//                    // Create labels for each piece of passenger information
//                    JLabel flightNumber = new JLabel(parts[0]);
//                    flightNumber.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Add left padding
//                    JLabel passengerName = new JLabel(parts[1]);
//                    JLabel luggageWeight = new JLabel(parts[2]);
//                    JLabel luggageVolume = new JLabel(parts[3]);
//
//                    // Add labels to passenger panel
//                    passengerPanel.add(flightNumber);
//                    passengerPanel.add(passengerName);
//                    passengerPanel.add(luggageWeight);
//                    passengerPanel.add(luggageVolume);
//
//                    mainPanel.add(passengerPanel);
//                } else {
//                    System.err.println("Invalid data format: " + line);
//                }
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 使用迭代器读取前五个乘客的信息
        // TODO 加载列表
        // 使用列表的 iterator() 方法来获取迭代器
        Iterator<Passenger> iterator = queue.iterator();

        int count = 0;
        while(iterator.hasNext() && count < 5) {
            Passenger passenger = iterator.next();

            JPanel passengerPanel = new JPanel(new GridLayout(0, 4)); // 为每个乘客创建一个面板

            // 根据Passenger对象创建标签
            JLabel flightCode = new JLabel(passenger.getFlightCode());
            flightCode.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // 添加左边距
            JLabel passengerName = new JLabel(passenger.getName());
            JLabel baggageWeight = new JLabel(String.valueOf(passenger.getBaggageWeight()));
            JLabel baggageVolume = new JLabel(String.valueOf(passenger.getBaggageVolume()));

            // 将标签添加到乘客面板
            passengerPanel.add(flightCode);
            passengerPanel.add(passengerName);
            passengerPanel.add(baggageWeight);
            passengerPanel.add(baggageVolume);

            mainPanel.add(passengerPanel);

            count++; // 增加计数器
        }

        // Add main panel to scroll pane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PassengerQueueGUI::new);
    }
}
