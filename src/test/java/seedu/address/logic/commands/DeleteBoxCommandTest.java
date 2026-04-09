package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commons.name.Name;
import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

public class DeleteBoxCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteSomeBoxes_success() {
        Person personToEdit = BENSON;

        Name targetName = personToEdit.getName();
        Set<String> targetBoxNames = Set.of("box-1");
        DeleteBoxCommand deleteBoxCommand = new DeleteBoxCommand(targetName, targetBoxNames);
        Set<Box> updatedBoxes = personToEdit.getBoxes().stream()
                .filter(box -> !box.boxName.equals("box-1"))
                .collect(Collectors.toSet());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), updatedBoxes, personToEdit.getRemark(), personToEdit.getDeliveryStatus(),
                personToEdit.getTags());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(DeleteBoxCommand.MESSAGE_DELETE_BOXES_SUCCESS, Set.of("box-1"),
                editedPerson.getName());

        assertCommandSuccess(deleteBoxCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLastBoxDeletesPerson_success() {
        Person personToDelete = ALICE;

        Set<String> targetBoxNames = Set.of("box-1");
        DeleteBoxCommand deleteBoxCommand = new DeleteBoxCommand(personToDelete.getName(), targetBoxNames);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(DeleteBoxCommand.MESSAGE_DELETE_BOXES_AND_PERSON_SUCCESS,
                Set.of("box-1"), personToDelete.getName());

        assertCommandSuccess(deleteBoxCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_failure() {
        DeleteBoxCommand deleteBoxCommand = new DeleteBoxCommand(new Name("lennon"), Set.of("box-1"));
        assertCommandFailure(deleteBoxCommand, model, DeleteBoxCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_boxNotFound_failure() {
        Person personToEdit = ALICE;

        DeleteBoxCommand deleteBoxCommand = new DeleteBoxCommand(personToEdit.getName(), Set.of("box-9"));
        String expectedMessage = String.format(DeleteBoxCommand.MESSAGE_BOX_NOT_FOUND, personToEdit.getName(),
                Set.of("box-9"));
        assertCommandFailure(deleteBoxCommand, model, expectedMessage);
    }

    @Test
    public void execute_mixedExistingAndNonExistingBoxes_failure() {
        // box-1 exists for BENSON, box-9 does not — entire command should fail
        Person personToEdit = BENSON;

        DeleteBoxCommand deleteBoxCommand = new DeleteBoxCommand(personToEdit.getName(),
                Set.of("box-1", "box-9"));
        assertCommandFailure(deleteBoxCommand, model, String.format(
                DeleteBoxCommand.MESSAGE_BOX_NOT_FOUND, personToEdit.getName(), Set.of("box-9")));
    }

    @Test
    public void equals() {
        Person personToEdit = ALICE;

        Set<String> targetBoxNames = personToEdit.getBoxes().stream()
                .map(Box::getBoxName)
                .collect(Collectors.toSet());

        DeleteBoxCommand standardCommand = new DeleteBoxCommand(personToEdit.getName(), targetBoxNames);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different targetName -> returns false
        assertFalse(standardCommand.equals(new DeleteBoxCommand(new Name("Bob"), targetBoxNames)));

        // different set of box names -> returns false
        assertFalse(standardCommand.equals(new DeleteBoxCommand(personToEdit.getName(), Set.of("box-2"))));
    }
}
