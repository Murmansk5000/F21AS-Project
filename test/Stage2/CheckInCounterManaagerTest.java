package Stage2;

import Stage1.FlightList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is used to test that counters can be correctly created
 * and closed according to quantity limits in the CHeckInCounterManagement class.
 */
class CheckInCounterManagerTest {
    private CheckInCounterManager manager;

    @BeforeEach
    void setUp() {
        // Initialize a manager instance for testing
        manager = new CheckInCounterManager(new FlightList());
        // Open the maximum number of regular counters
        for (int i = 0; i < CheckInCounterManager.getMAX_REGULAR_COUNTER(); i++) {
            manager.createNewCounter(false);
        }
        // Open the maximum number of VIP counters
        for (int i = 0; i < CheckInCounterManager.getMAX_VIP_COUNTER(); i++) {
            manager.createNewCounter(true);
        }
    }

    @Test
    void testCreateNewCounterAtMaxLimit() {
        // When the number of counters reaches the maximum number, no new counters can be created.
        manager.createNewCounter(true);
        assertEquals(CheckInCounterManager.getMAX_VIP_COUNTER(), manager.getOpenCount(true),
                "Should not add more VIP counters beyond max limit");
        manager.createNewCounter(false);
        assertEquals(CheckInCounterManager.getMAX_REGULAR_COUNTER(), manager.getOpenCount(false),
                "Should not add more regular counters beyond max limit");
    }

    @Test
    void testCloseCounterAtMinLimit() {
        // Minimize the number of both types of counters.
        // No more counters can be closed.
        for (int i = manager.getOpenCount(true); i > CheckInCounterManager.getMIN_VIP_COUNTER(); i--) {
            manager.closeCounter(true);
        }
        for (int i = manager.getOpenCount(false); i > CheckInCounterManager.getMIN_REGULAR_COUNTER(); i--) {
            manager.closeCounter(false);
        }
        assertEquals(CheckInCounterManager.getMIN_VIP_COUNTER(), manager.getOpenCount(true),
                "Should not close VIP counters beyond min limit");
        assertEquals(CheckInCounterManager.getMIN_REGULAR_COUNTER(), manager.getOpenCount(false),
                "Should not close regular counters beyond min limit");
    }

    @AfterEach
    void tearDown() {
        manager = null;
    }
}

