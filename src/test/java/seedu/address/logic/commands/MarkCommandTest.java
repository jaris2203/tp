package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
public class MarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // create updated person with new delivery status
        Person updatedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getEmail(),
                personToMark.getAddress(),
                personToMark.getBoxes(),
                personToMark.getRemark(),
                DeliveryStatus.DELIVERED,
                personToMark.getTags()
        );

        MarkCommand command = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.DELIVERED);

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_MARK_DELIVERED,
                Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, updatedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkCommand command = new MarkCommand(outOfBoundIndex, DeliveryStatus.DELIVERED);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person updatedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getEmail(),
                personToMark.getAddress(),
                personToMark.getBoxes(),
                personToMark.getRemark(),
                DeliveryStatus.DELIVERED,
                personToMark.getTags()
        );

        MarkCommand command = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.DELIVERED);

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_MARK_DELIVERED,
                Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, updatedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased()
                < model.getAddressBook().getPersonList().size());

        MarkCommand command = new MarkCommand(outOfBoundIndex, DeliveryStatus.DELIVERED);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkCommand firstCommand = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.DELIVERED);
        MarkCommand secondCommand = new MarkCommand(INDEX_SECOND_PERSON, DeliveryStatus.DELIVERED);
        MarkCommand anotherStatusCommand = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.PACKED);

        // same object -> true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> true
        MarkCommand firstCommandCopy = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.DELIVERED);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> false
        assertFalse(firstCommand.equals(1));

        // null -> false
        assertFalse(firstCommand.equals(null));

        // different index -> false
        assertFalse(firstCommand.equals(secondCommand));

        // different delivery status -> false
        assertFalse(firstCommand.equals(anotherStatusCommand));
    }

    @Test
    public void execute_markPending_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person updatedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getEmail(),
                personToMark.getAddress(),
                personToMark.getBoxes(),
                personToMark.getRemark(),
                DeliveryStatus.PENDING,
                personToMark.getTags()
        );

        MarkCommand command = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.PENDING);

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_MARK_PENDING,
                Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, updatedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markPacked_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person updatedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getEmail(),
                personToMark.getAddress(),
                personToMark.getBoxes(),
                personToMark.getRemark(),
                DeliveryStatus.PACKED,
                personToMark.getTags()
        );

        MarkCommand command = new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.PACKED);

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_MARK_PACKED,
                Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToMark, updatedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkCommand command = new MarkCommand(targetIndex, DeliveryStatus.DELIVERED);
        String expected = MarkCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", deliveryStatus=" + DeliveryStatus.DELIVERED + "}";
        assertEquals(expected, command.toString());
    }
}
