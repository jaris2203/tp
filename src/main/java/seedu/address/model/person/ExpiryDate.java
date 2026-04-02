package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Represents a Person's expiry date in the address book.
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry date should be a valid date in one of these formats: "
            + "yyyy-MM-dd "
            + "dd/MM/yyyy\n"
            + "Example: "
            + "2026-12-31, or "
            + "31/12/2026.";

    public static final DateTimeFormatter STANDARD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final List<DateTimeFormatter> FORMATS = List.of(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            STANDARD_FORMAT
    );


    public final String value;

    /**
     * Constructs an {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = normalizeExpiryDate(expiryDate);
    }

    /**
     * Normalize the accepted expiry date formats to a standardized yyyy-MM-dd format.
     */
    public static String normalizeExpiryDate(String expiryDate) {
        String trimmed = expiryDate.trim();

        for (DateTimeFormatter formatter : FORMATS) {
            try {
                // this line may accept invalid dates like 2026-12-30, and parses it to return 2026-12-28.
                LocalDate date = LocalDate.parse(trimmed, formatter);
                String reformatted = date.format(formatter);

                // this block prevents the above comment from being accepted.
                if (trimmed.equals(reformatted)) {
                    return date.format(STANDARD_FORMAT);
                }
            } catch (DateTimeParseException e) {
                // do nothing, loop through the list of formats.
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        requireNonNull(test);
        String trimmed = test.trim();

        for (DateTimeFormatter formatter : FORMATS) {
            try {
                // this line may accept invalid dates like 2026-12-30, and parses it to return 2026-12-28.
                LocalDate date = LocalDate.parse(trimmed, formatter);
                String reformatted = date.format(formatter);

                // this block prevents the above comment from being accepted.
                if (trimmed.equals(reformatted)) {
                    return true;
                }
            } catch (DateTimeParseException e) {
                // do nothing, loop through the list of formats.
            }
        }
        return false;
    }

    public int compareTo(ExpiryDate other) {
        return this.value.compareTo(other.value);
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
