package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Box in Client2Door
 */
public class Box implements Comparable<Box> {

    public static final String MESSAGE_CONSTRAINTS = "Box names should follow the format '[type]-[number]',"
            + " where type consists of only lowercase letters and number is a single digit number";
    public static final String MESSAGE_INVALID_BOX_WITH_EXPIRY_FORMAT =
            "Boxes must be in the format b/BOX_NAME:NUMBER_OF_MONTHS";
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

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns true if the expiry date of the {@code Box} instance has passed.
     */
    public boolean isExpired() {
        for (DateTimeFormatter formatter : ExpiryDate.FORMATS) {
            try {
                LocalDate date = LocalDate.parse(expiryDate.value, formatter);
                return date.isBefore(LocalDate.now());
            } catch (DateTimeParseException e) {
                // do nothing, iterate to next format.
            }
        }
        return false;
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
