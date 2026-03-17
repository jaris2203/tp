package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

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

    public DeleteCommand(Index targetIndex) {
        this.targetEmail = null;
        this.targetIndex = targetIndex;
    }

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
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else if (!isNull(targetEmail) && isNull(targetIndex)) {
            for (Person p : lastShownList) {
                if (p.getEmail().equals(targetEmail)) {
                    model.deletePerson(p);
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

        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && targetEmail.equals(otherDeleteCommand.targetEmail);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
