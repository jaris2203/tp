package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Remark remark;
    private final DeliveryStatus deliveryStatus;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ExpiryDate expiryDate;
    private final Set<Box> boxes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Person(Name name, Phone phone, Email email, Address address,
                  OrderDescription orderDescription, ExpiryDate expiryDate,
                  DeliveryStatus deliveryStatus, Set<Box> boxes, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, orderDescription, deliveryStatus, boxes, tags);
=======
    public Person(Name name, Phone phone, Email email, Address address, Set<Box> boxes,
                  Remark remark, ExpiryDate expiryDate,
                  DeliveryStatus deliveryStatus, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, remark, deliveryStatus, tags);
>>>>>>> da280918 (Refactor order description)

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
<<<<<<< HEAD
        this.orderDescription = orderDescription;
=======
        this.boxes.addAll(boxes);
        this.remark = remark;
>>>>>>> da280918 (Refactor order description)
        this.expiryDate = expiryDate;
        this.deliveryStatus = deliveryStatus;
        this.boxes.addAll(boxes);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

<<<<<<< HEAD
    public OrderDescription getOrderDescription() {
        return orderDescription;
=======
    /**
     * Returns an immutable box set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Box> getBoxes() {
        return Collections.unmodifiableSet(boxes);
    }

    public Remark getRemark() {
        return remark;
>>>>>>> da280918 (Refactor order description)
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * Returns an immutable box set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Box> getBoxes() {
        return Collections.unmodifiableSet(boxes);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
<<<<<<< HEAD
                && orderDescription.equals(otherPerson.orderDescription)
=======
                && boxes.equals(otherPerson.boxes)
                && remark.equals(otherPerson.remark)
>>>>>>> da280918 (Refactor order description)
                && expiryDate.equals(otherPerson.expiryDate)
                && deliveryStatus.equals(otherPerson.deliveryStatus)
                && boxes.equals(otherPerson.boxes)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
<<<<<<< HEAD
        return Objects.hash(name, phone, email, address, orderDescription, expiryDate, deliveryStatus, boxes, tags);
=======
        return Objects.hash(name, phone, email, address, boxes, remark, expiryDate, deliveryStatus, tags);
>>>>>>> da280918 (Refactor order description)
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
<<<<<<< HEAD
                .add("orderDescription", orderDescription)
=======
                .add("boxes", boxes)
                .add("remark", remark)
>>>>>>> da280918 (Refactor order description)
                .add("expiryDate", expiryDate)
                .add("deliveryStatus", deliveryStatus)
                .add("boxes", boxes)
                .add("tags", tags)
                .toString();
    }

}
