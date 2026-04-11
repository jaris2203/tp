package seedu.address.model.commons.name;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphabets, hyphen, apostrophe and spaces, and it should not be blank.\n"
            + "Examples: Brian O'Connor, Le-Jay \n"
            + "Invalid Examples: Brian O ' Connor, Le'-Jay";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}]+(?:[ '-][\\p{Alpha}]+)*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = toTitleCase(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    /**
     * Converts a name to title case.
     */
    private static String toTitleCase(String name) {
        String[] words = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String[] parts = word.toLowerCase().split("((?<=['-])|(?=['-]))");
                for (String part : parts) {
                    if (part.equals("'") || part.equals("-")) {
                        sb.append(part);
                    } else {
                        sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
                    }
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.trim().equalsIgnoreCase(otherName.fullName.trim());
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
