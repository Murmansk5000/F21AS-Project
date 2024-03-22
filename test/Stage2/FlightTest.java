package Stage2;

import Stage1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightTest {
    private FlightList flightList;
    private Flight flight;
    private CheckInCounter checkInCounter;
    private Passenger passenger;
    private PassengerList passengerList;

    @BeforeEach
    void setUp() {
        flightList = new FlightList();
        passengerList = new PassengerList();
        flight = new Flight("DQ2692", "Destination", "Carrier", 100, 20000, 200, 100);
        flightList.addFlight(flight);
        checkInCounter = new CheckInCounter(1, new PassengerQueue(), false, flightList);
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
        // Passenger attempts to check-in when flight has not departed
        assertFalse(flight.getIsTakenOff(), "Flight should not have taken off.");
        assertTrue(checkInCounter.processPassenger(passenger), "Passenger should be able to check in before takeoff.");
    }

    @Test
    void testCannotCheckInAfterFlightTakeoff() throws AllExceptions.NoMatchingFlightException, AllExceptions.NoMatchingRefException, AllExceptions.NumberErrorException {
        // The flight has taken off
        flight.fly();
        assertTrue(flight.getIsTakenOff(), "Flight should have taken off.");
        // Try to check-in for a flight after departure
        assertFalse(checkInCounter.processPassenger(passenger), "Passenger should not be able to check");
    }
}
