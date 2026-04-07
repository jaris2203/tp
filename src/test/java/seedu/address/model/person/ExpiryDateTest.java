package seedu.address.model.person;

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
        assertFalse(ExpiryDate.isValidExpiryDate("2026-01-01")); // correct format, past date

        // valid expiry dates
        assertTrue(ExpiryDate.isValidExpiryDate("2026-12-31"));
        assertTrue(ExpiryDate.isValidExpiryDate("2028-02-29")); // leap year
        assertTrue(ExpiryDate.isValidExpiryDate("2030-01-01"));
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
}
