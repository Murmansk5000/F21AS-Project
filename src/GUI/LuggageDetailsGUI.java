package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LuggageDetailsGUI extends JFrame {

    public LuggageDetailsGUI() {
        setTitle("行李信息");
        setSize(400, 500); // 统一界面大小
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Please enter your luggage Details", SwingConstants.CENTER);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        // 创建行李重量输入区域（纵向排列）
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));

        JPanel weightFieldPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        weightFieldPanel1.add(new JLabel("Baggage 1 Weight: "));
        weightFieldPanel1.add(new JTextField(5));
        weightPanel.add(weightFieldPanel1);

        JPanel weightFieldPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        weightFieldPanel2.add(new JLabel("Baggage 2 Weight:"));
        weightFieldPanel2.add(new JTextField(5));
        weightPanel.add(weightFieldPanel2);

        JPanel weightFieldPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        weightFieldPanel3.add(new JLabel("Baggage 3 Weight:"));
        weightFieldPanel3.add(new JTextField(5));
        weightPanel.add(weightFieldPanel3);

        mainPanel.add(weightPanel);

        // 创建维度输入区域
        mainPanel.add(createDimensionPanel("Dimensions for Weight 1:"));
        mainPanel.add(createDimensionPanel("Dimensions for Weight 2:"));
        mainPanel.add(createDimensionPanel("Dimensions for Weight 3:"));

        // 创建下一步按钮
        JButton nextStepButton = new JButton("Next Step");
        nextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到下一个页面的逻辑
                JOptionPane.showMessageDialog(LuggageDetailsGUI.this,
                        "Next Step logic goes here.",
                        "Next Step", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextStepButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private JPanel createDimensionPanel(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.add(new JLabel(label));

        JTextField lengthField = new JTextField(3);
        JTextField widthField = new JTextField(3);
        JTextField heightField = new JTextField(3);

        panel.add(new JLabel("L:"));
        panel.add(lengthField);
        panel.add(new JLabel("W:"));
        panel.add(widthField);
        panel.add(new JLabel("H:"));
        panel.add(heightField);

        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LuggageDetailsGUI().setVisible(true));
    }
}
