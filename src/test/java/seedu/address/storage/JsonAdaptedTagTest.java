package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class JsonAdaptedTagTest {

    private static final String VALID_TAG = "friends";
    private static final String INVALID_TAG = "#friend"; // # is not allowed

    @Test
    public void toModelType_validTag_returnsTag() throws Exception {
        JsonAdaptedTag adapted = new JsonAdaptedTag(VALID_TAG);
        Tag expected = new Tag(VALID_TAG);
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        JsonAdaptedTag adapted = new JsonAdaptedTag(INVALID_TAG);
        assertThrows(IllegalValueException.class, Tag.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }

    @Test
    public void constructor_fromTag_roundTrip() throws Exception {
        Tag original = new Tag(VALID_TAG);
        JsonAdaptedTag adapted = new JsonAdaptedTag(original);
        assertEquals(original, adapted.toModelType());
    }

    @Test
    public void getTagName_returnsCorrectName() {
        JsonAdaptedTag adapted = new JsonAdaptedTag(VALID_TAG);
        assertEquals(VALID_TAG, adapted.getTagName());
    }
}
