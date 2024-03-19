package Stage2.GUI;

import Stage1.Baggage;
import Stage1.Passenger;
import Stage2.Observer;
import Stage2.PassengerQueue;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class PassengerQueueGUI extends JFrame implements Observer {
    private JPanel mainPanel;

    //private JPanel passengerPanel;
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
        setTitle("Passenger Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 400);

        mainPanel = new JPanel(new BorderLayout());

        // 创建抬头部分的面板
        createHeaderPanel();

        // 创建用于存放乘客信息部分的面板
        vipPanel = new JPanel();
        vipPanel.setLayout(new BoxLayout(vipPanel, BoxLayout.Y_AXIS));
        regularPanel = new JPanel();
        regularPanel.setLayout(new BoxLayout(regularPanel, BoxLayout.Y_AXIS));

        vipMainPanel = new JPanel(new BorderLayout());
        // 创建包含原始头部和 "VIP Queue" 标签的新面板
        JPanel vipHeaderPanel = new JPanel(new BorderLayout());
        JLabel vipLabel = new JLabel("VIP Queue");
        vipLabel.setHorizontalAlignment(SwingConstants.CENTER); // 居中显示 "VIP Queue" 文本
        vipHeaderPanel.add(vipLabel, BorderLayout.NORTH); // "VIP Queue" 标签位于北部
        vipHeaderPanel.add(createHeaderPanel(), BorderLayout.CENTER); // 原始头部面板位于中心

        vipMainPanel.add(vipHeaderPanel, BorderLayout.NORTH);
        vipMainPanel.add(vipPanel, BorderLayout.CENTER);

        regularMainPanel = new JPanel(new BorderLayout());
        JPanel regularHeaderPanel = new JPanel(new BorderLayout());
        JLabel regularLabel = new JLabel("Regular Queue");
        regularLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regularHeaderPanel.add(regularLabel, BorderLayout.NORTH);
        regularHeaderPanel.add(createHeaderPanel(), BorderLayout.CENTER); // 原始头部面板位于中心

        regularMainPanel.add(regularHeaderPanel, BorderLayout.NORTH);
        regularMainPanel.add(regularPanel, BorderLayout.CENTER);
        // 将抬头部分和乘客信息部分的面板添加到主面板
        mainPanel.add(vipMainPanel,BorderLayout.WEST);
        mainPanel.add(regularMainPanel, BorderLayout.EAST);

        add(mainPanel);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // 用GridLayout将四个标签平均分布
        JLabel flightNumberLabel = new JLabel("Flight Code");
        flightNumberLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        JLabel passengerNameLabel = new JLabel("Passenger Name");
        JLabel BaggageWeightLabel = new JLabel("Baggage Weight");
        JLabel BaggageVolumeLabel = new JLabel("Baggage Volume");
        BaggageVolumeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        headerPanel.add(flightNumberLabel);
        headerPanel.add(passengerNameLabel);
        headerPanel.add(BaggageWeightLabel);
        headerPanel.add(BaggageVolumeLabel);
        return headerPanel;
    }


    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

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
            for (int i = 0; i < 5 && vipIterator.hasNext(); i++) {
                Passenger passenger = vipIterator.next();
                JPanel panel = new JPanel(new GridLayout(1, 4)); // 为每个乘客创建一个面板

                // 根据Passenger对象创建标签
                JLabel flightCode = new JLabel(passenger.getFlightCode());
                //flightCode.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 添加左边距
                JLabel passengerName = new JLabel(passenger.getLastName());
                JLabel baggageWeight;
                JLabel baggageVolume;


                if (!passenger.getHisBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getHisBaggageList().get(0);
                    String weightFormatted = String.format("%.2f", firstBaggage.getWeight());
                    String volumeFormatted = String.format("%.2f", firstBaggage.getVolume());

                    baggageWeight = new JLabel(weightFormatted);
                    baggageVolume = new JLabel(volumeFormatted);
                } else {
                    baggageWeight = new JLabel("N/A"); // 表示没有行李
                    baggageVolume = new JLabel("N/A");
                }


                // 将标签添加到乘客面板
                panel.add(flightCode);
                panel.add(passengerName);
                panel.add(baggageWeight);
                panel.add(baggageVolume);

                vipPanel.add(panel);
                vipPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));

            }

            for (int i = 0; i < 5 && regularIterator.hasNext(); i++) {
                Passenger passenger = regularIterator.next();
                JPanel panel = new JPanel(new GridLayout(1, 4)); // 为每个乘客创建一个面板

                // 根据Passenger对象创建标签
                JLabel flightCode = new JLabel(passenger.getFlightCode());
                //flightCode.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // 添加左边距
                JLabel passengerName = new JLabel(passenger.getLastName());
                JLabel baggageWeight;
                JLabel baggageVolume;

                if (!passenger.getHisBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getHisBaggageList().get(0);
                    String weightFormatted = String.format("%.2f", firstBaggage.getWeight());
                    String volumeFormatted = String.format("%.2f", firstBaggage.getVolume());

                    baggageWeight = new JLabel(weightFormatted);
                    baggageVolume = new JLabel(volumeFormatted);
                } else {
                    baggageWeight = new JLabel("N/A"); // 表示没有行李
                    baggageVolume = new JLabel("N/A");
                }

                // 将标签添加到乘客面板
                panel.add(flightCode);
                panel.add(passengerName);
                panel.add(baggageWeight);
                panel.add(baggageVolume);

                regularPanel.add(panel);
                regularPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
                // 重新绘制 GUI

            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
//    public static void main(String[] args) {
//        // 创建 PassengerQueue 实例
//        PassengerQueue passengerQueue = new PassengerQueue();
//
//        // 使用 SwingUtilities.invokeLater 来确保 GUI 更新在正确的线程上执行
//        SwingUtilities.invokeLater(() -> new PassengerQueueGUI(passengerQueue));
//    }
    }
