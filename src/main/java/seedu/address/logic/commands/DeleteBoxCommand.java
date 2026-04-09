package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.ClearDriversUtil;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

/**
 * Deletes one or more boxes identified by their box name from a person
 * identified by their name.
 */
public class DeleteBoxCommand extends Command {

    public static final String COMMAND_WORD = "deletebox";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more boxes from a subscriber identified by their name.\n"
            + "Note: Subscribers will automatically be deleted if they no longer have boxes after this command is "
            + "executed.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_BOX + "BOX_NAME "
            + "[" + PREFIX_BOX + "BOX_NAME...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_BOX + "box-1 "
            + PREFIX_BOX + "box-2";

    public static final String MESSAGE_DELETE_BOXES_SUCCESS = "Deleted %1$s from Person: %2$s";
    public static final String MESSAGE_DELETE_BOXES_AND_PERSON_SUCCESS = MESSAGE_DELETE_BOXES_SUCCESS + " "
            + "Also deleted Person: %2$s as person has no more boxes.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No subscriber with the specified name.";
    public static final String MESSAGE_BOX_NOT_FOUND = "The following box(es) do not exist under %1$s: %2$s";

    private final Name targetName;
    private final Set<String> targetBoxNames;

    /**
     * Creates a DeleteBoxCommand for a given subscriber name and the names of boxes to delete
     * @param targetName of the person to delete boxes from
     * @param targetBoxNames of the boxes to delete
     */
    public DeleteBoxCommand(Name targetName, Set<String> targetBoxNames) {
        requireNonNull(targetName);
        requireNonNull(targetBoxNames);
        this.targetName = targetName;
        this.targetBoxNames = targetBoxNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = getPersonByName(lastShownList, targetName);

        if (isNull(personToEdit)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        if (!getNonExistentBoxNames(personToEdit, targetBoxNames).isEmpty()) {
            throw new CommandException(String.format(MESSAGE_BOX_NOT_FOUND, personToEdit.getName(),
                    getNonExistentBoxNames(personToEdit, targetBoxNames)));
        }

        Set<Box> updatedBoxes = personToEdit.getBoxes().stream()
                .filter(box -> !targetBoxNames.contains(box.boxName))
                .collect(Collectors.toSet());

        // if person has no more boxes upon deletion, the person gets deleted as well
        if (updatedBoxes.isEmpty()) {
            model.deletePerson(personToEdit);
            ClearDriversUtil.clearDriverAssignments(model);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_BOXES_AND_PERSON_SUCCESS, targetBoxNames,
                    personToEdit.getName()));
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(),
                updatedBoxes, personToEdit.getRemark(), personToEdit.getDeliveryStatus(), personToEdit.getTags(),
                personToEdit.hasDriver() ? personToEdit.getAssignedDriver() : null
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_BOXES_SUCCESS, targetBoxNames, editedPerson.getName()));
    }

    public Set<String> getNonExistentBoxNames(Person personToEdit, Set<String> targetBoxNames) {
        Set<String> existingBoxNames = personToEdit.getBoxes().stream()
                .map(box -> box.boxName)
                .collect(Collectors.toSet());

        return targetBoxNames.stream()
                .filter(boxName -> !existingBoxNames.contains(boxName))
                .collect(Collectors.toSet());
    }

    public Person getPersonByName(List<Person> persons, Name name) {
        for (Person person : persons) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteBoxCommand)) {
            return false;
        }

        DeleteBoxCommand otherDeleteBoxCommand = (DeleteBoxCommand) other;
        return Objects.equals(targetName, otherDeleteBoxCommand.targetName)
                && Objects.equals(targetBoxNames, otherDeleteBoxCommand.targetBoxNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .add("targetNameBoxes", targetBoxNames)
                .toString();
    }
}
