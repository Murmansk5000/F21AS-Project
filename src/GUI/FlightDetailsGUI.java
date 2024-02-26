package GUI;
import javax.swing.*;
import java.awt.*;

// 现有的FlightCheckInGUI类...

public class FlightDetailsGUI extends JFrame {

    public FlightDetailsGUI() {
        setTitle("航班信息");
        setSize(400, 500); // 统一界面大小
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Here is your flight details", SwingConstants.LEFT);
        mainPanel.add(headerLabel);

        //添加航班信息
        mainPanel.add(createDetailPanel("Arrival Airport: ", "Example Airport"));
        mainPanel.add(createDetailPanel("Carrier: ", "Example Airline"));
        mainPanel.add(createDetailPanel("Max Passenger: ", "220"));
        mainPanel.add(createDetailPanel("Max Weight: ", "18,000kg"));
        mainPanel.add(createDetailPanel("Max Volume: ", "200m^3"));
        mainPanel.add(createDetailPanel("Flight Number: ", "EA123"));

        JButton nextButton = new JButton("Next Step");
        nextButton.addActionListener(e -> {
            this.dispose(); // 关闭当前窗口
            // 打开航班详情窗口（最终实现这里会有修改）
            new BaggageDetailsGUI().setVisible(true);
        });

        // 为了在下方右对齐按钮，用FlowLayout管理器
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);

        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    private JPanel createDetailPanel(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
        return panel;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FlightDetailsGUI().setVisible(true));
    }
    // Omitted the rest of the class for brevity...
}
