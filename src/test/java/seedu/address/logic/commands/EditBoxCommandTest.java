package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditBoxCommand.EditBoxDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.DateTestUtil;
import seedu.address.testutil.EditBoxDescriptorBuilder;

public class EditBoxCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUpClock() {
        DateTestUtil.useFixedClock();
    }

    @AfterEach
    public void tearDownClock() {
        DateTestUtil.resetClock();
    }

    @Test
    public void execute_boxNameSpecified_success() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setBoxName("box-3");

        EditBoxCommand editBoxCommand = new EditBoxCommand(
                targetPerson.getName(), "box-1", descriptor
        );

        Box editedBox = new Box("box-3", new ExpiryDate("2026-06-30"));
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
    public void execute_bothFieldsSpecified_success() {
        Person targetPerson = BENSON;

        EditBoxDescriptor descriptor = new EditBoxDescriptor();
        descriptor.setBoxName("box-3");
        descriptor.setExpiryDate(new ExpiryDate("2026-12-30"));

        EditBoxCommand editBoxCommand = new EditBoxCommand(
                targetPerson.getName(), "box-1", descriptor
        );

        Box editedBox = new Box("box-3", new ExpiryDate("2026-12-30"));
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

    @Test
    public void equals() {
        final EditBoxDescriptor descriptor = new EditBoxDescriptorBuilder()
                .withBoxName("box-1")
                .withExpiryDate("2026-12-31")
                .build();
        final EditBoxDescriptor differentDescriptor = new EditBoxDescriptorBuilder()
                .withBoxName("box-2")
                .withExpiryDate("2026-12-30")
                .build();
        final EditBoxCommand standardCommand = new EditBoxCommand(new Name("AMY"), "box-1",
                descriptor);

        // same values -> returns true
        EditBoxDescriptor copyDescriptor = new EditBoxDescriptor(descriptor);
        EditBoxCommand commandWithSameValues = new EditBoxCommand(new Name("AMY"), "box-1", copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different subscriberName -> returns false
        assertFalse(standardCommand.equals(new EditBoxCommand(new Name("BOB"), "box-1", copyDescriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBoxCommand(new Name("AMY"), "box-1", differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        Name subscriberName = new Name("AMY");
        EditBoxDescriptor editBoxDescriptor = new EditBoxDescriptor();
        EditBoxCommand editBoxCommand = new EditBoxCommand(subscriberName, "box-1", editBoxDescriptor);
        String expected = EditBoxCommand.class.getCanonicalName() + "{subscriberName=" + subscriberName
                + ", boxName=box-1" + ", editBoxDescriptor=" + editBoxDescriptor + "}";
        assertEquals(expected, editBoxCommand.toString());
    }

    private static Person createEditedPerson(Person personToEdit, String oldBoxName, Box editedBox) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Remark updatedRemark = personToEdit.getRemark();
        DeliveryStatus updatedDeliveryStatus = personToEdit.getDeliveryStatus();
        Set<Tag> updatedTags = personToEdit.getTags();

        Set<Box> updatedBoxes = new TreeSet<>();
        for (Box box : personToEdit.getBoxes()) {
            if (!box.boxName.equals(oldBoxName)) {
                updatedBoxes.add(box);
            }
        }
        updatedBoxes.add(editedBox);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedBoxes,
                updatedRemark, updatedDeliveryStatus, updatedTags);
    }
}
