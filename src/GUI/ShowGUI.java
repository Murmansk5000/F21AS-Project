package GUI;

import models.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowGUI extends JFrame {
    private JTextField lastNameTextField;
    private JTextField referenceTextField;

    public void FlightCheckInGUI() {
        setTitle("Airport Check-in System");
        setSize(400, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        // 使用BoxLayout进行面板布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel("Welcome to Airport Check-in System", SwingConstants.LEFT);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        // 输入面板的统一方法，优化代码重用
        // mainPanel.add(createInputPanel("                First Name:", new JTextField(20)));
        mainPanel.add(createLoginPanel("               Last Name:", new JTextField(20)));
        mainPanel.add(createLoginPanel("Booking Reference:", new JTextField(20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        JButton finishButton = new JButton("Log in");
        finishButton.addActionListener(e -> {

            // 打开航班详情窗口（最终实现这里会有修改）

            String lastName = lastNameTextField.getText();
            String reference = referenceTextField.getText();

            this.dispose(); // 关闭当前窗口
            System.out.println((lastName));
            System.out.println(reference);

            new FlightDetailsGUI().setVisible(true);
        });

        buttonPanel.add(quitButton);
        buttonPanel.add(finishButton);

        // 添加按钮面板到主面板
        mainPanel.add(buttonPanel);
        // 添加主面板到Frame
        add(mainPanel);

    }

    public JPanel createLoginPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel(label));

        if (label.trim().equals("Last Name:")) {
            lastNameTextField = textField; // 为 "Last Name" 文本框设置对象
        } else if (label.trim().equals("Booking Reference:")) {
            referenceTextField = textField; // 为 "Booking Reference" 文本框设置对象
        }

        panel.add(textField);
        return panel;
    }

    class FlightDetailsGUI extends JFrame{
        public FlightDetailsGUI() {
            setTitle("Flight Details");
            setSize(400, 500); // 统一界面大小
            setLocationRelativeTo(null); // 居中显示
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel headerLabel = new JLabel("Your Flight Details", SwingConstants.LEFT);
            headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(headerLabel);

            //添加航班信息
            mainPanel.add(createDetailPanel("Flight Number: ", "EA123"));
            mainPanel.add(createDetailPanel("Carrier: ", "Example Airline"));
            mainPanel.add(createDetailPanel("Max Passenger: ", "220"));
            mainPanel.add(createDetailPanel("Airport of Departure: ", "Example Airport"));
            mainPanel.add(createDetailPanel("Airport of Destination: ", "Example Airport"));
            mainPanel.add(createDetailPanel("Estimated time of Departure: ", "00:00"));
            mainPanel.add(createDetailPanel("Estimated time of Arrival: ", "22:00"));
//            mainPanel.add(createDetailPanel("Max Baggage Weight: ", "18,000kg"));
//            mainPanel.add(createDetailPanel("Max Baggage Volume: ", "200m^3"));
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
    }
    private JTextField weightField1;
    private JTextField lengthField1;
    private JTextField widthField1;
    private JTextField heightField1;
    class BaggageDetailsGUI extends JFrame{
        private JTextField weightField1, weightField2, weightField3;
        private JTextField lengthField1, widthField1, heightField1;
        private JTextField lengthField2, widthField2, heightField2;
        private JTextField lengthField3, widthField3, heightField3;
        public BaggageDetailsGUI(){
            setTitle("Baggage Details");
            setSize(500, 500); // 统一界面大小
            setLocationRelativeTo(null); // 居中显示
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel headerLabel = new JLabel("Please Enter Your Baggage Details", SwingConstants.LEFT);
            headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(headerLabel);

            // 添加第一个行李重量输入区域和它的尺寸输入区域
            mainPanel.add(createBaggageWeightPanel("Baggage 1:      Weight (kg):", 1));
            mainPanel.add(createDimensionPanel("                         Dimensions (cm):", 1));

            // 添加第二个行李重量输入区域和它的尺寸输入区域
            mainPanel.add(createBaggageWeightPanel("Baggage 2:      Weight (kg):", 2));
            mainPanel.add(createDimensionPanel("                         Dimensions (cm):", 2));

            // 添加第三个行李重量输入区域和它的尺寸输入区域
            mainPanel.add(createBaggageWeightPanel("Baggage 3:      Weight (kg):", 3));
            mainPanel.add(createDimensionPanel("                         Dimensions (cm):", 3));
            // 创建行李重量输入区域（纵向排列）
            JPanel weightPanel = new JPanel();
            weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));

            // 创建下一步按钮
            JButton nextButton = new JButton("Next Step");
            nextButton.addActionListener(e -> {
                String baggage1Weight = weightField1.getText(); // 获取第一个行李重量的文本
                String baggage1Length = lengthField1.getText(); // 获取第一个行李长度的文本
                String baggage1Width = widthField1.getText(); // 获取第一个行李宽度的文本
                String baggage1Height = heightField1.getText(); // 获取第一个行李高度的文本

                String baggage2Weight = weightField2.getText(); // 获取第二个行李重量的文本
                String baggage2Length = lengthField2.getText(); // 获取第二个行李长度的文本
                String baggage2Width = widthField2.getText(); // 获取第二个行李宽度的文本
                String baggage2Height = heightField2.getText(); // 获取第二个行李高度的文本

                String baggage3Weight = weightField3.getText(); // 获取第三个行李重量的文本
                String baggage3Length = lengthField3.getText(); // 获取第三个行李长度的文本
                String baggage3Width = widthField3.getText(); // 获取第三个行李宽度的文本
                String baggage3Height = heightField3.getText(); // 获取第三个行李高度的文本

                System.out.println(baggage1Weight);
                System.out.println(baggage1Length);
                System.out.println(baggage1Width);
                System.out.println(baggage1Height);

                System.out.println(baggage2Weight);
                System.out.println(baggage2Length);
                System.out.println(baggage2Width);
                System.out.println(baggage2Height);

                System.out.println(baggage3Weight);
                System.out.println(baggage3Length);
                System.out.println(baggage3Width);
                System.out.println(baggage3Height);

                this.dispose(); // 关闭当前窗口
                // 计算额外费用，跳不同窗口



            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(nextButton);
            mainPanel.add(buttonPanel);

            add(mainPanel);
        }
        private JPanel createBaggageWeightPanel(String labelText, int baggageNumber) {
            JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            weightPanel.add(new JLabel(labelText));
            JTextField weightField = new JTextField(5);
            //weightPanel.add(new JTextField(5));

            weightPanel.add(weightField);

            switch (baggageNumber) {
                case 1:
                    weightField1 = weightField;
                    break;
                case 2:
                    weightField2 = weightField;
                    break;
                case 3:
                    weightField3 = weightField;
                    break;
            }
            return weightPanel;
        }

        private JPanel createDimensionPanel(String labelText, int baggageNumber) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(new JLabel(labelText));

            JTextField lengthField = new JTextField(3), widthField = new JTextField(3), heightField = new JTextField(3);
            panel.add(new JLabel("L:"));
            panel.add(lengthField);
            panel.add(new JLabel("W:"));
            panel.add(widthField);
            panel.add(new JLabel("H:"));
            panel.add(heightField);

            // 根据行李编号，将对应的长、宽、高文本框变量指向创建的文本框
            switch (baggageNumber) {
                case 1:
                    lengthField1 = lengthField;
                    widthField1 = widthField;
                    heightField1 = heightField;
                    break;
                case 2:
                    lengthField2 = lengthField;
                    widthField2 = widthField;
                    heightField2 = heightField;
                    break;
                case 3:
                    lengthField3 = lengthField;
                    widthField3 = widthField;
                    heightField3 = heightField;
                    break;
            }
            return panel;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShowGUI showGUI = new ShowGUI();
            showGUI.FlightCheckInGUI();
            showGUI.setVisible(true);
        });

    }
}
