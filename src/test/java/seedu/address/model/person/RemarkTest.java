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
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "#cake";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null Remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid Remarks
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only
        assertFalse(Remark.isValidRemark("#")); // only non-alphanumeric characters
        assertFalse(Remark.isValidRemark("cake!")); // contains non-alphanumeric characters

        // valid Remarks
        assertTrue(Remark.isValidRemark("2 cakes")); // alphanumeric with spaces
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("Large Latte")); // letters with capitals
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
}
