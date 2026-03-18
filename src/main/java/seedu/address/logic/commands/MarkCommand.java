package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Marks a person as "delivered" using it's displayed index from the address book.
 */
public class MarkCommand extends Command {

    public static final String NOT_IMPLEMENTED_YET = "markDelivered command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the delivery status of the person "
            + "identified by the index number used in the "
            + "last person listing to delivered.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_PENDING = "Subscription has not been fulfilled for: %1$s";
    public static final String MESSAGE_MARK_PACKED = "Package(s) have been packed for: %1$s";
    public static final String MESSAGE_MARK_DELIVERED = "Package(s) successfully delivered to: %1$s";

    public final Index targetIndex;
    public final DeliveryStatus newDeliveryStatus;

    public MarkCommand(Index targetIndex, DeliveryStatus newDeliveryStatus) {
        this.targetIndex = targetIndex;
        this.newDeliveryStatus = newDeliveryStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(targetIndex.getZeroBased());
        Person markedPerson = setStatusOf(personToMark);

        model.setPerson(personToMark, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String MESSAGE = switch (newDeliveryStatus) {
            case PENDING -> MESSAGE_MARK_PENDING;
            case PACKED -> MESSAGE_MARK_PACKED;
            case DELIVERED -> MESSAGE_MARK_DELIVERED;
        };

        return new CommandResult(
                String.format(MESSAGE, Messages.format(markedPerson))
        );
    }

    private Person setStatusOf(Person personToMark) {
        Name nameCopy = personToMark.getName();
        Phone phoneCopy = personToMark.getPhone();
        Email emailCopy = personToMark.getEmail();
        Address addressCopy = personToMark.getAddress();
        Set<Box> boxesCopy = personToMark.getBoxes();
        Remark remarkCopy = personToMark.getRemark();
        Set<Tag> tagsCopy = personToMark.getTags();
        ExpiryDate expiryCopy = personToMark.getExpiryDate();

        return new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, expiryCopy,
                newDeliveryStatus, tagsCopy);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return targetIndex.equals(otherMarkCommand.targetIndex)
                && newDeliveryStatus == otherMarkCommand.newDeliveryStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("deliveryStatus", newDeliveryStatus)
                .toString();
    }


}
