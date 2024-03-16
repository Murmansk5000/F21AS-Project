package Stage1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaggageListTest {
    private BaggageList baggageList;

    @BeforeEach
    public void setUp() {
        baggageList = new BaggageList();
    }

    @Test
    public void testNotOverweight() {
        try {
            // Under weight limit
            baggageList.addBaggage(new Baggage(15.0, 10.0, 20.0, 20.0));
            assertEquals(0.0, baggageList.calculateTotalFee(), "Total fee should be 0 for weight within limit");
            // Just under the size limit (158cm) but not over
            baggageList.addBaggage(new Baggage(12.0, 50.0, 50.0, 58.0));
            assertEquals(0.0, baggageList.calculateTotalFee(), "Total fee should be 0 for weight within limit");
            // Just under the weight limit (40kg) but not over
            baggageList.addBaggage(new Baggage(13.0, 20.0, 20.0, 20.0));
            assertEquals(0.0, baggageList.calculateTotalFee(), "Total fee should be 0 for weight within limit");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage creation should not throw NumberErrorException for valid dimensions and weight");
        }
    }

    // Test whether the excess baggage charge is calculated correctly
    @Test
    public void testCalculateTotalFee() {
        try {
            // Under weight limit
            baggageList.addBaggage(new Baggage(22.0, 20.0, 20.0, 20.0));
            assertEquals(0.0, baggageList.calculateTotalFee(), "Total fee should be 0 for weight within limit");
            // Causes total weight to exceed limit
            baggageList.addBaggage(new Baggage(21.0, 20.0, 20.0, 20.0));
            assertEquals(150.0, baggageList.calculateTotalFee(), "Total fee should be greater than 0 for weight over limit");
        } catch (AllExceptions.NumberErrorException e) {
            fail("Baggage creation should not throw NumberErrorException for valid dimensions and weight");
        }
    }

    /**
     * We limit the weight of a single piece of baggage to 23kg and its dimensions to a total of 158cm on three sides.
     * if this is exceeded, an NumberErrorException will be thrown and the item will not be able to be checked in.
     */
    @Test
    void testBaggageWeightLimitExceeded() {
        assertThrows(AllExceptions.NumberErrorException.class, () -> {
            new Baggage(24.0, 50.0, 30.0, 10.0);
        }, "Expected NumberErrorException due to weight limit exceeded");
    }

    @Test
    void testBaggageSizeLimitExceeded() {
        assertThrows(AllExceptions.NumberErrorException.class, () -> {
            new Baggage(20.0, 60.0, 60.0, 60.0);
        }, "Expected NumberErrorException due to size limit exceeded");
    }

    @AfterEach
    public void tearDown() {
        baggageList = null;
    }
}