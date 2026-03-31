package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Adds one or more boxes to a person in the address book.
 */
public class AddBoxCommand extends Command {

    public static final String COMMAND_WORD = "addbox";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or more boxes with a fixed expiry date to "
            + "the person identified by the name used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_BOX + "BOX_NAME "
            + "[" + PREFIX_BOX + "BOX_NAME:EXPIRY_DATE]... "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_BOX + "box-1:2026-01-01 "
            + PREFIX_BOX + "box-2:2026-12-31";

    public static final String MESSAGE_SUCCESS = "Added %1$s to Person: %2$s";
    public static final String MESSAGE_EXISTING_BOX_NAME = "One or more of the box names added already exists under "
            + "Person: %1$s. Use the editbox command to edit details for existing boxes.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person with the input name does not exist.";

    private final Name subscriberName;
    private final Set<Box> boxesToAdd;

    /**
     * Creates an AddBoxCommand to add the boxes to a specified subscriber.
     */
    public AddBoxCommand(Name subscriberName, Set<Box> boxesToAdd) {
        requireNonNull(subscriberName);
        requireNonNull(boxesToAdd);
        this.subscriberName = subscriberName;
        this.boxesToAdd = boxesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = null;
        for (Person person : lastShownList) {
            if (person.getName().equals(this.subscriberName)) {
                personToEdit = person;
                break;
            }
        }

        if (personToEdit == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Set<String> existingBoxNames = personToEdit.getBoxes().stream()
                .map(Box::getBoxName)
                .collect(Collectors.toSet());

        boolean hasMatchingBoxNames = boxesToAdd.stream()
                .map(Box::getBoxName)
                .anyMatch(existingBoxNames::contains);

        if (hasMatchingBoxNames) {
            throw new CommandException(String.format(MESSAGE_EXISTING_BOX_NAME, personToEdit.getName()));
        }
        Person editedPerson = createPersonWithUpdatedBoxes(personToEdit, boxesToAdd);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, boxesToAdd, personToEdit.getName()));
    }

    private static Person createPersonWithUpdatedBoxes(Person personToEdit, Set<Box> boxesToAdd) {
        assert personToEdit != null;
        assert boxesToAdd != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Remark remark = personToEdit.getRemark();
        DeliveryStatus deliveryStatus = personToEdit.getDeliveryStatus();
        Set<Tag> tags = personToEdit.getTags();

        Set<Box> updatedBoxes = new HashSet<>(personToEdit.getBoxes());
        updatedBoxes.addAll(boxesToAdd);
        return new Person(name, phone, email, address, updatedBoxes, remark, deliveryStatus, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof AddBoxCommand)) {
            return false;
        }

        AddBoxCommand otherAddBoxCommand = (AddBoxCommand) other;
        return subscriberName.equals(otherAddBoxCommand.subscriberName)
                && boxesToAdd.equals(otherAddBoxCommand.boxesToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subscriberName", subscriberName)
                .add("boxesToAdd", boxesToAdd)
                .toString();
    }
}
