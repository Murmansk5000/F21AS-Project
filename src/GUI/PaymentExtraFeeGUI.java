package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentExtraFeeGUI extends JFrame {
    private int luggageCount; // 行李数量
    private double extraFee; // 需要额外支付的费用

    public PaymentExtraFeeGUI(int luggageCount, double extraFee) {
        this.luggageCount = luggageCount;
        this.extraFee = extraFee;

        setTitle("支付额外费用");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel congratsLabel = new JLabel("Congratulations! You have added " + luggageCount + " items. Please pay extra fee.");
        mainPanel.add(congratsLabel);

        JLabel feeLabel = new JLabel("Please pay extra fee: $" + extraFee);
        mainPanel.add(feeLabel);

        // 添加支付方式图标和支付按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.add(new JLabel("支付方式:"));
        buttonPanel.add(new JLabel(new ImageIcon("visa_icon.png"))); // 替换为实际的图标文件路径
        buttonPanel.add(new JLabel(new ImageIcon("paypal_icon.png")));
        buttonPanel.add(new JLabel(new ImageIcon("wechatpay_icon.png")));

        JButton payButton = new JButton("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实现支付逻辑，这里可以添加支付成功的逻辑
                JOptionPane.showMessageDialog(PaymentExtraFeeGUI.this,
                        "Payment successful!",
                        "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(payButton);

        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    public static void main(String[] args) {
        // 这里需要传入行李数量和额外费用，用于展示具体的内容
        EventQueue.invokeLater(() -> new PaymentExtraFeeGUI(3, 20.0).setVisible(true));
    }
}

