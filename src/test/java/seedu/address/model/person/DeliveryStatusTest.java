package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertFalse(deliveryStatus.equals(DeliveryStatus.fromString("packed")));
    }

    @Test
    public void fromString_allStatuses_success() {
        assertEquals(DeliveryStatus.PENDING, DeliveryStatus.fromString("pending"));
        assertEquals(DeliveryStatus.PACKED, DeliveryStatus.fromString("packed"));
        assertEquals(DeliveryStatus.DELIVERED, DeliveryStatus.fromString("delivered"));
    }

    @Test
    public void fromString_caseInsensitive_success() {
        assertEquals(DeliveryStatus.PENDING, DeliveryStatus.fromString("PENDING"));
        assertEquals(DeliveryStatus.PENDING, DeliveryStatus.fromString("Pending"));
        assertEquals(DeliveryStatus.PACKED, DeliveryStatus.fromString("PACKED"));
        assertEquals(DeliveryStatus.DELIVERED, DeliveryStatus.fromString("DELIVERED"));
    }

    @Test
    public void fromString_withWhitespace_success() {
        assertEquals(DeliveryStatus.PENDING, DeliveryStatus.fromString("  pending  "));
    }

    @Test
    public void toString_returnsCapitalisedLabel() {
        assertEquals("Pending", DeliveryStatus.PENDING.toString());
        assertEquals("Packed", DeliveryStatus.PACKED.toString());
        assertEquals("Delivered", DeliveryStatus.DELIVERED.toString());
    }
}
