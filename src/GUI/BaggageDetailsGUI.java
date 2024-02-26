package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Passenger;

public class BaggageDetailsGUI extends JFrame {

    public BaggageDetailsGUI() {
        setTitle("Baggage Details");
        setSize(500, 500); // 统一界面大小
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Please Enter Your Baggage Details", SwingConstants.CENTER);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        // 添加第一个行李重量输入区域和它的尺寸输入区域
        mainPanel.add(createBaggageWeightPanel("Baggage 1 Weight(kg):"));
        mainPanel.add(createDimensionPanel("        Dimensions for Baggage 1(cm):"));

        // 添加第二个行李重量输入区域和它的尺寸输入区域
        mainPanel.add(createBaggageWeightPanel("Baggage 2 Weight(kg):"));
        mainPanel.add(createDimensionPanel("        Dimensions for Baggage 2(cm):"));

        // 添加第三个行李重量输入区域和它的尺寸输入区域
        mainPanel.add(createBaggageWeightPanel("Baggage 3 Weight(kg):"));
        mainPanel.add(createDimensionPanel("        Dimensions for Baggage 3(cm):"));
        // 创建行李重量输入区域（纵向排列）
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));

        // 创建下一步按钮
        JButton nextStepButton = new JButton("Next Step");
        nextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到下一个页面的逻辑
                JOptionPane.showMessageDialog(BaggageDetailsGUI.this,
                        "Next Step logic goes here.",
                        "Next Step", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextStepButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }
    private JPanel createBaggageWeightPanel(String labelText) {
        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        weightPanel.add(new JLabel(labelText));
        weightPanel.add(new JTextField(5));
        return weightPanel;
    }

    private JPanel createDimensionPanel(String label) {
        JPanel panel = new JPanel(new GridBagLayout()); // 改用GridBagLayout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2); // 设置组件间的间距

        constraints.gridx = 0; // 第一列
        panel.add(new JLabel(label), constraints);

        constraints.gridx = 1; // 第三列
        panel.add(new JLabel("L:"), constraints);

        constraints.gridx = 2; // 第二列
        JTextField lengthField = new JTextField(3);
        panel.add(lengthField, constraints);

        constraints.gridx = 3; // 第五列
        panel.add(new JLabel("W:"), constraints);

        constraints.gridx = 4; // 第四列
        JTextField widthField = new JTextField(3);
        panel.add(widthField, constraints);

        constraints.gridx = 5; // 第七列
        panel.add(new JLabel("H:"), constraints);

        constraints.gridx = 6; // 第六列
        JTextField heightField = new JTextField(3);
        panel.add(heightField, constraints);

        return panel;
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new BaggageDetailsGUI().setVisible(true));
    }
}