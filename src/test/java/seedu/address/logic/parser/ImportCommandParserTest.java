package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validCsvFileName_returnsImportCommandInstance() throws Exception {
        ImportCommand cmd = parser.parse(" test.csv");
        assertTrue(cmd instanceof ImportCommand);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, "File name cannot be empty.", () -> parser.parse(""));
    }

    @Test
    public void parse_nonCsvExtension_throwsParseException() {
        String msg = "Invalid file type. Only .csv files are allowed for import.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" test.txt"));
    }

    @Test
    public void parse_pathWithSeparator_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" subdir/test.csv"));
    }

    @Test
    public void parse_dotDot_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" ../test.csv"));
    }

    @Test
    public void parse_windowsPathSeparator_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" subdir\\test.csv"));
    }
}
