package Stage1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight("FL123", "New York", "AirTest", 5, 50.0, 100.0);
        flight.addPassenger(new Passenger("B0501", "John", "Doe", "VD7018", false));
    }

    // Test not exceeding the limits
    @Test
    void canTakeOffUnderLimits() {
        try {
            flight.addBaggageToFlight(new Baggage(10.0, 2.0, 2.0, 2.0));
            flight.addBaggageToFlight(new Baggage(5.0, 1.0, 1.0, 5.0));
            assertTrue(flight.canTakeOff(), "Flight should be able to take off when under limits");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage should not exceed weight or size limits");
        }
    }

    @Test
    void canTakeOffUnderLimitsByPassenger() {
        try {
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(10.0, 2.0, 2.0, 2.0));
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(5.0, 1.0, 1.0, 5.0));
            flight.addAllBaggageToFlight();
            assertTrue(flight.canTakeOff(), "Flight should be able to take off when under limits");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage should not exceed weight or size limits");
        } catch (AllExceptions.NoMatchingRefException e) {
            throw new RuntimeException(e);
        }
    }

    // When the total baggage weight exceeds the maximum baggage weight, test whether canTakeOff() returns false.
    @Test
    void cannotTakeOffDueToExcessPassengers() throws AllExceptions.NumberErrorException {
        for (int i = 0; i < 6; i++) {
            Passenger passenger = new Passenger("REF" + i, "Name" + i, "Surname" + i, "FL123", true);
            flight.addPassenger(passenger);
        }
        assertFalse(flight.canTakeOff(), "Flight should not be able to take off due to too many passengers");
    }

    // When the total baggage weight exceeds the maximum baggage weight, test if canTakeOff() returns false
    @Test
    void cannotTakeOffDueToExcessBaggageWeight() {
        try {
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(20.0, 20.0, 20.0, 20.0));
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(20.0, 20.0, 20.0, 20.0));
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(20.0, 20.0, 20.0, 20.0));
            flight.addAllBaggageToFlight();
            assertFalse(flight.canTakeOff(), "Flight should be able to take off when under limits");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage should not exceed weight or size limits");
        } catch (AllExceptions.NoMatchingRefException e) {
            throw new RuntimeException(e);
        }
    }

    // When the total baggage volume exceeds the maximum baggage volume, test whether canTakeOff() returns false or not.
    @Test
    void cannotTakeOffDueToExcessBaggageVolumeByPassenger() {
        try {
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(10.0, 50.0, 50.0, 50.0));
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(10.0, 50.0, 50.0, 50.0));
            flight.getPassengerInFlight().findByRefCode("B0501").getHisBaggageList().addBaggage(new Baggage(10.0, 50.0, 50.0, 50.0));
            flight.addAllBaggageToFlight();
            assertFalse(flight.canTakeOff(), "Flight should be able to take off when under limits");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage should not exceed weight or size limits");
        } catch (AllExceptions.NoMatchingRefException e) {
            throw new RuntimeException(e);
        }
    }

}
