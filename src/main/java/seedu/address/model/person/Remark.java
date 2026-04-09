package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Remark in the address book.
 */
public class Remark {

    public static final String DEFAULT_REMARK = "No remark";
    public static final int MAX_LENGTH = 40;

    public static final String MESSAGE_INVALID_CHARACTERS =
            "Remarks should only contain alphanumeric characters and spaces";
    public static final String MESSAGE_TOO_LONG =
            "Remarks must be at most " + MAX_LENGTH + " characters long";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * <p>A valid remark must:</p>
     * <ul>
     *     <li>Contain only alphanumeric characters and spaces</li>
     *     <li>Not be blank</li>
     *     <li>Not exceed {@link #MAX_LENGTH} characters</li>
     * </ul>
     *
     * @param description A valid remark string.
     * @throws IllegalArgumentException if {@code description} contains invalid
     *     characters or exceeds {@link #MAX_LENGTH} characters.
     */
    public Remark(String description) {
        requireNonNull(description);

        if (!description.matches(VALIDATION_REGEX)) {
            checkArgument(false, MESSAGE_INVALID_CHARACTERS);
        }
        if (description.length() > MAX_LENGTH) {
            checkArgument(false, MESSAGE_TOO_LONG);
        }

        this.value = description;
    }

    /**
     * Constructs a default {@code Remark} with no description (used for reset).
     */
    public Remark() {
        this.value = DEFAULT_REMARK;
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

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
