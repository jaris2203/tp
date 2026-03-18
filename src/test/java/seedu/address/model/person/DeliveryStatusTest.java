package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeliveryStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DeliveryStatus.fromString(null));
    }

    @Test
    public void constructor_invalidDeliveryStatus_throwsIllegalArgumentException() {
        String invalidDeliveryStatus = "maybe";
        assertThrows(IllegalArgumentException.class, () -> DeliveryStatus.fromString(invalidDeliveryStatus));
    }

    @Test
    public void equals() {
        DeliveryStatus deliveryStatus = DeliveryStatus.fromString("delivered");

        // same values -> returns true
        assertTrue(deliveryStatus.equals(DeliveryStatus.fromString("delivered")));

        // same object -> returns true
        assertTrue(deliveryStatus.equals(deliveryStatus));

        // null -> returns false
        assertFalse(deliveryStatus.equals(null));

        // different types -> returns false
        assertFalse(deliveryStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(deliveryStatus.equals(DeliveryStatus.fromString("pending")));

        // different values -> returns false
        assertFalse(deliveryStatus.equals(DeliveryStatus.fromString("packing")));
    }
}
