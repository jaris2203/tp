package seedu.address.model.person;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BoxTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Box(null));
    }

    @Test
    public void constructor_invalidBoxName_throwsIllegalArgumentException() {
        String invalidBoxName = "";
        assertThrows(IllegalArgumentException.class, () -> new Box(invalidBoxName));
    }

    @Test
    public void isValidBoxName() {
        // null box name
        assertThrows(NullPointerException.class, () -> Box.isValidBoxName(null));
    }
}
