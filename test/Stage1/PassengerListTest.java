package Stage1;

import Stage1.modules.AllExceptions;
import Stage1.modules.Passenger;
import Stage1.modules.PassengerList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerListTest {

    private PassengerList passengerList;

    @BeforeEach
    public void setUp() {
        //Add two Passenger instances for testing
        passengerList = new PassengerList();
        try {
            passengerList.addPassenger(new Passenger("B0501", "John", "Doe", "VD7018", false));
            passengerList.addPassenger(new Passenger("B0502", "Jane", "Doe", "DQ2692", true));
        } catch (IllegalStateException e) {
            fail("Passenger creation failed due to invalid input");
        }
    }

    // Test whether the find was successful
    @Test
    public void testFindByRefCodeFound() {
        try {
            Passenger result = passengerList.findByRefCode("B0501");
            assertNotNull(result, "Passenger should be found with ref code B0501.");
            assertEquals("Doe", result.getLastName(), "The passenger's last name should be Doe.");
        } catch (AllExceptions.NoMatchingRefException e) {
            fail("Did not expect NoMatchingRefException.");
        }
    }

    // Test whether the program can throw NoMatchingRefException when a non-existent reservation number is entered.
    @Test
    public void testFindByRefCodeNotFound() {
        assertThrows(AllExceptions.NoMatchingRefException.class, () -> {
            passengerList.findByRefCode("NONEXISTENT");
        }, "Expected NoMatchingRefException for non-existent ref code.");
    }

    // The programme should not throw an exception when the reference number entered matches the passenger's surname entered
    @Test
    public void testMatchPassengerMatch() {
        assertDoesNotThrow(() -> {
            assertTrue(passengerList.matchPassenger("B0501", "Doe"),
                    "The ref code and last name should match for the passenger.");
        });
    }

    // Test whether the program can throw a NameCodeMismatchException when the reference number entered and the passenger's surname entered do not match
    @Test
    public void testMatchPassengerNoMatch() {
        assertThrows(AllExceptions.NameCodeMismatchException.class, () -> {
            passengerList.matchPassenger("B0502", "Smith");
        }, "Expected NameCodeMismatchException for mismatched last name.");
    }

    @AfterEach
    public void tearDown() {
        passengerList = null;
    }

}
