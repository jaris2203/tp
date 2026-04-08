package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidCharacters_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Remark("#cake"));
    }

    @Test
    public void constructor_tooLong_throwsIllegalArgumentException() {
        // 41 characters — one over the limit
        String tooLong = "a".repeat(Remark.MAX_LENGTH + 1);
        assertThrows(IllegalArgumentException.class, () -> new Remark(tooLong));
    }

    @Test
    public void constructor_exactMaxLength_success() {
        // exactly 40 characters — should pass
        String exactLength = "a".repeat(Remark.MAX_LENGTH);
        Remark remark = new Remark(exactLength);
        assertEquals(exactLength, remark.value);
    }

    @Test
    public void constructor_validRemark_success() {
        // alphanumeric with spaces
        assertEquals("2 cakes", new Remark("2 cakes").value);
        // numbers only
        assertEquals("12345", new Remark("12345").value);
        // letters with capitals
        assertEquals("Large Latte", new Remark("Large Latte").value);
    }

    @Test
    public void equals() {
        Remark remark = new Remark("2 cakes");

        // same values -> returns true
        assertTrue(remark.equals(new Remark("2 cakes")));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("3 cakes")));
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        Remark first = new Remark("2 cakes");
        Remark second = new Remark("2 cakes");
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void isValidRemark_boundary_singleCharacter_valid() {
        assertTrue(Remark.isValidRemark("a")); // single alphanumeric char
        assertTrue(Remark.isValidRemark("1")); // single digit
    }

    @Test
    public void isValidRemark_boundary_leadingSpace_invalid() {
        // VALIDATION_REGEX = "[\p{Alnum}][\p{Alnum} ]*" — same as Name, must start with alnum
        assertFalse(Remark.isValidRemark(" cakes")); // starts with space
    }

    @Test
    public void isValidRemark_boundary_specialCharsAfterValid_invalid() {
        assertFalse(Remark.isValidRemark("cakes!")); // special char at end
        assertFalse(Remark.isValidRemark("2-cakes")); // hyphen not allowed
        assertFalse(Remark.isValidRemark("cake#")); // hash not allowed
    }

    @Test
    public void toString_returnsValue() {
        Remark remark = new Remark("2 cakes");
        assertEquals("2 cakes", remark.toString());
    }

    @Test
    public void hashCode_differentValues_differentHash() {
        Remark first = new Remark("2 cakes");
        Remark second = new Remark("3 cakes");
        assertFalse(first.hashCode() == second.hashCode());
    }
}
