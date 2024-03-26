package Stage2.GUI;

import Stage1.Flight;
import Stage1.FlightList;
import Stage2.Observer;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;

public class FlightStatusGUI extends JPanel implements Observer {
    private JPanel flightPanels;
    private FlightList flightList;
    private Instant currentTime;

    public FlightStatusGUI(FlightList flightList) {
        this.flightList = flightList;
        this.flightList.registerObserver(this);

        setSize(1300, 300);

        this.setLayout(new GridLayout(3, 1)); // Arrange flight panels horizontally

        JLabel headerLabel = new JLabel("Flight Status");

        flightPanels = new JPanel();
        flightPanels.setLayout(new GridLayout(1, 10, 10, 10));

//        Font boldFont = new Font(headerLabel.getFont().getName(), Font.BOLD, (int)(headerLabel.getFont().getSize() * 1.3)); // 将字体大小增大为原来大小的1.5倍
//        headerLabel.setFont(boldFont); // 设置字体为加粗且增大字号
//        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        headerLabel.setForeground(Color.BLACK); // 设置字体颜色为黑色
//        this.add(headerLabel);
        this.add(flightPanels);
        update();

    }

    @Override
    public void update() {

        SwingUtilities.invokeLater(() -> {

            flightPanels.removeAll(); // 清空航班信息部分的面板


            Iterator<Flight> flightIterator = flightList.iterator();

            for (int i = 0; i < 10 && flightIterator.hasNext(); i++) {
                Flight flight = flightIterator.next();
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 1, 10, 10));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // 根据Flight对象创建标签
                JLabel flightCode = new JLabel(flight.getFlightCode() + " " + flight.getCarrier());
                Font boldFont1 = new Font(flightCode.getFont().getName(), Font.BOLD, (int) (flightCode.getFont().getSize() * 1.1)); // 将字体大小增大为原来大小的1.5倍
                flightCode.setFont(boldFont1); // 设置字体为加粗且增大字号
                flightCode.setHorizontalAlignment(SwingConstants.CENTER);
                flightCode.setForeground(Color.BLACK); // 设置字体颜色为黑色
                //flightCode.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // 添加左边距

                JLabel checkIn = new JLabel(flight.getPassengerInFlight().checkInSize() + " checked in of " + flight.getPassengerInFlight().size());

                checkIn.setHorizontalAlignment(SwingConstants.CENTER);
                double percentage = ((double) flight.getPassengerInFlight().checkInSize() / (double) flight.getPassengerInFlight().size() * 100.0);
                String per = String.format("%.2f", percentage);
//                System.out.println("0000"+flight.getPassengerInFlight().checkInSize());
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

                JLabel temp = new JLabel(flight.getIsTakenOff() ? "yes" : "no");

                panel.add(flightCode);
                panel.add(checkIn);
                panel.add(percentageLabel);

                panel.add(departureLabel);
                //panel.add(temp);

                flightPanels.add(panel);
            }

            this.revalidate();
            this.repaint();
        });
    }

}
