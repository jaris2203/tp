package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void constructor_blankPostalCode_throwsIllegalArgumentException() {
        String blankPostalCode = "";
        Assert.assertThrows(IllegalArgumentException.class, PostalCode.MESSAGE_CONSTRAINTS_SIX_DIGIT, () ->
                new PostalCode(blankPostalCode));
    }

    @Test
    public void constructor_postalCodeWithPrefix83_throwsIllegalArgumentException() {
        String postalCode = "831234";
        Assert.assertThrows(IllegalArgumentException.class, PostalCode.MESSAGE_CONSTRAINTS_PREFIX, () ->
                new PostalCode(postalCode));
    }

    @Test
    public void constructor_postalCodeWithPrefix00_throwsIllegalArgumentException() {
        String postalCode = "001234";
        Assert.assertThrows(IllegalArgumentException.class, PostalCode.MESSAGE_CONSTRAINTS_PREFIX, () ->
                new PostalCode(postalCode));
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
        assertFalse(PostalCode.isValidPostalCode("004567")); // prefix below 01
        assertFalse(PostalCode.isValidPostalCode("834567")); // prefix above 82
        assertFalse(PostalCode.isValidPostalCode("12345a")); // contains letter

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("123456")); // normal
        assertTrue(PostalCode.isValidPostalCode("012345")); // starting with 0
    }

    @Test
    public void isValidPrefix_validPrefix_returnsTrue() {
        assertTrue(PostalCode.isValidPrefix(1)); // min boundary
        assertTrue(PostalCode.isValidPrefix(82)); // max boundary
        assertTrue(PostalCode.isValidPrefix(40)); // middle
    }

    @Test
    public void isValidPrefix_invalidPrefix_returnsFalse() {
        assertFalse(PostalCode.isValidPrefix(0)); // below min
        assertFalse(PostalCode.isValidPrefix(83)); // above max
        assertFalse(PostalCode.isValidPrefix(-1)); // negative
    }

    @Test
    public void getValidationMessage_nullOrBlankOrNonSixDigit_returnsSixDigitMessage() {
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_SIX_DIGIT, PostalCode.getValidationMessage(null));
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_SIX_DIGIT, PostalCode.getValidationMessage(""));
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_SIX_DIGIT, PostalCode.getValidationMessage("12345"));
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_SIX_DIGIT, PostalCode.getValidationMessage("1234567"));
    }

    @Test
    public void getValidationMessage_invalidPrefix_returnsPrefixMessage() {
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_PREFIX, PostalCode.getValidationMessage("001234"));
        assertEquals(PostalCode.MESSAGE_CONSTRAINTS_PREFIX, PostalCode.getValidationMessage("831234"));
    }

    @Test
    public void getValidationMessage_validPostalCode_returnsNull() {
        assertNull(PostalCode.getValidationMessage("123456"));
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
}
