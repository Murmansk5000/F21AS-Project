package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage1.Passenger;
import Stage2.Observer;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class FlightStatusGUI extends JFrame implements Observer{
    private JPanel mainPanel;
    private JPanel flightPanels;
    private FlightList flightList;
    private Flight flight;

    public FlightStatusGUI(FlightList flightList, Flight flight) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);
        this.flight = flight;

        setTitle("Flight Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 300);

        mainPanel = new JPanel(new GridLayout(3, 1)); // Arrange flight panels horizontally

        JLabel headerLabel = new JLabel("Flight Status");
        Font boldFont = new Font(headerLabel.getFont().getName(), Font.BOLD, (int)(headerLabel.getFont().getSize() * 1.3)); // 将字体大小增大为原来大小的1.5倍
        headerLabel.setFont(boldFont); // 设置字体为加粗且增大字号
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(Color.BLACK); // 设置字体颜色为黑色
        flightPanels = new JPanel();
        flightPanels.setLayout(new GridLayout(1, 10, 10, 10));
        mainPanel.add(headerLabel);
        mainPanel.add(flightPanels);

        // Add main panel to frame
        add(mainPanel);

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

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
                panel.setLayout(new GridLayout(3, 1, 10, 10));
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

                // 将标签添加到乘客面板
                panel.add(flightCode);
                panel.add(checkIn);
                panel.add(percentageLabel);

                flightPanels.add(panel);

            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }
}
