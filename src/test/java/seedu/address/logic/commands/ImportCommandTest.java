package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    // The ImportCommand always reads from data/ relative to working directory
    private static final String TEST_FILENAME = "testimport_unit.csv";
    private static final String TEST_FILEPATH = "data" + File.separator + TEST_FILENAME;

    @AfterEach
    public void cleanUp() {
        new File(TEST_FILEPATH).delete();
    }

    private Model emptyModel() {
        return new ModelManager(new AddressBook(), new UserPrefs());
    }

    private void writeTestCsv(String content) throws IOException {
        File dataDir = new File("data");
        dataDir.mkdirs();
        Files.writeString(new File(TEST_FILEPATH).toPath(), content);
    }

    // ---- Constructor validation tests ----

    @Test
    public void constructor_validFileName_success() {
        assertDoesNotThrow(() -> new ImportCommand("valid.csv"));
    }

    @Test
    public void constructor_emptyFileName_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ImportCommand(""));
    }

    @Test
    public void constructor_nonCsvExtension_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ImportCommand("test.txt"));
    }

    @Test
    public void constructor_withPathSeparator_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ImportCommand("subdir/test.csv"));
    }

    @Test
    public void constructor_withDotDot_throwsCommandException() {
        assertThrows(CommandException.class, () -> new ImportCommand("../test.csv"));
    }

    // ---- Execute tests ----

    @Test
    public void execute_nonexistentFile_throwsCommandException() throws Exception {
        ImportCommand command = new ImportCommand("nonexistent_unit_test.csv");
        assertThrows(CommandException.class, () -> command.execute(emptyModel()));
    }

    @Test
    public void execute_validCsvFile_addsPersons() throws Exception {
        String csv = "index,name,phone,email,address,box1name,box1months,remark,trailing\n"
                + "0,Alice Test,91234567,alice@example.com,123 Main Road 123456,box-1,12,Some Remark,extra\n"
                + "1,Bob Test,98765432,bob@example.com,456 Second Road 234567,box-2,6,Another Remark,extra\n";
        writeTestCsv(csv);

        ImportCommand command = new ImportCommand(TEST_FILENAME);
        CommandResult result = command.execute(emptyModel());
        assertTrue(result.getFeedbackToUser().startsWith("Imported 2/2"));
    }

    @Test
    public void execute_emptyFile_addsZeroPersons() throws Exception {
        // Only header row — no data rows
        writeTestCsv("index,name,phone,email,address,box1name,box1months,remark,trailing\n");

        ImportCommand command = new ImportCommand(TEST_FILENAME);
        CommandResult result = command.execute(emptyModel());
        assertTrue(result.getFeedbackToUser().startsWith("Imported 0/0"));
    }

    @Test
    public void execute_rowWithBlankBoxName_skipsBlankBox() throws Exception {
        // box1name is blank — box is skipped, person is still imported with empty boxes
        String csv = "index,name,phone,email,address,box1name,box1months,remark,trailing\n"
                + "0,Alice Test,91234567,alice@example.com,123 Main Road 123456,,12,Some Remark,extra\n";
        writeTestCsv(csv);

        ImportCommand command = new ImportCommand(TEST_FILENAME);
        CommandResult result = command.execute(emptyModel());
        // Person with no boxes is valid — imported successfully
        assertTrue(result.getFeedbackToUser().startsWith("Imported 1/1"));
    }

    @Test
    public void execute_rowWithNonIntegerMonth_failsGracefully() throws Exception {
        String csv = "index,name,phone,email,address,box1name,box1months,remark,trailing\n"
                + "0,Alice Test,91234567,alice@example.com,123 Main Road 123456,box-1,abc,Some Remark,extra\n";
        writeTestCsv(csv);

        ImportCommand command = new ImportCommand(TEST_FILENAME);
        CommandResult result = command.execute(emptyModel());
        // Row fails due to NumberFormatException — reported in failed imports, not a crash
        assertTrue(result.getFeedbackToUser().contains("0/1"));
        assertTrue(result.getFeedbackToUser().contains("failed to import"));
    }

    @Test
    public void execute_rowTooShort_skippedSilently() throws Exception {
        // Row has fewer than 9 columns — skipped by ImportUtil, not counted at all
        String csv = "index,name,phone,email,address,box1name,box1months,remark,trailing\n"
                + "0,Alice,91234567\n";
        writeTestCsv(csv);

        ImportCommand command = new ImportCommand(TEST_FILENAME);
        CommandResult result = command.execute(emptyModel());
        // Short row is silently dropped by parseCsv — total is 0, not 1
        assertTrue(result.getFeedbackToUser().startsWith("Imported 0/0"));
    }
}
