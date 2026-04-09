package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.Driver;

public class JsonAdaptedDriverTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_PHONE = "91234567";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";

    @Test
    public void toModelType_validDriverDetails_returnsDriver() throws Exception {
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(VALID_NAME, VALID_PHONE);
        Driver expected = new Driver(new Name(VALID_NAME), new Phone(VALID_PHONE));
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(null, VALID_PHONE);
        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedDriver.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()),
                adapted::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(INVALID_NAME, VALID_PHONE);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(VALID_NAME, null);
        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedDriver.MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()),
                adapted::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(VALID_NAME, INVALID_PHONE);
        assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }

    @Test
    public void constructor_fromDriver_roundTrip() throws Exception {
        Driver original = new Driver(new Name(VALID_NAME), new Phone(VALID_PHONE));
        JsonAdaptedDriver adapted = new JsonAdaptedDriver(original);
        assertEquals(original, adapted.toModelType());
    }
}
