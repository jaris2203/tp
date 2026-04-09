package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;

public class ExportCommandTest {

    private static final String TEST_FILENAME = "testexport_unit.html";
    private static final String TEST_FILEPATH = "data" + File.separator + TEST_FILENAME;

    @BeforeEach
    public void setUp() {
        DeliveryAssignmentHashMap.clearAssignments();
    }

    @AfterEach
    public void cleanUp() {
        new File(TEST_FILEPATH).delete();
    }

    // ---- Constructor validation tests ----

    @Test
    public void constructor_nullPath_usesDefault() {
        assertDoesNotThrow(() -> new ExportCommand(null));
    }

    @Test
    public void constructor_blankPath_usesDefault() {
        assertDoesNotThrow(() -> new ExportCommand(""));
    }

    @Test
    public void constructor_nonHtmlExtension_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ExportCommand("output.txt"));
    }

    @Test
    public void constructor_validHtmlFileName_success() {
        assertDoesNotThrow(() -> new ExportCommand("output.html"));
    }

    @Test
    public void constructor_withPathSeparator_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ExportCommand("subdir/output.html"));
    }

    @Test
    public void constructor_withDotDot_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ExportCommand("../output.html"));
    }

    // ---- Execute tests ----

    @Test
    public void execute_notAllPersonsHaveDriver_throwsCommandException() throws Exception {
        AddressBook ab = new AddressBook();
        ab.addPerson(HOON); // HOON has no driver
        Model model = new ModelManager(ab, new UserPrefs());
        ExportCommand command = new ExportCommand("output.html");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_allPersonsHaveDriver_success() throws Exception {
        // All typical persons already have drivers assigned
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ExportCommand command = new ExportCommand(TEST_FILENAME);

        CommandResult result = command.execute(model);
        // ExportCommand prepends "data/" so result contains "data/testexport_unit.html"
        assertTrue(result.getFeedbackToUser().contains(TEST_FILENAME));
    }
}
