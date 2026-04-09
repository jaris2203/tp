package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.Driver;

public class AssignCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        DeliveryAssignmentHashMap.clearAssignments();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validSingleDriver_success() throws Exception {
        Driver driver = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand command = new AssignCommand(driver);

        CommandResult result = command.execute(model);
        assertEquals(AssignCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_excessDrivers_showsNote() throws Exception {
        // 8 drivers but only 7 typical persons → 1 excess
        Driver d1 = new Driver(new Name("Driver One"), new Phone("91111111"));
        Driver d2 = new Driver(new Name("Driver Two"), new Phone("92222222"));
        Driver d3 = new Driver(new Name("Driver Three"), new Phone("93333333"));
        Driver d4 = new Driver(new Name("Driver Four"), new Phone("94444444"));
        Driver d5 = new Driver(new Name("Driver Five"), new Phone("95555555"));
        Driver d6 = new Driver(new Name("Driver Six"), new Phone("96666666"));
        Driver d7 = new Driver(new Name("Driver Seven"), new Phone("97777777"));
        Driver d8 = new Driver(new Name("Driver Eight"), new Phone("98888888"));
        AssignCommand command = new AssignCommand(d1, d2, d3, d4, d5, d6, d7, d8);

        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("not utilised"));
    }

    @Test
    public void constructor_duplicateDrivers_throwsCommandException() {
        Driver driver = new Driver(new Name("Kyle"), new Phone("91234567"));
        assertThrows(CommandException.class, AssignCommand.MESSAGE_DUPLICATE_DRIVER, () ->
                new AssignCommand(driver, driver));
    }

    @Test
    public void equals_sameDrivers_returnsTrue() throws Exception {
        Driver d = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand a = new AssignCommand(d);
        AssignCommand b = new AssignCommand(new Driver(new Name("Kyle"), new Phone("91234567")));
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_sameObject_returnsTrue() throws Exception {
        Driver d = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand command = new AssignCommand(d);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentDrivers_returnsFalse() throws Exception {
        Driver d1 = new Driver(new Name("Kyle"), new Phone("91234567"));
        Driver d2 = new Driver(new Name("John"), new Phone("98765432"));
        AssignCommand a = new AssignCommand(d1);
        AssignCommand b = new AssignCommand(d2);
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_null_returnsFalse() throws Exception {
        Driver d = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand command = new AssignCommand(d);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() throws Exception {
        Driver d = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand command = new AssignCommand(d);
        assertFalse(command.equals(1));
    }

    @Test
    public void toStringMethod() throws Exception {
        Driver d = new Driver(new Name("Kyle"), new Phone("91234567"));
        AssignCommand command = new AssignCommand(d);
        assertTrue(command.toString().contains("toAssignDrivers"));
    }
}
