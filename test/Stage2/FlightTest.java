package Stage2;

import Stage1.AllExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import Stage1.Passenger;
import Stage1.PassengerList;
import Stage1.Flight;
import Stage1.FlightList;

class FlightTest {
    private FlightList flightList;
    private Flight flight;
    private CheckInCounter checkInCounter;
    private Passenger passenger;
    private PassengerList passengerList;

    @BeforeEach
    void setUp() {
        // 初始化航班列表和航班
        flightList = new FlightList();
        passengerList = new PassengerList();
        flight = new Flight("DQ2692", "Destination", "Carrier", 100, 20000, 200, 100);
        flightList.addFlight(flight);
        // 初始化值机柜台
        checkInCounter = new CheckInCounter(1, new PassengerQueue(), false, flightList);
        // 初始化乘客
        passenger = new Passenger("B0600", "Jane", "Doe", "DQ2692", false);
        passengerList.addPassenger(passenger);
        try {
            flightList.addPassengersToFlights(passengerList);
        } catch (AllExceptions.NoMatchingFlightException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCheckInBeforeFlightTakeoff() throws AllExceptions.NoMatchingFlightException, AllExceptions.NoMatchingRefException, AllExceptions.NumberErrorException {
        // 航班未起飞，乘客尝试值机
        assertFalse(flight.getIsTakenOff(), "Flight should not have taken off.");
        assertTrue(checkInCounter.processPassenger(passenger), "Passenger should be able to check in before takeoff.");
    }

    @Test
    void testCannotCheckInAfterFlightTakeoff() throws AllExceptions.NoMatchingFlightException, AllExceptions.NoMatchingRefException, AllExceptions.NumberErrorException {
        // 模拟航班起飞
        flight.fly(); // 假设`fly`方法将`isTakenOff`设置为true
        assertTrue(flight.getIsTakenOff(), "Flight should have taken off.");
        // 尝试为起飞后的航班进行值机
        assertFalse(checkInCounter.processPassenger(passenger), "Passenger should not be able to check");
    }
    }
