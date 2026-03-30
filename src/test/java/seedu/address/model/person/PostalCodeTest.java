package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.PostalCode.extractPostalCode;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

public class PostalCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PostalCode(null));
    }

    @Test
    public void constructor_invalidPostalCode_throwsIllegalArgumentException() {
        String invalidPostalCode = "";
        Assert.assertThrows(IllegalArgumentException.class, PostalCode.MESSAGE_CONSTRAINTS, () ->
                new PostalCode(invalidPostalCode));
    }

    @Test
    public void isValidPostalCode() {
        // null postal code
        assertFalse(PostalCode.isValidPostalCode(null));

        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("12345")); // only 5 digits
        assertFalse(PostalCode.isValidPostalCode("1234567")); // 7 digits

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("123456")); // normal
        assertTrue(PostalCode.isValidPostalCode("012345")); // starting with 0
    }

    @Test
    public void equals() {
        PostalCode postalCode = new PostalCode("123456");

        // same values -> returns true
        assertTrue(postalCode.equals(new PostalCode("123456")));

        // same object -> returns true
        assertTrue(postalCode.equals(postalCode));

        // null -> returns false
        assertFalse(postalCode.equals(null));

        // different types -> returns false
        assertFalse(postalCode.equals(5.0f));

        // different values -> returns false
        assertFalse(postalCode.equals(new PostalCode("011111")));
    }

    @Test
    public void extractPostalCode_validAddress_returnsPostalCode() {
        String address = "Blk 123 Sengkang Street 11, Singapore 123456";
        assertEquals("123456", extractPostalCode(address));
    }

    @Test
    public void extractPostalCode_addressWithoutPostalCode_throwsException() {
        String address = "Blk 123, Singapore";
        assertThrows(IllegalArgumentException.class, () ->
                extractPostalCode(address));
    }

    @Test
    public void extractPostalCode_nullAddress_throwsException() {
        assertThrows(NullPointerException.class, () ->
                extractPostalCode(null));
    }

    @Test
    public void getPostalPrefixFromPostalCode_forPostalCodeStartingWithNonZero_returnsFirstTwoDigits() {
        PostalCode postalCode = new PostalCode("123456");
        assertEquals(12, postalCode.getPostalPrefixFromPostalCode());
    }

    @Test
    public void getPostalPrefixFromPostalCode_forPostalCodeStartingWithZero_returnsCorrectPrefixFromPostalCode() {
        PostalCode postalCode = new PostalCode("045678");
        assertEquals(4, postalCode.getPostalPrefixFromPostalCode());
    }
}
