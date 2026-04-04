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

    public static final String MESSAGE_CONSTRAINTS_SIX_DIGIT = "Postal code must be a 6-digit number.";
    public static final String MESSAGE_CONSTRAINTS_PREFIX = "Postal code prefix must be between 01 and 82.";
    public static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\b\\d{6}\\b");

    /**
     * The postal code must be a 6-digit number.
     */
    private static final String VALIDATION_REGEX = "^\\d{6}$";
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(VALIDATION_REGEX);
    private static final int MIN_PREFIX = 1;
    private static final int MAX_PREFIX = 82;

    public final String value;

    /**
     * Constructs a {@code PostalCode}.
     *
     * @param postalCode A valid postal code.
     */
    public PostalCode(String postalCode) {
        requireNonNull(postalCode);
        checkArgument(isValidPostalCode(postalCode), getValidationMessage(postalCode));
        this.value = postalCode;
    }

    /**
     * Returns true if a given string is a valid postal code with a valid prefix.
     */
    public static boolean isValidPostalCode(String test) {
        if (test == null || !VALIDATION_PATTERN.matcher(test).matches()) {
            return false;
        }
        return isValidPrefix(Integer.parseInt(test.substring(0, 2)));
    }

    /**
     * Returns true if the given prefix is a valid Singapore postal code prefix (01 to 82).
     */
    public static boolean isValidPrefix(int prefix) {
        return prefix >= MIN_PREFIX && prefix <= MAX_PREFIX;
    }

    /**
     * Returns specific validation error message based on the given postalCode.
     */
    public static String getValidationMessage(String postalCode) {
        if (postalCode == null || postalCode.isBlank() || !VALIDATION_PATTERN.matcher(postalCode).matches()) {
            return MESSAGE_CONSTRAINTS_SIX_DIGIT;
        }

        if (!isValidPrefix(Integer.parseInt(postalCode.substring(0, 2)))) {
            return MESSAGE_CONSTRAINTS_PREFIX;
        }

        return null;
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
