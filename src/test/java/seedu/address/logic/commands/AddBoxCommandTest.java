package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Box;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class AddBoxCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addOneBox_success() {
        Person personToEdit = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(new Name("Benson Meier")))
                .findFirst()
                .orElseThrow();

        Set<Box> boxesToAdd = Set.of(new Box("box-3", new ExpiryDate("2026-12-31")));
        AddBoxCommand addBoxCommand = new AddBoxCommand(personToEdit.getName(), boxesToAdd);

        Set<Box> updatedBoxes = new TreeSet<>(personToEdit.getBoxes());
        updatedBoxes.addAll(boxesToAdd);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), updatedBoxes, personToEdit.getRemark(), personToEdit.getExpiryDate(),
                personToEdit.getDeliveryStatus(), personToEdit.getTags()
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        String expectedMessage = String.format(
                AddBoxCommand.MESSAGE_SUCCESS,
                boxesToAdd,
                personToEdit.getName()
        );

        assertCommandSuccess(addBoxCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_failure() {
        Set<Box> boxesToAdd = Set.of(new Box("box-1", new ExpiryDate("2026-12-31")));
        AddBoxCommand addBoxCommand = new AddBoxCommand(new Name("John"), boxesToAdd);

        assertCommandFailure(addBoxCommand, model, AddBoxCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_existingBoxNames_failure() {
        Person personToEdit = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(new Name("Benson Meier")))
                .findFirst()
                .orElseThrow();

        String existingBoxName = personToEdit.getBoxes().iterator().next().getBoxName();
        Set<Box> boxesToAdd = Set.of(new Box(existingBoxName, new ExpiryDate("2026-12-31")));

        AddBoxCommand addBoxCommand = new AddBoxCommand(personToEdit.getName(), boxesToAdd);
        String expectedMessage = String.format(AddBoxCommand.MESSAGE_EXISTING_BOX_NAME, personToEdit.getName());
        assertCommandFailure(addBoxCommand, model, expectedMessage);
    }
}
