package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.DriverAssignedToPersonPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasBoxPredicate;

/**
 * Filters and lists all persons in address book whose boxes match any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Filters the list of persons based on the boxes ordered or drivers assigned.\n"
            + "Parameters: BOX_NAME [MORE_BOX_NAMES]... or d/DRIVER [d/MORE_DRIVERS]...\n"
            + "Example: " + COMMAND_WORD + " box1 or " + COMMAND_WORD + " d/Alex";

    private final Predicate<Person> predicate;

    private final String predicateLabel;

    /**
     * Creates a filter command using a box predicate.
     */
    public FilterCommand(PersonHasBoxPredicate predicate) {
        this(predicate, "boxPredicate");
    }

    /**
     * Creates a filter command using a driver-assignment predicate.
     */
    public FilterCommand(DriverAssignedToPersonPredicate driverPredicate) {
        this(driverPredicate, "driverPredicate");
    }

    private FilterCommand(Predicate<Person> predicate, String predicateLabel) {
        requireNonNull(predicate);
        requireNonNull(predicateLabel);
        this.predicate = predicate;
        this.predicateLabel = predicateLabel;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add(predicateLabel, predicate)
                .toString();
    }
}
