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
    private JScrollPane scrollPane;

    public PassengerQueueGUI(PassengerQueue vipQueue, PassengerQueue regularQueue) {
        this.vipQueue = vipQueue;
        this.regularQueue = regularQueue;
        this.vipQueue.registerObserver(this);
        this.regularQueue.registerObserver(this);
        setTitle("Passenger Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 200);

        mainPanel = new JPanel(new BorderLayout());

        // 创建抬头部分的面板
        createHeaderPanel();

        // 创建用于存放乘客信息部分的面板
        vipPanel = new JPanel();
        vipPanel.setLayout(new GridLayout(20, 1, 10, 10)); // 使用 GridLayout，垂直方向自动扩展

        regularPanel = new JPanel();
        regularPanel.setLayout(new GridLayout(20, 1, 10, 10));

// 创建 JScrollPane 来包装 vipPanel，以便添加垂直滚动条
        JScrollPane vipScrollPane = new JScrollPane(vipPanel);
        vipScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vipScrollPane.setPreferredSize(new Dimension(600, 400)); // 设置滚动面板大小

// 创建 JScrollPane 来包装 regularPanel，以便添加垂直滚动条
        JScrollPane regularScrollPane = new JScrollPane(regularPanel);
        regularScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        regularScrollPane.setPreferredSize(new Dimension(600, 200)); // 设置滚动面板大小

        vipMainPanel = new JPanel(new BorderLayout());
        // 创建包含原始头部和 "VIP Queue" 标签的新面板
        JPanel vipHeaderPanel = new JPanel(new BorderLayout());
        JLabel vipLabel = new JLabel("VIP Queue");
        Font boldFont = new Font(vipLabel.getFont().getName(), Font.BOLD, (int) (vipLabel.getFont().getSize() * 1.3)); // 将字体大小增大为原来大小的1.5倍
        vipLabel.setFont(boldFont); // 设置字体为加粗且增大字号
        vipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vipLabel.setForeground(Color.BLACK); // 设置字体颜色为黑色
        vipHeaderPanel.add(vipLabel, BorderLayout.NORTH); // "VIP Queue" 标签位于北部
        vipHeaderPanel.add(createHeaderPanel(), BorderLayout.CENTER); // 原始头部面板位于中心

        vipMainPanel.add(vipHeaderPanel, BorderLayout.NORTH);
        vipMainPanel.add(vipScrollPane, BorderLayout.CENTER);

        regularMainPanel = new JPanel(new BorderLayout());
        JPanel regularHeaderPanel = new JPanel(new BorderLayout());
        JLabel regularLabel = new JLabel("Regular Queue");
        regularLabel.setFont(boldFont); // 设置字体为加粗且增大字号
        regularLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regularLabel.setForeground(Color.BLACK); // 设置字体颜色为黑色
        regularHeaderPanel.add(regularLabel, BorderLayout.NORTH);
        regularHeaderPanel.add(createHeaderPanel(), BorderLayout.CENTER); // 原始头部面板位于中心

        regularMainPanel.add(regularHeaderPanel, BorderLayout.NORTH);
        regularMainPanel.add(regularScrollPane, BorderLayout.CENTER);
        // 将抬头部分和乘客信息部分的面板添加到主面板
        mainPanel.add(vipMainPanel, BorderLayout.EAST);
        mainPanel.add(regularMainPanel, BorderLayout.WEST);

        add(mainPanel);
        setLocationRelativeTo(null); // Center the frame
//        setVisible(true);
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


                if (!passenger.getTheBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getTheBaggageList().get(0);
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

                if (!passenger.getTheBaggageList().isEmpty()) {
                    Baggage firstBaggage = passenger.getTheBaggageList().get(0);
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
                // 重新绘制 GUI

            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }


//    public static void main(String[] args) {
//        PassengerQueue passengerQueue = new PassengerQueue();
//
//        SwingUtilities.invokeLater(() -> new PassengerQueueGUI(passengerQueue));
//    }
}
