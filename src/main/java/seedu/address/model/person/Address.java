package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.PostalCode.extractPostalCode;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS_BLANK = "Address should not be blank.";
    public static final String MESSAGE_CONSTRAINTS_POSTAL_CODE = "Address must contain a 6-digit postal code.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The address must also contain a 6-digit postal code.
     */
    private static final String VALIDATION_REGEX = "^(?=.*\\b\\d{6}\\b).+$";

    public final String value;
    public final PostalCode postalCode;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), getValidationMessage(address));
        this.value = address;
        this.postalCode = new PostalCode(extractPostalCode(address));
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns specific validation error message based on the given address.
     */
    public static String getValidationMessage(String address) {
        if (address == null || address.isBlank()) {
            return MESSAGE_CONSTRAINTS_BLANK;
        }

        if (!address.matches(VALIDATION_REGEX)) {
            return MESSAGE_CONSTRAINTS_POSTAL_CODE;
        }

        return null;
    }

    /**
     * Returns the postal code of this address.
     */
    public PostalCode getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the postal code prefix of this address.
     */
    public int getPostalPrefixFromAddress() {
        return getPostalCode().getPostalPrefixFromPostalCode();
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
