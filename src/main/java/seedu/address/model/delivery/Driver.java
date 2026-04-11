package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;

/**
 * Represents a Driver in Client2Door.
 * A Driver is identified by a {@link Name} and a {@link Phone} number.
 * This class is immutable.
 */
public class Driver {

    private final Name name;
    private final Phone phone;

    /**
     * Constructs a {@code Driver} with the specified {@code Name} and {@code Phone}.
     *
     * @param name  the name of the driver
     * @param phone the phone number of the driver
     */
    public Driver(Name name, Phone phone) {

        requireAllNonNull(name, phone);

        this.name = name;
        this.phone = phone;
    }

    public Name getName() {
        return this.name;
    }

    public Phone getPhone() {
        return this.phone;
    }

    /**
     * Compares this driver to the specified object. The result is {@code true} if and only if
     * the argument is not {@code null}, is a {@code Driver} object, and has the same
     * {@code Name} and {@code Phone} as this driver.
     *
     * @param other the object to compare this {@code Driver} against
     * @return {@code true} if the given object represents a {@code Driver}
     *     equivalent to this driver, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Driver)) {
            return false;
        }

        Driver otherDriver = (Driver) other;

        return name.equals(otherDriver.name)
                || phone.equals(otherDriver.phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.name)
                .add("phone", this.phone)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
}
