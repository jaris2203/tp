package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_BOX + "BOXES:EXPIRY_DATE "
            + "[" + PREFIX_REMARKS + "REMARKS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 123456 "
            + PREFIX_EXPIRY_DATE + "2026-12-31 "
            + PREFIX_BOX + "box-1:2026-12-31 "
            + PREFIX_REMARKS + "2 fishburgers "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        DeliveryAssignmentHashMap.clearAssignments();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Clears all Driver assignments foe every subscriber in existing address book
     * @param model
     */
    private void clearDriverAssignments(Model model) {
        requireNonNull(model);
        List<Person> persons = new ArrayList<>(model.getAddressBook().getPersonList());
        for (Person oldPerson : persons) {
            Person updatedPerson = createPersonWithoutDriver(oldPerson);
            model.setPerson(oldPerson, updatedPerson);
        }
    }

    /**
     * Creates a copy of the input Person without an assigned Driver
     * @param personToCopy
     * @return Person without {@code Driver} assigned
     */
    private Person createPersonWithoutDriver(Person personToCopy) {
        Name nameCopy = personToCopy.getName();
        Phone phoneCopy = personToCopy.getPhone();
        Email emailCopy = personToCopy.getEmail();
        Address addressCopy = personToCopy.getAddress();
        DeliveryStatus statusCopy = personToCopy.getDeliveryStatus();
        Set<Box> boxesCopy = personToCopy.getBoxes();
        Remark remarkCopy = personToCopy.getRemark();
        Set<Tag> tagsCopy = new HashSet<>(personToCopy.getTags()); // have modifiable tags

        // Create new instance with Driver
        Person assignedPerson = new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, statusCopy, tagsCopy);

        return assignedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
