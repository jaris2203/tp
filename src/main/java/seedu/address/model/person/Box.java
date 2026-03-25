package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Box in Client2Door
 */
public class Box implements Comparable<Box> {

    public static final String MESSAGE_CONSTRAINTS = "Box names should follow the format '[type]-[number]', where"
            + "type consists of only lowercase letters and number is a single digit number";
    public static final String VALIDATION_REGEX = "^[a-z]+-\\d{1}$";

    public final String boxName;
    public final ExpiryDate expiryDate;

    /**
     * Constructs a {@code Box}.
     *
     * @param boxName A valid box name.
     */
    public Box(String boxName, ExpiryDate expiryDate) {
        requireNonNull(boxName);
        requireNonNull(expiryDate);
        checkArgument(isValidBoxName(boxName), MESSAGE_CONSTRAINTS);
        this.boxName = boxName;
        this.expiryDate = expiryDate;
    }

    public static boolean isValidBoxName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getBoxName() {
        return boxName;
    }

    @Override
    public int compareTo(Box other) {
        if (this.expiryDate.compareTo(other.expiryDate) < 0) {
            return -1;
        }
        if (this.expiryDate.compareTo(other.expiryDate) > 0) {
            return 1;
        }
        return this.boxName.compareTo(other.boxName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Box)) {
            return false;
        }

        Box otherBox = (Box) other;
        return boxName.equals(otherBox.boxName) && expiryDate.equals(otherBox.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boxName, expiryDate);
    }

    /**
     * Formats state as text for viewing
     * @return Formatted package name
     */
    @Override
    public String toString() {
        return '[' + boxName + "|" + expiryDate + ']';
    }
}
