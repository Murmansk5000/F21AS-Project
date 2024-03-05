package GUI;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GUI extends JFrame {
    //    private Baggage oneBaggage;
//    private BaggageList baggageListOfOne;
    private static Passenger selectedPassenger;
    private boolean shouldExit = false;
    private JTextField lastNameTextField;
    private JTextField referenceTextField;
    private PassengerList passengerList;
    private FlightList flightList;
    private Flight selectFlight;

    public GUI(PassengerList passengerList, FlightList flightList) {
        this.passengerList = passengerList;
        this.flightList = flightList;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    onExit();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //throw new RuntimeException(ex);
            }
        });
    }

    public PassengerList getPassengerList() {
        return this.passengerList;
    }

    public FlightList getFlightList() {
        return this.flightList;
    }

    public boolean getShouldExit() {
        return shouldExit;
    }

    private void onExit() throws IOException {
        this.flightList.renewBaggageInFlight();
        Report report = new Report(this.flightList);
    }

    public void FlightCheckInGUI() {


        setTitle("Airport Check-in System");
        setSize(400, 300); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        // Load background image
//        JPanel backgroundPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                // 绘制背景图像
//                ImageIcon imageIcon = new ImageIcon("images/background.jpg");
//                Image image = imageIcon.getImage();
//                g.drawImage(image, 0, 0, this);
//            }
//        };
//        setContentPane(backgroundPanel);


        // 使用BoxLayout进行面板布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //mainPanel.setOpaque(false);
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
        quitButton.addActionListener(e -> {
            try {
                onExit();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        });

        JButton finishButton = new JButton("Log In");
        finishButton.addActionListener(e -> {

            // 打开航班详情窗口（最终实现这里会有修改）

            String lastName = lastNameTextField.getText();
            String reference = referenceTextField.getText();
            try {
                validateInputs();
                selectedPassenger = passengerList.findByRefCode(reference);
                selectedPassenger = passengerList.findByLastName(lastName);
                if (selectedPassenger.getLastName().equals(lastName)) {
                    // If inputs are valid and correct, proceed to the next step
                    new FlightDetailsGUI(lastName, reference, passengerList, flightList).setVisible(true);
                    dispose(); // Close the current window
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (AllExceptions.NoMatchingRefException ex) {
                ex.printStackTrace();
            } catch (AllExceptions.NoMatchingNameException ex) {
                ex.printStackTrace();
            } catch (AllExceptions.NoMatchingFlightException ex) {
                ex.printStackTrace();
            }

        });

        buttonPanel.add(quitButton);
        buttonPanel.add(finishButton);

        // 添加按钮面板到主面板
        mainPanel.add(buttonPanel);
        // 添加主面板到Frame
        add(mainPanel);

    }

    private void validateInputs() throws IllegalArgumentException {
        String lastName = lastNameTextField.getText().trim();
        String reference = referenceTextField.getText().trim();

        if (lastName.isEmpty() || reference.isEmpty()) {
            // Show dialog to remind passenger
            JOptionPane.showMessageDialog(null, "Last Name and Booking Reference cannot be empty.\n", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Last Name and Booking Reference cannot be empty.");
        }
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

    class FlightDetailsGUI extends JFrame {

        private String lastName;
        private String reference;

        public FlightDetailsGUI(String lastName, String reference, PassengerList passengerList, FlightList flightList) throws AllExceptions.NoMatchingRefException, AllExceptions.NoMatchingFlightException {
            this.lastName = lastName;
            this.reference = reference;

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

            String flightCode = selectedPassenger.getFlightCode();
            selectFlight = flightList.findByCode(flightCode);
            if (selectFlight != null) {
                selectedPassenger = selectFlight.getPassengerInFlight().findByRefCode(reference);
                //添加航班信息
                mainPanel.add(createDetailPanel("Flight Number: ", selectFlight.getFlightCode()));
                mainPanel.add(createDetailPanel("Carrier: ", selectFlight.getCarrier()));
                mainPanel.add(createDetailPanel("Flight Max Passenger: ", String.valueOf(selectFlight.getMaxPassengers())));
                mainPanel.add(createDetailPanel("Flight Max Baggage Weight: ", String.valueOf(selectFlight.getMaxBaggageWeight())+" kg"));
                mainPanel.add(createDetailPanel("Flight Max Baggage Volume: ", String.valueOf(selectFlight.getMaxBaggageVolume()/1000000)+" cubic meters"));
                mainPanel.add(createDetailPanel("Your Purchased Baggage Weight: ", String.valueOf(selectFlight.getBaggageInFlight().getWeightLimit())));
            } else {
                throw new AllExceptions.NoMatchingFlightException();
            }


            JButton nextButton = new JButton("Next Step");
            JButton backButton = new JButton("Back");
            nextButton.addActionListener(e -> {
                this.dispose(); // 关闭当前窗口
                // 打开航班详情窗口（最终实现这里会有修改）
                new BaggageDetailsGUI().setVisible(true);
            });
            backButton.addActionListener(e -> {
                this.dispose();
                GUI gui = new GUI(passengerList, flightList);
                gui.FlightCheckInGUI();
                gui.setVisible(true);
            });
            // 为了在下方右对齐按钮，用FlowLayout管理器
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(backButton);
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


    class BaggageDetailsGUI extends JFrame {
        private double totalWeight = 0;
        private double totalVolume = 0;
        private double totalFee = 0;
        private JTextField
                weightField1, lengthField1, widthField1, heightField1,
                weightField2, lengthField2, widthField2, heightField2,
                weightField3, lengthField3, widthField3, heightField3;

        public BaggageDetailsGUI() {

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

            JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton clearBaggageButton = new JButton("Clear Baggage");
            clearBaggageButton.addActionListener(e -> removeBaggage());
            clearBaggageButton.addActionListener(e -> clearBaggageFields());
            buttonPanel1.add(clearBaggageButton);
            mainPanel.add(buttonPanel1);
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

                // 先尝试将所有行李信息添加到一个临时的行李列表中
                BaggageList tempBaggageList = selectedPassenger.getBaggageList();
                try {
                    if (isValidBaggage(weightField1, lengthField1, widthField1, heightField1)) {
                        Baggage tempBaggage = new Baggage(
                                Double.parseDouble(weightField1.getText()),
                                Double.parseDouble(lengthField1.getText()),
                                Double.parseDouble(widthField1.getText()),
                                Double.parseDouble(heightField1.getText())
                        );
                        tempBaggageList.addBaggage(tempBaggage);
                    }

                    if (isValidBaggage(weightField2, lengthField2, widthField2, heightField2)) {
                        Baggage tempBaggage = new Baggage(
                                Double.parseDouble(weightField2.getText()),
                                Double.parseDouble(lengthField2.getText()),
                                Double.parseDouble(widthField2.getText()),
                                Double.parseDouble(heightField2.getText())
                        );
                        tempBaggageList.addBaggage(tempBaggage);
                    }

                    if (isValidBaggage(weightField3, lengthField3, widthField3, heightField3)) {
                        Baggage tempBaggage = new Baggage(
                                Double.parseDouble(weightField3.getText()),
                                Double.parseDouble(lengthField3.getText()),
                                Double.parseDouble(widthField3.getText()),
                                Double.parseDouble(heightField3.getText())
                        );
                        tempBaggageList.addBaggage(tempBaggage);
                    }

                    selectedPassenger.setBaggageList(tempBaggageList);
                    totalVolume = tempBaggageList.getTotalVolume();
                    totalWeight = tempBaggageList.getTotalWeight();
                    totalFee = tempBaggageList.getTotalFee();

                    if (totalFee > 0) {
                        dispose();
                        new PaymentExtraFeeGUI(totalWeight, totalFee).setVisible(true);
                    } else {
                        new CongratsPaymentGUI(totalWeight).setVisible(true);
                        dispose();
                    }

                } catch (AllExceptions.InvalidBaggageInfoException ex) {
                    ex.printStackTrace();
                } catch (AllExceptions.IncompleteBaggageInfoException ex) {
                    // 处理单个行李信息不全异常
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    // 处理数字格式异常，例如用户未输入有效的数字等情况
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (AllExceptions.FormatErrorException ex) {
                    // 处理格式错误异常
                    ex.printStackTrace();
                }

            });
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                this.dispose();
                String lastName = selectedPassenger.getLastName();
                String reference = selectedPassenger.getRefCode();
                try {
                    new FlightDetailsGUI(lastName, reference, passengerList, flightList).setVisible(true);
                } catch (AllExceptions.NoMatchingRefException ex) {
                    throw new RuntimeException(ex);
                } catch (AllExceptions.NoMatchingFlightException ex) {
                    throw new RuntimeException(ex);
                }
            });

            JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel2.add(backButton);
            buttonPanel2.add(nextButton);
            mainPanel.add(buttonPanel2);

            add(mainPanel);
        }

        private static void removeBaggage() {
            // 假设 selectedPassenger 是当前选定的乘客对象
            if (selectedPassenger != null && selectedPassenger.getBaggageList() != null) {
                selectedPassenger.getBaggageList().clear();
                JOptionPane.showMessageDialog(null, "All baggage has been cleared.", "Baggage Cleared", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Clear the entered baggage information
        private void clearBaggageFields() {
            weightField1.setText("");
            lengthField1.setText("");
            widthField1.setText("");
            heightField1.setText("");

            weightField2.setText("");
            lengthField2.setText("");
            widthField2.setText("");
            heightField2.setText("");

            weightField3.setText("");
            lengthField3.setText("");
            widthField3.setText("");
            heightField3.setText("");
        }


        // 一个辅助方法，用来检查行李信息是否有效
        private boolean isValidBaggage(JTextField weightField, JTextField lengthField, JTextField widthField, JTextField heightField) throws AllExceptions.IncompleteBaggageInfoException, AllExceptions.InvalidBaggageInfoException {
            String weight = weightField.getText().trim();
            String length = lengthField.getText().trim();
            String width = widthField.getText().trim();
            String height = heightField.getText().trim();
            if (weight.isEmpty() &&
                    length.isEmpty() &&
                    width.isEmpty() &&
                    height.isEmpty()) {
                return false;
            }
            if (weight.isEmpty() ||
                    length.isEmpty() ||
                    width.isEmpty() ||
                    height.isEmpty()) {
                throw new AllExceptions.IncompleteBaggageInfoException();
            } else if (weight.equals("0") &&
                    length.equals("0") &&
                    width.equals("0") &&
                    height.equals("0")) {
                return false;
            } else if (weight.equals("0") ||
                    length.equals("0") ||
                    width.equals("0") ||
                    height.equals("0")) {
                throw new AllExceptions.InvalidBaggageInfoException();
            }
            return true;
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

    class PaymentExtraFeeGUI extends JFrame {
        public PaymentExtraFeeGUI(double totalWeight, double totalFee) {
            setTitle("Pay Extra Fee");
            setSize(600, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel congratsLabel = new JLabel("Congratulations! You have added " + totalWeight + " kg. Please pay extra fee.");
            mainPanel.add(congratsLabel);

            JLabel feeLabel1 = new JLabel("Extra Fee: $" + totalFee);
            mainPanel.add(feeLabel1);
            JLabel feeLabel2 = new JLabel("[Extra Fee = (Total Weight - Purchased Baggage Weight) * 50]");
            mainPanel.add(feeLabel2);

            // 添加支付方式图标和支付按钮
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            buttonPanel.add(new JLabel("Payment Method:"));
            ImageIcon icon = new ImageIcon("D:\\Learn\\fourth_year\\advanced\\visa.png");
            Image image = icon.getImage(); // 转换图标为 Image
            Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // 调整图标大小
            ImageIcon newIcon = new ImageIcon(newImage); // 生成新的 ImageIcon

            JLabel label = new JLabel(newIcon);
            buttonPanel.add(label);


//            buttonPanel.add(new JLabel(new ImageIcon("D:\\Learn\\fourth_year\\advanced\\visa.png"))); // 替换为实际的图标文件路径
//            buttonPanel.add(new JLabel(new ImageIcon("D:\\Learn\\fourth_year\\advanced\\Paypal.png")));
//            buttonPanel.add(new JLabel(new ImageIcon("D:\\Learn\\fourth_year\\advanced\\wechat-1.png")));
//            buttonPanel.add(new JLabel(new ImageIcon("D:\\Learn\\fourth_year\\advanced\\Alipay.png")));
            JButton payButton = new JButton("Pay");
            payButton.addActionListener(e ->
            {
                this.dispose();
                new CongratsPaymentGUI(totalWeight).setVisible(true);
            });

            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                this.dispose();
                BaggageDetailsGUI.removeBaggage(); // 调用 removeBaggage 方法
                new BaggageDetailsGUI().setVisible(true);
            });
            buttonPanel.add(backButton);
            buttonPanel.add(payButton);

            mainPanel.add(buttonPanel);

            add(mainPanel);
        }
    }

    class CongratsPaymentGUI extends JFrame {
        public CongratsPaymentGUI(double totalWeight) {
            setTitle("Congratulation!");
            setSize(500, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel congratsLabel = new JLabel("Congratulations! You have successfully loaded your items.");
            JLabel weightLabel = new JLabel("The total weight is " + totalWeight + "kg.");
            mainPanel.add(congratsLabel);
            mainPanel.add(weightLabel);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JButton finishButton = new JButton("Finish");
            finishButton.addActionListener(e ->
            {
                selectedPassenger.setIfCheck(true);
                this.dispose();
                GUI gui = new GUI(passengerList, flightList);
                gui.FlightCheckInGUI();
                gui.setVisible(true);
            });
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                this.dispose();
                BaggageDetailsGUI.removeBaggage(); // 调用 removeBaggage 方法
                new BaggageDetailsGUI().setVisible(true);
            });
            buttonPanel.add(backButton);
            buttonPanel.add(finishButton);
            mainPanel.add(buttonPanel);
            add(mainPanel);
        }
    }
}
