package seedu.address.model.person;

/**
 * Represents a Person's delivery status in the address book.
 * Guarantees: immutable; can be constructed from valid strings via {@link #fromString(String)}
 */
public enum DeliveryStatus {
    PENDING("Pending"),
    PACKING("Packing"),
    DELIVERED("Delivered");

    public static final String MESSAGE_CONSTRAINTS =
            "Delivery status should be one of the following: 'Pending', 'Packing', 'Delivered'";

    public final String deliveryStatus;

    /**
     * Constructs a {@code DeliveryStatus}.
     *
     * @param deliveryStatus A valid delivery status.
     */
    DeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString() {
        return deliveryStatus;
    }

    /**
     * Converts a given string to a corresponding {@code DeliveryStatus} enum.
     * The input string is case-insensitive and leading/trailing whitespace is ignored.
     *
     * @param deliveryStatus the string representation of the delivery status
     * @return the corresponding {@code DeliveryStatus} enum
     * @throws IllegalArgumentException if the input string does not match any valid delivery status
     */
    public static DeliveryStatus fromString(String deliveryStatus) {
        try {
            return DeliveryStatus.valueOf(deliveryStatus.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }
}
