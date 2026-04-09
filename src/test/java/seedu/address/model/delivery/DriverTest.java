package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;

public class DriverTest {

    private static final Name VALID_NAME = new Name("Kyle");
    private static final Phone VALID_PHONE = new Phone("91234567");

    @Test
    public void constructor_validNameAndPhone_success() {
        Driver driver = new Driver(VALID_NAME, VALID_PHONE);
        assertEquals(VALID_NAME, driver.getName());
        assertEquals(VALID_PHONE, driver.getPhone());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Driver(null, VALID_PHONE));
    }

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Driver(VALID_NAME, null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Driver driver = new Driver(VALID_NAME, VALID_PHONE);
        assertTrue(driver.equals(driver));
    }

    @Test
    public void equals_sameNameAndPhone_returnsTrue() {
        Driver a = new Driver(new Name("Kyle"), new Phone("91234567"));
        Driver b = new Driver(new Name("Kyle"), new Phone("91234567"));
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Driver a = new Driver(new Name("Kyle"), VALID_PHONE);
        Driver b = new Driver(new Name("John"), VALID_PHONE);
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_differentPhone_returnsFalse() {
        Driver a = new Driver(VALID_NAME, new Phone("91234567"));
        Driver b = new Driver(VALID_NAME, new Phone("98765432"));
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_null_returnsFalse() {
        Driver driver = new Driver(VALID_NAME, VALID_PHONE);
        assertFalse(driver.equals(null));
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        Driver a = new Driver(new Name("Kyle"), new Phone("91234567"));
        Driver b = new Driver(new Name("Kyle"), new Phone("91234567"));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toString_containsNameAndPhone() {
        Driver driver = new Driver(VALID_NAME, VALID_PHONE);
        String result = driver.toString();
        assertTrue(result.contains("Kyle"));
        assertTrue(result.contains("91234567"));
    }
}
