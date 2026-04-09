package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Updates the remark of a person using the displayed index from the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the remark of the person identified by "
            + "the index number used in the displayed person list.\n"
            + "Parameters: INDEX REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 prefers morning delivery";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Updated remark for Person: %1$s";
    public static final String MESSAGE_REMARK_RESET_SUCCESS = "Reset remark for Person: %1$s";


    private final Index targetIndex;
    private final Remark remark;

    /**
     * Creates a {@code RemarkCommand} to update the remark of the person at the given index.
     */
    public RemarkCommand(Index targetIndex, Remark remark) {
        this.targetIndex = targetIndex;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person remarkedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBoxes(), remark, personToEdit.getDeliveryStatus(),
                personToEdit.getTags());

        model.setPerson(personToEdit, remarkedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        boolean isReset = remark.value.equals(Remark.DEFAULT_REMARK);
        String messageTemplate = isReset ? MESSAGE_REMARK_RESET_SUCCESS : MESSAGE_REMARK_PERSON_SUCCESS;
        return new CommandResult(String.format(messageTemplate, Messages.format(remarkedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return targetIndex.equals(otherRemarkCommand.targetIndex)
                && remark.equals(otherRemarkCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("remark", remark)
                .toString();
    }
}
