package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BoxTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Box(null, null));
    }

    @Test
    public void constructor_invalidBoxName_throwsIllegalArgumentException() {
        String invalidBoxName = "";
        ExpiryDate validExpiryDate = new ExpiryDate("2026-12-31");
        assertThrows(IllegalArgumentException.class, () -> new Box(invalidBoxName, validExpiryDate));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        String validBoxName = "box-1";
        String invalidExpiryDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Box(validBoxName, new ExpiryDate(invalidExpiryDate)));
    }

    @Test
    public void isValidBoxName() {
        // null box name
        assertThrows(NullPointerException.class, () -> Box.isValidBoxName(null));
    }

    @Test
    public void compareTo() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box2 = new Box("box-2", new ExpiryDate("2026-12-31"));
        Box box1_earlier = new Box("box-1", new ExpiryDate("2026-01-01"));

        // box with earlier expiry date compared to box with later expiry date of the same name -> returns -1
        assertEquals(-1, box1_earlier.compareTo(box1));

        // box with later expiry date compared to box with earlier expiry date of the same name -> returns 1
        assertEquals(1, box1.compareTo(box1_earlier));

        // boxNames are in lexographical order -> returns 1
        assertEquals(-1, box1.compareTo(box2));
    }
}
