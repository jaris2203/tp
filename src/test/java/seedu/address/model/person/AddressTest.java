package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_blankAddress_throwsIllegalArgumentException() {
        String blankAddress = "";
        assertThrows(IllegalArgumentException.class, Address.MESSAGE_CONSTRAINTS_BLANK, () ->
                new Address(blankAddress));
    }

    @Test
    public void constructor_noPostalCode_throwsIllegalArgumentException() {
        String noPostalCodeAddress = "blk 123, Sengkang Street 11, Singapore";
        assertThrows(IllegalArgumentException.class, Address.MESSAGE_CONSTRAINTS_POSTAL_CODE, () ->
                new Address(noPostalCodeAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertFalse(Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("sengkang")); // no 6-digit postal code
        assertFalse(Address.isValidAddress("12345")); // only 5 digits
        assertFalse(Address.isValidAddress("1234567")); // 7 digits

        // valid addresses
        assertTrue(Address.isValidAddress("123456")); // only postal code
        assertTrue(Address.isValidAddress("123456 Sengkang")); // postal code first
        assertTrue(Address.isValidAddress("Sengkang 123456")); // postal code last
        assertTrue(Address.isValidAddress("Sengkang 123456 Street 11")); // postal code middle
        assertTrue(Address.isValidAddress("Postal: 123456, Singapore")); // with punctuation
        assertTrue(Address.isValidAddress("Blk 123 Sengkang Street 11, Singapore 123456")); // long address
    }

    @Test
    public void getValidationMessage_null_returnsBlankMessage() {
        assertEquals(Address.MESSAGE_CONSTRAINTS_BLANK,
                Address.getValidationMessage(null));
    }

    @Test
    public void getValidationMessage_blank_returnsBlankMessage() {
        assertEquals(Address.MESSAGE_CONSTRAINTS_BLANK,
                Address.getValidationMessage(" "));
    }

    @Test
    public void getValidationMessage_noPostalCode_returnsPostalCodeMessage() {
        assertEquals(Address.MESSAGE_CONSTRAINTS_POSTAL_CODE,
                Address.getValidationMessage("Blk 123 Jurong East St 32"));
    }

    @Test
    public void getValidationMessage_validAddress_returnsNull() {
        assertNull(Address.getValidationMessage("Blk 123 Jurong East St 32, 123456"));
    }

    @Test
    public void getPostalCode_returnsCorrectPostalCode() {
        Address address = new Address("Blk 123 Jurong East St 32, 123456");
        assertEquals("123456", address.getPostalCode().toString());
    }

    @Test
    public void constructor_postalCodeWithInvalidPrefix00_throwsIllegalArgumentException() {
        // 001234 has prefix 00, which is below the valid 01-82 range
        assertThrows(IllegalArgumentException.class, () -> new Address("Blk 1 Street 001234"));
    }

    @Test
    public void constructor_postalCodeWithInvalidPrefix83_throwsIllegalArgumentException() {
        // 831234 has prefix 83, which is above the valid 01-82 range
        assertThrows(IllegalArgumentException.class, () -> new Address("Blk 1 Street 831234"));
    }

    @Test
    public void isValidAddress_fiveDigitNumber_invalid() {
        assertFalse(Address.isValidAddress("12345")); // 5-digit number, no 6-digit postal code
    }

    @Test
    public void isValidAddress_sevenDigitNumber_invalid() {
        assertFalse(Address.isValidAddress("1234567")); // 7-digit number, no 6-digit postal code
    }

    @Test
    public void isValidAddress_validPrefixBoundaries_valid() {
        assertTrue(Address.isValidAddress("Blk 1 Street 012345")); // prefix 01 — minimum valid
        assertTrue(Address.isValidAddress("Blk 1 Street 823456")); // prefix 82 — maximum valid
    }

    @Test
    public void isValidAddress_multipleNumbers_usesFirstValidPostalCode() {
        // Address contains two 6-digit numbers; first one (123456) has valid prefix
        assertTrue(Address.isValidAddress("Unit 100000, Block 123456"));
    }

    @Test
    public void equals() {
        Address address = new Address("Blk 123 Sengkang Street 11, Singapore 123456");

        // same values -> returns true
        assertTrue(address.equals(new Address("Blk 123 Sengkang Street 11, Singapore 123456")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("011111")));
    }
}
