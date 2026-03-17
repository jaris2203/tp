package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ORDER_DESCRIPTION = "1 cake";
    public static final String DEFAULT_EXPIRY_DATE = "2026-12-31";
    public static final String DEFAULT_DELIVERY_STATUS = "pending";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private ExpiryDate expiryDate;
    private DeliveryStatus deliveryStatus;
    private Set<Tag> tags;
    private Set<Box> boxes;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_ORDER_DESCRIPTION);
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        deliveryStatus = new DeliveryStatus(DEFAULT_DELIVERY_STATUS);
        tags = new HashSet<>();
        boxes = SampleDataUtil.getBoxSet("box-1");
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        expiryDate = personToCopy.getExpiryDate();
        deliveryStatus = personToCopy.getDeliveryStatus();
        tags = new HashSet<>(personToCopy.getTags());
        boxes = new HashSet<>(personToCopy.getBoxes());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code boxes} into a {@code Set<Box>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withBoxes(String ... boxes) {
        this.boxes = SampleDataUtil.getBoxSet(boxes);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code DeliveryStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = new DeliveryStatus(deliveryStatus);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, boxes, remark, expiryDate, deliveryStatus, tags);
    }

}
