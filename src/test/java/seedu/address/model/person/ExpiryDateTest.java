package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        String invalidExpiryDate = "31-12-2026";
        assertThrows(IllegalArgumentException.class, () -> new ExpiryDate(invalidExpiryDate));
    }

    @Test
    public void isValidExpiryDate() {
        // null expiry date
        assertThrows(NullPointerException.class, () -> ExpiryDate.isValidExpiryDate(null));

        // invalid expiry dates
        assertFalse(ExpiryDate.isValidExpiryDate("")); // empty string
        assertFalse(ExpiryDate.isValidExpiryDate(" ")); // spaces only
        assertFalse(ExpiryDate.isValidExpiryDate("31-12-2026")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("2026/12/31")); // wrong separator
        assertFalse(ExpiryDate.isValidExpiryDate("2026-02-30")); // invalid calendar date
        assertFalse(ExpiryDate.isValidExpiryDate("FAKE DATE")); // not a date
        assertFalse(ExpiryDate.isValidExpiryDate("2026/12/31")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("01-02-2026")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("2026-01-01")); // correct format, past date

        // valid expiry dates
        assertTrue(ExpiryDate.isValidExpiryDate("2026-12-31"));
        assertTrue(ExpiryDate.isValidExpiryDate("2028-02-29")); // leap year
        assertTrue(ExpiryDate.isValidExpiryDate("2030-01-01"));
        assertTrue(ExpiryDate.isValidExpiryDate("31/12/2026"));

        assertEquals("2026-12-31", new ExpiryDate("31/12/2026").toString());
    }

    @Test
    public void equals() {
        ExpiryDate expiryDate = new ExpiryDate("2026-12-31");

        // same values -> returns true
        assertTrue(expiryDate.equals(new ExpiryDate("2026-12-31")));

        // same object -> returns true
        assertTrue(expiryDate.equals(expiryDate));

        // null -> returns false
        assertFalse(expiryDate.equals(null));

        // different types -> returns false
        assertFalse(expiryDate.equals(5.0f));

        // different values -> returns false
        assertFalse(expiryDate.equals(new ExpiryDate("2027-01-01")));
    }

    @Test
    public void hashCode_equalDates_sameHash() {
        ExpiryDate d1 = new ExpiryDate("2026-12-31");
        ExpiryDate d2 = new ExpiryDate("2026-12-31");
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void hashCode_differentDates_differentHash() {
        ExpiryDate d1 = new ExpiryDate("2026-12-31");
        ExpiryDate d2 = new ExpiryDate("2027-01-01");
        assertFalse(d1.hashCode() == d2.hashCode());
    }

    @Test
    public void compareTo_earlierDate_returnsNegative() {
        ExpiryDate earlier = new ExpiryDate("2026-12-30");
        ExpiryDate later = new ExpiryDate("2026-12-31");
        assertTrue(earlier.compareTo(later) < 0);
    }

    @Test
    public void compareTo_laterDate_returnsPositive() {
        ExpiryDate earlier = new ExpiryDate("2026-12-30");
        ExpiryDate later = new ExpiryDate("2026-12-31");
        assertTrue(later.compareTo(earlier) > 0);
    }

    @Test
    public void compareTo_sameDate_returnsZero() {
        ExpiryDate d1 = new ExpiryDate("2026-12-31");
        ExpiryDate d2 = new ExpiryDate("2026-12-31");
        assertEquals(0, d1.compareTo(d2));
    }
}
