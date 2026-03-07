package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class OrderDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Order descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    private final String value;

    public OrderDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidOrderDescription(description), MESSAGE_CONSTRAINTS);
        this.value = description;
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

        if (!(other instanceof OrderDescription)) {
            return false;
        }

        OrderDescription otherOrderDescription = (OrderDescription) other;
        return value.equals(otherOrderDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
