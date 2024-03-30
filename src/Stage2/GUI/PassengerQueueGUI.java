package Stage2.GUI;

import Stage1.Baggage;
import Stage1.Passenger;
import Stage2.Observer;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class PassengerQueueGUI extends JPanel implements Observer {
    private JPanel vipPanel;
    private JPanel regularPanel;
    private JPanel vipMainPanel;
    private JPanel regularMainPanel;
    private PassengerQueue vipQueue;
    private PassengerQueue regularQueue;

    public PassengerQueueGUI(PassengerQueue vipQueue, PassengerQueue regularQueue) {
        this.vipQueue = vipQueue;
        this.regularQueue = regularQueue;
        this.vipQueue.registerObserver(this);
        this.regularQueue.registerObserver(this);
        setSize(1300, 300);

        vipMainPanel = createQueuePanel(vipQueue, "VIP Queue");
        regularMainPanel = createQueuePanel(regularQueue, "Regular Queue");

        // 将抬头部分和乘客信息部分的面板添加到主面板
        this.add(vipMainPanel, BorderLayout.EAST);
        this.add(regularMainPanel, BorderLayout.WEST);
    }

    /*

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

        // Create the header panel with the title "Flight Code", "Passenger Name", "Baggage Weight" and "Baggage Volume".
        JPanel headerPanel = createHeaderPanel();

        JLabel queueLabel = new JLabel(title);
        queueLabel.setFont(new Font(queueLabel.getFont().getName(), Font.BOLD, 20));
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
        // 在这里更新 GUI 显示以反映新的乘客队列状态
        SwingUtilities.invokeLater(() -> {

            vipPanel.removeAll(); // 清空乘客信息部分的面板
            regularPanel.removeAll();

            // 重新加载乘客信息
            Iterator<Passenger> vipIterator = vipQueue.iterator();
            Iterator<Passenger> regularIterator = regularQueue.iterator();
            for (int i = 0; i < 20 && vipIterator.hasNext(); i++) {
                Passenger passenger = vipIterator.next();
                JPanel panel = new JPanel(new GridLayout(1, 4)); // 为每个乘客创建一个面板

                // 根据Passenger对象创建标签
                JLabel flightCode = new JLabel(passenger.getFlightCode());
                flightCode.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // 添加左边距
                JLabel passengerName = new JLabel(passenger.getName());
                JLabel baggageWeight;
                JLabel baggageVolume;

                if (!passenger.getHisBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getHisBaggageList().get(0);
                    String weightFormatted = String.format("%.2f", firstBaggage.getWeight());
                    //String volumeFormatted = String.format("%.2f", firstBaggage.getVolumePrint());

                    baggageWeight = new JLabel(weightFormatted);
                    baggageWeight.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                    baggageVolume = new JLabel(firstBaggage.getVolumePrint());
                    baggageVolume.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
                } else {
                    baggageWeight = new JLabel("N/A"); // 表示没有行李
                    baggageWeight.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                    baggageVolume = new JLabel("N/A");
                    baggageVolume.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                }

                // 将标签添加到乘客面板
                panel.add(flightCode);
                panel.add(passengerName);
                panel.add(baggageWeight);
                panel.add(baggageVolume);

                vipPanel.add(panel);
                vipPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

            }

            for (int i = 0; i < 20 && regularIterator.hasNext(); i++) {
                Passenger passenger = regularIterator.next();
                JPanel panel = new JPanel(new GridLayout(1, 4)); // 为每个乘客创建一个面板

                // 根据Passenger对象创建标签
                JLabel flightCode = new JLabel(passenger.getFlightCode());
                flightCode.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // 添加左边距
                JLabel passengerName = new JLabel(passenger.getName());
                JLabel baggageWeight;
                JLabel baggageVolume;

                if (!passenger.getHisBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getHisBaggageList().get(0);
                    String weightFormatted = String.format("%.2f", firstBaggage.getWeight());
                    //String volumeFormatted = String.format("%.2f", firstBaggage.getVolumePrint());

                    baggageWeight = new JLabel(weightFormatted);
                    baggageWeight.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                    baggageVolume = new JLabel(firstBaggage.getVolumePrint());
                    baggageVolume.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
                } else {
                    baggageWeight = new JLabel("N/A"); // 表示没有行李
                    baggageWeight.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                    baggageVolume = new JLabel("N/A");
                    baggageVolume.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                }

                // 将标签添加到乘客面板
                panel.add(flightCode);
                panel.add(passengerName);
                panel.add(baggageWeight);
                panel.add(baggageVolume);

                regularPanel.add(panel);
                regularPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            }
            this.revalidate();
            this.repaint();
        });
    }


}
