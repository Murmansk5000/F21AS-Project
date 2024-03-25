package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class FlightStatusGUI extends JFrame implements Observer{
    private JPanel mainPanel;
    private JPanel flightPanels;
    private FlightList flightList;

    public FlightStatusGUI(FlightList flightList) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);

        setTitle("Flight Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 300);

        mainPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel headerLabel = new JLabel("Flight Status");
        Font boldFont = new Font(headerLabel.getFont().getName(), Font.BOLD, (int) (headerLabel.getFont().getSize() * 1.3));
        headerLabel.setFont(boldFont);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(Color.BLACK);
        mainPanel.add(headerLabel, gbc);

        flightPanels = new JPanel(new GridLayout(2, 5, 10, 10));
        gbc.gridy = 1;
        mainPanel.add(flightPanels, gbc);

        // Add main panel to frame
        add(mainPanel);

        setLocationRelativeTo(null); // Center the frame
//        setVisible(true);

        update();

    }

    @Override
    public void update() {

        // 在这里更新 GUI 显示以反映新的乘客队列状态
        SwingUtilities.invokeLater(() -> {

            flightPanels.removeAll(); // 清空航班信息部分的面板

            // 重新加载乘客信息
            Iterator<Flight> flightIterator = flightList.iterator();

            for (int i = 0; i < 10 && flightIterator.hasNext(); i++) {
                Flight flight1 = flightIterator.next();
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 1, 10, 10));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // 根据Flight对象创建标签
                JLabel flightCode = new JLabel(flight1.getFlightCode()+" "+flight1.getCarrier());
                Font boldFont1 = new Font(flightCode.getFont().getName(), Font.BOLD, (int)(flightCode.getFont().getSize() * 1.1)); // 将字体大小增大为原来大小的1.5倍
                flightCode.setFont(boldFont1); // 设置字体为加粗且增大字号
                flightCode.setHorizontalAlignment(SwingConstants.CENTER);
                flightCode.setForeground(Color.BLACK); // 设置字体颜色为黑色
                //flightCode.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // 添加左边距

                JLabel checkIn = new JLabel(flight1.getPassengerInFlight().checkInSize()+" checked in of "+ flight1.getPassengerInFlight().size());

                checkIn.setHorizontalAlignment(SwingConstants.CENTER);
                double percentage = ((double)flight1.getPassengerInFlight().checkInSize() / (double)flight1.getPassengerInFlight().size() * 100.0);
                String per = String.format("%.2f", percentage);
//                System.out.println("0000"+flight.getPassengerInFlight().checkInSize());
                JLabel percentageLabel = new JLabel("Hold is " + per + "% full");
                percentageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                // 根据isTakenOff参数更新航班起飞状态标签
                String departureStatus = flight1.getIsTakenOff() ? "Flight has departed" : "Flight not departed";
                JLabel departureStatusLabel = new JLabel(departureStatus);
                departureStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);

                panel.add(flightCode);
                panel.add(checkIn);
                panel.add(percentageLabel);
                panel.add(departureStatusLabel);

                flightPanels.add(panel);
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
