package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DESCRIPTION_BOB;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for {@code RemarkCommand}.
 */
public class RemarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark remark = new Remark(VALID_ORDER_DESCRIPTION_BOB);
        RemarkCommand command = new RemarkCommand(INDEX_FIRST_PERSON, remark);

        Person remarkedPerson = new PersonBuilder(personToRemark).withRemark(remark.value).build();
        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS,
                Messages.format(remarkedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToRemark, remarkedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand command = new RemarkCommand(outOfBoundIndex, new Remark(VALID_ORDER_DESCRIPTION_BOB));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand command = new RemarkCommand(outOfBoundIndex, new Remark(VALID_ORDER_DESCRIPTION_BOB));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemarkCommand firstCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("first remark"));
        RemarkCommand secondCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark("second remark"));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("first remark"))));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Remark remark = new Remark("prefers morning delivery");
        RemarkCommand command = new RemarkCommand(targetIndex, remark);
        String expected = RemarkCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", remark=" + remark + "}";
        assertEquals(expected, command.toString());
    }
}
