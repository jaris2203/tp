package seedu.address.model.commons.name;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isValidName_singleCharacter_valid() {
        assertTrue(Name.isValidName("a")); // single alphanumeric char
        assertTrue(Name.isValidName("Z")); // single uppercase char
        assertTrue(Name.isValidName("1")); // single digit
    }

    @Test
    public void isValidName_leadingSpace_invalid() {
        assertFalse(Name.isValidName(" Alice")); // starts with space — regex requires alnum first
        assertFalse(Name.isValidName(" 1")); // digit preceded by space
    }

    @Test
    public void isValidName_trailingSpace_valid() {
        assertTrue(Name.isValidName("Alice ")); // trailing space allowed by regex
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Name lower = new Name("alice");
        Name upper = new Name("ALICE");
        Name mixed = new Name("Alice");
        // Name.equals uses equalsIgnoreCase
        assertTrue(lower.equals(upper));
        assertTrue(lower.equals(mixed));
        assertTrue(upper.equals(mixed));
    }

    @Test
    public void equals_differentNames_returnsFalse() {
        assertFalse(new Name("Alice").equals(new Name("Bob")));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void hashCode_sameValue_sameHash() {
        Name n1 = new Name("Alice");
        Name n2 = new Name("Alice");
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    public void toString_returnsFullName() {
        Name name = new Name("Alice Tan");
        assertEquals("Alice Tan", name.toString());
    }
}
