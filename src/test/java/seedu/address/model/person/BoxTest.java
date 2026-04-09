package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        // invalid box names — VALIDATION_REGEX = "^[a-z]+-\d{1}$"
        assertFalse(Box.isValidBoxName("")); // empty string
        assertFalse(Box.isValidBoxName("box-12")); // two digits — regex allows only 1 digit
        assertFalse(Box.isValidBoxName("Box-1")); // uppercase letter not allowed
        assertFalse(Box.isValidBoxName("-1")); // no letter before hyphen
        assertFalse(Box.isValidBoxName("box-")); // no digit after hyphen
        assertFalse(Box.isValidBoxName("box1")); // missing hyphen
        assertFalse(Box.isValidBoxName("BOX-1")); // all uppercase
        assertFalse(Box.isValidBoxName("box -1")); // space inside

        // valid box names
        assertTrue(Box.isValidBoxName("box-1")); // standard
        assertTrue(Box.isValidBoxName("a-0")); // minimal: single letter, digit 0
        assertTrue(Box.isValidBoxName("z-9")); // single letter, digit 9
        assertTrue(Box.isValidBoxName("abc-5")); // multiple letters, single digit
        assertTrue(Box.isValidBoxName("parcel-3")); // longer type name
    }

    @Test
    public void compareTo() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box2 = new Box("box-2", new ExpiryDate("2026-12-31"));
        Box box1Earlier = new Box("box-1", new ExpiryDate("2026-12-30"));

        // box with earlier expiry date compared to box with later expiry date of the same name -> returns -1
        assertEquals(-1, box1Earlier.compareTo(box1));

        // box with later expiry date compared to box with earlier expiry date of the same name -> returns 1
        assertEquals(1, box1.compareTo(box1Earlier));

        // boxNames are in lexographical order -> returns 1
        assertEquals(-1, box1.compareTo(box2));
    }

    @Test
    public void compareTo_equalBoxes_returnsZero() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box1Copy = new Box("box-1", new ExpiryDate("2026-12-31"));
        assertEquals(0, box1.compareTo(box1Copy));
    }

    @Test
    public void equals_sameNameAndExpiry_returnsTrue() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box1Copy = new Box("box-1", new ExpiryDate("2026-12-31"));
        assertTrue(box1.equals(box1Copy));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        assertTrue(box1.equals(box1));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box2 = new Box("box-2", new ExpiryDate("2026-12-31"));
        assertFalse(box1.equals(box2));
    }

    @Test
    public void equals_differentExpiry_returnsFalse() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box1DifferentExpiry = new Box("box-1", new ExpiryDate("2026-12-30"));
        assertFalse(box1.equals(box1DifferentExpiry));
    }

    @Test
    public void equals_null_returnsFalse() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        assertFalse(box1.equals(null));
    }

    @Test
    public void hashCode_equalBoxes_sameHash() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box1Copy = new Box("box-1", new ExpiryDate("2026-12-31"));
        assertEquals(box1.hashCode(), box1Copy.hashCode());
    }

    @Test
    public void hashCode_differentBoxes_differentHash() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        Box box2 = new Box("box-2", new ExpiryDate("2026-12-31"));
        assertNotEquals(box1.hashCode(), box2.hashCode());
    }

    @Test
    public void toString_containsBoxNameAndExpiry() {
        Box box1 = new Box("box-1", new ExpiryDate("2026-12-31"));
        String result = box1.toString();
        assertTrue(result.contains("box-1"));
        assertTrue(result.contains("2026-12-31"));
    }
}
