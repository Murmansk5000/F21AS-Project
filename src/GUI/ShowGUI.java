package GUI;

import models.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowGUI extends JFrame {

    public void FlightCheckInGUI() {
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
        // mainPanel.add(createInputPanel("                First Name:", new JTextField(20)));
        mainPanel.add(createLoginPanel("              Last Name:", new JTextField(20)));
        mainPanel.add(createLoginPanel("Booking Reference:", new JTextField(20)));

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

    private JPanel createLoginPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel(label));
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
    }
    class BaggageDetailsGUI extends JFrame{
        public BaggageDetailsGUI(){
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
            mainPanel.add(createBaggageWeightPanel("Baggage 1:              Weight (kg)"));
            mainPanel.add(createDimensionPanel("        Dimensions (cm):"));

            // 添加第二个行李重量输入区域和它的尺寸输入区域
            mainPanel.add(createBaggageWeightPanel("Baggage 2:              Weight (kg)"));
            mainPanel.add(createDimensionPanel("        Dimensions (cm):"));

            // 添加第三个行李重量输入区域和它的尺寸输入区域
            mainPanel.add(createBaggageWeightPanel("Baggage 3:              Weight (kg):"));
            mainPanel.add(createDimensionPanel("        Dimensions (cm):"));
            // 创建行李重量输入区域（纵向排列）
            JPanel weightPanel = new JPanel();
            weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));

            // 创建下一步按钮
            JButton nextButton = new JButton("Next Step");
            nextButton.addActionListener(e -> {
                this.dispose(); // 关闭当前窗口
                // 计算额外费用，跳不同窗口
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(nextButton);
            mainPanel.add(buttonPanel);

            add(mainPanel);
        }
        private JPanel createBaggageWeightPanel(String labelText) {
            JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
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

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShowGUI showGUI = new ShowGUI();
            showGUI.FlightCheckInGUI();
            showGUI.setVisible(true);
        });

    }
}
