package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Box;
import seedu.address.model.person.ExpiryDate;

public class JsonAdaptedBoxTest {

    private static final String VALID_BOX_NAME = "box-1";
    private static final String VALID_EXPIRY_DATE = "2026-12-31";
    private static final String INVALID_BOX_NAME = "Box 1"; // uppercase + space fails ^[a-z]+-\d{1}$
    private static final String INVALID_EXPIRY_DATE = "FAKE DATE";

    @Test
    public void toModelType_validBoxDetails_returnsBox() throws Exception {
        JsonAdaptedBox adapted = new JsonAdaptedBox(VALID_BOX_NAME, VALID_EXPIRY_DATE);
        Box expected = new Box(VALID_BOX_NAME, new ExpiryDate(VALID_EXPIRY_DATE));
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_invalidBoxName_throwsIllegalValueException() {
        JsonAdaptedBox adapted = new JsonAdaptedBox(INVALID_BOX_NAME, VALID_EXPIRY_DATE);
        assertThrows(IllegalValueException.class, Box.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }

    @Test
    public void toModelType_invalidExpiryDate_throwsIllegalValueException() {
        JsonAdaptedBox adapted = new JsonAdaptedBox(VALID_BOX_NAME, INVALID_EXPIRY_DATE);
        assertThrows(IllegalValueException.class, ExpiryDate.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }

    @Test
    public void constructor_fromBox_roundTrip() throws Exception {
        Box original = new Box(VALID_BOX_NAME, new ExpiryDate(VALID_EXPIRY_DATE));
        JsonAdaptedBox adapted = new JsonAdaptedBox(original);
        assertEquals(original, adapted.toModelType());
    }
}
