package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
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
 * Deletes a person identified using their email or displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by their email or the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or valid Email address \n"
            + "Example: " + COMMAND_WORD + " 1 OR CS2103@CS.EDU.SG";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final Email targetEmail;

    /**
     * Creates a DeleteCommand for a given index
     * @param targetIndex
     */
    public DeleteCommand(Index targetIndex) {
        this.targetEmail = null;
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a DeleteCommand for a given email
     * @param targetEmail
     */
    public DeleteCommand(Email targetEmail) {
        this.targetIndex = null;
        this.targetEmail = targetEmail;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (isNull(targetEmail) && !isNull(targetIndex)) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            DeliveryAssignmentHashMap.clearAssignments();

            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else if (!isNull(targetEmail) && isNull(targetIndex)) {
            for (Person p : lastShownList) {
                if (p.getEmail().equals(targetEmail)) {
                    model.deletePerson(p);
                    DeliveryAssignmentHashMap.clearAssignments();

                    return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(p)));
                }
            }
            // Cannot find Person with target email
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_EMAIL);
        } else {
            // Code path should not end here, but it is just a safeguard
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                    + " OR " + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_EMAIL);
        }
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
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        if (isNull(targetIndex)) {
            return targetEmail.equals(otherDeleteCommand.targetEmail);
        } else {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
