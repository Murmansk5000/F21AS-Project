package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionFromatErrorGUI extends JFrame {
    public ExceptionFromatErrorGUI(String ref) {
        setTitle("Sample Window");
        setSize(300, 200); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        setLocationRelativeTo(null); // 居中显示窗口

        // 使用BorderLayout
        setLayout(new BorderLayout());

        // 创建标签并添加到中间
        String labelText = "<html><span style='color: red;'>Not the correct input format.</span>" +
                " Dimensions of Checked baggage: <span style='color: red;'>the sum of length, width, and height not exceeding 158 (cm)</span>," +
                "<br>Weight of Checked baggage: <span style='color: red;'>not exceeding 23 (kg)</span>.</html>";
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // 创建按钮并添加到右下角
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            // 当按钮被点击时执行的操作
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 关闭程序
            }
        });

        // 创建一个面板放置 OK 按钮，并使其在面板的右下角
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH); // 将面板添加到窗口的南部（即底部）

        setVisible(true); // 使窗口可见
    }

}