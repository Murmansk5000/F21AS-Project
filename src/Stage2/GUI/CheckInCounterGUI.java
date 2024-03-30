package Stage2.GUI;

import Stage1.Passenger;
import Stage2.CheckInCounter;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckInCounterGUI extends JPanel implements Observer {
    private JPanel allVipPanel;
    private JPanel allRegularPanel;
    private List<CheckInCounter> counters;

    public CheckInCounterGUI(List<CheckInCounter> counters) {
        this.counters = counters;
        this.setLayout(new GridBagLayout());
        this.setSize(1300, 250);

        allVipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        allRegularPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        initCounterPanels();
    }


    private void initCounterPanels() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // 让组件完全填充其显示区域
        gbc.weighty = 1.0; // 在垂直方向上，组件都可以扩展

        // 创建常规柜台和VIP柜台的面板
        allRegularPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        allVipPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // 常规柜台面板设置
        gbc.gridx = 0; // 第一列
        gbc.weightx = 0.7; // 常规柜台的权重
        this.add(createMainPanel("Regular Counter", allRegularPanel), gbc);
        // VIP柜台面板设置
        gbc.gridx = 1; // 第二列
        gbc.weightx = 0.3; // VIP柜台的权重
        this.add(createMainPanel("VIP Counter", allVipPanel), gbc);
    }


    private JPanel createMainPanel(String title, JPanel panel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 20));
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return mainPanel;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            updateCounterDisplay(allVipPanel, true);
            updateCounterDisplay(allRegularPanel, false);

            this.revalidate();
            this.repaint();
        });
    }

    private void updateCounterDisplay(JPanel parentPanel, boolean isVip) {
        parentPanel.removeAll();

        for (CheckInCounter counter : counters) {
            if (counter.isVIP() == isVip && counter.getStatus()) {
                JPanel counterPanel = createCounterPanel(counter);
                parentPanel.add(counterPanel);
            }
        }
    }

    private JPanel createCounterPanel(CheckInCounter counter) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 100));

        JLabel counterIdLabel = new JLabel("Counter ID: " + counter.getCounterId());
        panel.add(counterIdLabel);

        Passenger passenger = counter.getCurrentPassenger();
        if (passenger != null) {
            JLabel passengerNameLabel = new JLabel("Passenger: " + passenger.getName());
            String weight = String.format("%.2f", passenger.getHisBaggageList().getTotalWeight());
            JLabel passengerBaggage = new JLabel("Baggage weight: " + weight);
            String fee = String.format("%.2f", passenger.getHisBaggageList().getTotalFee());
            JLabel baggageFee = new JLabel("Fee: " + fee);
            panel.add(passengerNameLabel);
            panel.add(passengerBaggage);
            panel.add(baggageFee);
        } else {
            JLabel noPassengerLabel = new JLabel("No current passenger");
            panel.add(noPassengerLabel);
        }

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }
}
