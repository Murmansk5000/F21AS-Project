package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightCheckInGUI extends JFrame {

    public FlightCheckInGUI() {
        setTitle("Welcome to airport check-in system");
        setSize(400, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        // 使用BoxLayout进行面板布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Welcome to airport check-in system", SwingConstants.CENTER);
        mainPanel.add(headerLabel);

        // 输入面板的统一方法，优化代码重用
        mainPanel.add(createInputPanel("                Last Name:", new JTextField(20)));
        mainPanel.add(createInputPanel("                First Name:", new JTextField(20)));
        mainPanel.add(createInputPanel("Booking Reference:", new JTextField(20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> {
            this.dispose(); // 关闭当前窗口
            // 打开航班详情窗口（最终实现这里会有修改）
            new FlightDetailsGUI().setVisible(true);
        });

        buttonPanel.add(quitButton);
        buttonPanel.add(finishButton);

        // 添加按钮面板到主面板
        mainPanel.add(buttonPanel);
        // 添加主面板到Frame
        add(mainPanel);
    }

    private JPanel createInputPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FlightCheckInGUI().setVisible(true));
    }
}
