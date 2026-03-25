package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's expiry date in the address book.
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry date should be a valid date in the format yyyy-MM-dd, "
                    + "for example, 2026-12-31.";

    public final String value;
    public final LocalDate localDateValue;

    /**
     * Constructs an {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = expiryDate;
        localDateValue = LocalDate.parse(expiryDate);
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        requireNonNull(test);
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public int compareTo(ExpiryDate other) {
        return this.localDateValue.compareTo(other.localDateValue);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpiryDate)) {
            return false;
        }

        ExpiryDate otherExpiryDate = (ExpiryDate) other;
        return value.equals(otherExpiryDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
