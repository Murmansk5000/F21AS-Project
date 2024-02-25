package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CongratsNoFeePaymentGUI extends JFrame {

    public CongratsNoFeePaymentGUI() {
        setTitle("恭喜！");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel congratsLabel = new JLabel("Congratulations! You have successfully loaded your items.");
        mainPanel.add(congratsLabel);

        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(CongratsNoFeePaymentGUI.this,
                        "Thank you for using our service. Have a great journey!",
                        "Exit", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        mainPanel.add(finishButton);

        add(mainPanel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new CongratsNoFeePaymentGUI().setVisible(true));
    }
}
