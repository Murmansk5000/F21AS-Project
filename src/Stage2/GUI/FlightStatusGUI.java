package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import javax.swing.border.EmptyBorder;

public class FlightStatusGUI extends JPanel implements Observer {
    private JPanel flightPanels;
    private FlightList flightList;

    public FlightStatusGUI(FlightList flightList) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);

        setLayout(new BorderLayout());
        setSize(1300, 400);

        JLabel headerLabel = new JLabel("Flight Status", SwingConstants.CENTER);
        Font boldFont = new Font(headerLabel.getFont().getName(), Font.BOLD, (int)(headerLabel.getFont().getSize() * 1.5));
        headerLabel.setFont(boldFont);
        headerLabel.setForeground(Color.BLACK);

        // 创建一个容纳标题的面板，并设置适当的边距
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // 添加边距
        headerPanel.add(headerLabel); // 将标签添加到面板中

        flightPanels = new JPanel(new GridLayout(2, 5, 10, 5));

        add(headerPanel, BorderLayout.NORTH); // 将标题面板添加到最上方
        add(flightPanels, BorderLayout.CENTER); // 将航班面板添加到中间

        update();
    }

    @Override
    public void update() {

        SwingUtilities.invokeLater(() -> {
            //TODO flightList.renewAllFilght();

            flightPanels.removeAll(); // 清空航班信息部分的面板

            Iterator<Flight> flightIterator = flightList.iterator();

            for (int i = 0; i < 10 && flightIterator.hasNext(); i++) {
                Flight flight = flightIterator.next();
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 1, 5, 2));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // 根据Flight对象创建标签
                JLabel flightCode = new JLabel(flight.getFlightCode() + ": " + flight.getCarrier());
                Font boldFont1 = new Font(flightCode.getFont().getName(), Font.BOLD, (int) (flightCode.getFont().getSize() * 1.2)); // 将字体大小增大为原来大小的1.5倍
                flightCode.setFont(boldFont1); // 设置字体为加粗且增大字号
                flightCode.setHorizontalAlignment(SwingConstants.CENTER);
                flightCode.setForeground(Color.BLACK); // 设置字体颜色为黑色
                // flightCode.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // 添加左边距

                JLabel checkIn = new JLabel(flight.getPassengerInFlight().checkInSize() + " checked in of " + flight.getPassengerInFlight().size());

                checkIn.setHorizontalAlignment(SwingConstants.CENTER);
                double percentage = (flight.getBaggageInFlight().getTotalVolume() / flight.getMaxBaggageVolume() * 100.0);
                String per = String.format("%.2f", percentage);
                JLabel percentageLabel = new JLabel("Hold is " + per + "% full");
                percentageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel departureLabel;

                if (flight.getIsTakenOff()) {
                    departureLabel = new JLabel("Flight has taken off.");
                } else {
                    long secondsUntilTakeoff = Duration.between(Instant.now(), flight.getTakeOffInstant()).getSeconds();
                    long timeUntilTakeoffInMinutes = (long) Math.ceil(secondsUntilTakeoff / 60.0);
                    if (timeUntilTakeoffInMinutes < 0) {
                        departureLabel = new JLabel("Flight delays.");
                    } else {
                        departureLabel = new JLabel("Flight will take off in " + timeUntilTakeoffInMinutes + " minutes.");
                    }

                }

                departureLabel.setHorizontalAlignment(SwingConstants.CENTER);

                panel.add(flightCode);
                panel.add(checkIn);
                panel.add(percentageLabel);
                panel.add(departureLabel);

                flightPanels.add(panel);
            }

            this.revalidate();
            this.repaint();
        });
    }

}
