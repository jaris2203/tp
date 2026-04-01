package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.Driver;

/**
 * Jackson-friendly version of {@link Driver}.
 */
class JsonAdaptedDriver {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Driver's %s field is missing!";

    private final String name;
    private final String phone;

    /**
     * Constructs a {@code JsonAdaptedDriver} with the given driver details.
     */
    @JsonCreator
    public JsonAdaptedDriver(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Converts a given {@code Driver} into this class for Jackson use.
     */
    public JsonAdaptedDriver(Driver source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
    }

    /**
     * Converts this Jackson-friendly adapted driver object into the model's {@code Driver} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted driver.
     */
    public Driver toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        return new Driver(modelName, modelPhone);
    }
}
