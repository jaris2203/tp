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
 * Marks a person as "delivered" using it's displayed index from the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the delivery status of the person "
            + "identified by the index number used in the "
            + "displayed person list.\n"
            + "Parameters: INDEX, STATUS (STATUS must be one of the following: 'Pending', 'Packed', 'Delivered')\n"
            + "Example: " + COMMAND_WORD + " 1 Packed";

    public static final String MESSAGE_MARK_PENDING = "Subscription has not been fulfilled for:";
    public static final String MESSAGE_MARK_PACKED = "Package(s) have been packed for:";
    public static final String MESSAGE_MARK_DELIVERED = "Package(s) successfully delivered to:";

    public static final String MESSAGE_ALREADY_MARKED = "Failed to mark: %1$s is already marked as %2$s.";

    public final Index targetIndex;
    public final DeliveryStatus newDeliveryStatus;

    /**
     * Marks a person's delivery status in the address book.
     * <p>
     * The command identifies a person using the index displayed in the most recent
     * person listing and updates their {@link DeliveryStatus} to the specified value.
     * Supported statuses include {@code PENDING}, {@code PACKED}, and {@code DELIVERED}.
     * <p>
     * Example usage:
     * <pre>
     *     {@code mark 1 delivered}   // Marks the first person in the list as DELIVERED
     * </pre>
     * <p>
     * The command returns a {@link CommandResult} containing a feedback message
     * indicating the updated delivery status of the person.
     */
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

        if (personToMark.getDeliveryStatus() == newDeliveryStatus) {
            throw new CommandException(String.format(
                    MESSAGE_ALREADY_MARKED,
                    personToMark.getName(),
                    newDeliveryStatus
            ));
        }

        model.setPerson(personToMark, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String message = switch (newDeliveryStatus) {
        case PENDING -> MESSAGE_MARK_PENDING;
        case PACKED -> MESSAGE_MARK_PACKED;
        case DELIVERED -> MESSAGE_MARK_DELIVERED;
        };

        String formattedDetails = formatPersonDetails(markedPerson);

        return new CommandResult(
                message + "\n\n" + formattedDetails
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

        return new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, newDeliveryStatus, tagsCopy);
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

    private String formatPersonDetails(Person p) {
        StringBuilder sb = new StringBuilder();

        sb.append("Recipient: ").append(p.getName()).append("\n");
        sb.append("Phone: ").append(p.getPhone()).append("\n");
        sb.append("Email: ").append(p.getEmail()).append("\n");
        sb.append("Address: ").append(p.getAddress()).append("\n\n");

        sb.append("Remark:\n").append(p.getRemark()).append("\n\n");

        sb.append("Status: ").append(p.getDeliveryStatus()).append("\n");

        if (p.getTags().isEmpty()) {
            sb.append("Tags: -");
        } else {
            sb.append("Tags: ");
            p.getTags().forEach(tag -> sb.append(tag.tagName).append(" "));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("deliveryStatus", newDeliveryStatus)
                .toString();
    }
}
