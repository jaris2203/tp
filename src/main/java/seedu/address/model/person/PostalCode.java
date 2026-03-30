package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a postal code extracted from an address.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {

    public static final String MESSAGE_CONSTRAINTS = "Postal code must be a 6-digit number.";
    public static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\b\\d{6}\\b");

    /**
     * The postal code must be a 6-digit number.
     */
    private static final String VALIDATION_REGEX = "^\\d{6}$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);

    public final String value;

    /**
     * Constructs a {@code PostalCode}.
     *
     * @param postalCode A valid postal code.
     */
    public PostalCode(String postalCode) {
        requireNonNull(postalCode);
        checkArgument(isValidPostalCode(postalCode), MESSAGE_CONSTRAINTS);
        this.value = postalCode;
    }

    /**
     * Returns true if a given string is a valid postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test != null && VALIDATION_PATTERN.matcher(test).matches();
    }

    /**
     * Extracts the postal code from the given address string.
     *
     * @param address The address string to extract the postal code from.
     * @return The postal code as a string.
     * @throws IllegalArgumentException if the postal code is not found in the address.
     */
    public static String extractPostalCode(String address) {
        Matcher postalCodeMatcher = POSTAL_CODE_PATTERN.matcher(address);
        if (postalCodeMatcher.find()) {
            return postalCodeMatcher.group();
        }
        throw new IllegalArgumentException("No valid postal code found in: " + address);
    }

    /**
     * Returns the postal code value as an integer.
     */
    public int getValue() {
        return Integer.parseInt(value);
    }

    /**
     * Returns the postal code prefix, which is the first 2 digits of the postal code, as an integer.
     */
    public int getPostalPrefixFromPostalCode() {
        return Integer.parseInt(value.substring(0, 2));
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
        if (!(other instanceof PostalCode)) {
            return false;
        }

        PostalCode otherPostalCode = (PostalCode) other;
        return value.equals(otherPostalCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
