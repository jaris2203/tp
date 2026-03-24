package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditBoxCommand.EditBoxDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

public class EditBoxCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_boxNameSpecified_success() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setBoxName("box-3");

        EditBoxCommand editBoxCommand = new EditBoxCommand(
                targetPerson.getName(), "box-1", descriptor
        );

        Box editedBox = new Box("box-3", new ExpiryDate("2026-12-31"));
        Person editedPerson = createEditedPerson(targetPerson, "box-1", editedBox);

        String expectedMessage = String.format(EditBoxCommand.MESSAGE_EDIT_BOX_SUCCESS, editedBox,
                targetPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(editBoxCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_expiryDateSpecified_success() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setExpiryDate(new ExpiryDate("2026-12-30"));

        EditBoxCommand editBoxCommand = new EditBoxCommand(
                targetPerson.getName(), "box-1", descriptor
        );

        Box editedBox = new Box("box-1", new ExpiryDate("2026-12-30"));
        Person editedPerson = createEditedPerson(targetPerson, "box-1", editedBox);

        String expectedMessage = String.format(EditBoxCommand.MESSAGE_EDIT_BOX_SUCCESS, editedBox,
                targetPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, editedPerson);

        assertCommandSuccess(editBoxCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBoxName_failure() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setBoxName("box-2");

        EditBoxCommand editBoxCommand = new EditBoxCommand(targetPerson.getName(), "box-1", descriptor);

        assertCommandFailure(editBoxCommand, model, EditBoxCommand.MESSAGE_DUPLICATE_BOX);
    }

    @Test
    public void execute_boxNotFound_failure() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setBoxName("box-3");

        EditBoxCommand editBoxCommand = new EditBoxCommand(targetPerson.getName(), "box-5", descriptor);

        assertCommandFailure(editBoxCommand, model, EditBoxCommand.MESSAGE_BOX_NOT_FOUND);
    }

    private static Person createEditedPerson(Person personToEdit, String oldBoxName, Box editedBox) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Remark updatedRemark = personToEdit.getRemark();
        ExpiryDate updatedPersonExpiryDate = personToEdit.getExpiryDate();
        DeliveryStatus updatedDeliveryStatus = personToEdit.getDeliveryStatus();
        Set<Tag> updatedTags = personToEdit.getTags();

        Set<Box> updatedBoxes = new HashSet<>();
        for (Box box : personToEdit.getBoxes()) {
            if (!box.boxName.equals(oldBoxName)) {
                updatedBoxes.add(box);
            }
        }
        updatedBoxes.add(editedBox);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedBoxes,
                updatedRemark, updatedPersonExpiryDate, updatedDeliveryStatus, updatedTags);
    }
}
