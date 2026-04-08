package seedu.address.model.commons.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void isValidPhone_threeDigits_valid() {
        assertTrue(Phone.isValidPhone("100")); // exactly 3 digits, starts with 1
        assertTrue(Phone.isValidPhone("999")); // exactly 3 digits, starts with 9
    }

    @Test
    public void isValidPhone_twoDigits_invalid() {
        assertFalse(Phone.isValidPhone("12")); // only 2 digits — below minimum
        assertFalse(Phone.isValidPhone("99")); // only 2 digits
    }

    @Test
    public void isValidPhone_startsWithZero_invalid() {
        // VALIDATION_REGEX = "[1-9]\d{2,}" — must start with 1-9
        assertFalse(Phone.isValidPhone("099")); // starts with 0
        assertFalse(Phone.isValidPhone("012")); // starts with 0
    }

    @Test
    public void hashCode_sameValue_sameHash() {
        Phone p1 = new Phone("999");
        Phone p2 = new Phone("999");
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void toString_returnsValue() {
        Phone phone = new Phone("91234567");
        assertEquals("91234567", phone.toString());
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
