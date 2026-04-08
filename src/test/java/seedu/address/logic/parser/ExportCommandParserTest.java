package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArgs_returnsExportCommandInstance() {
        assertDoesNotThrow(() -> {
            ExportCommand cmd = parser.parse(" ");
            assertTrue(cmd instanceof ExportCommand);
        });
    }

    @Test
    public void parse_validHtmlPath_returnsExportCommandInstance() {
        assertDoesNotThrow(() -> {
            ExportCommand cmd = parser.parse(" output.html");
            assertTrue(cmd instanceof ExportCommand);
        });
    }

    @Test
    public void parse_nonHtmlExtension_throwsParseException() {
        String msg = "Invalid file type. Please provide a file name ending with .html";
        assertThrows(ParseException.class, msg, () -> parser.parse(" output.txt"));
    }

    @Test
    public void parse_pathWithSeparator_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" subdir/output.html"));
    }

    @Test
    public void parse_dotDot_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" ../output.html"));
    }

    @Test
    public void parse_windowsPathSeparator_throwsParseException() {
        String msg = "Invalid file name. Please provide a file name only, not a path.";
        assertThrows(ParseException.class, msg, () -> parser.parse(" subdir\\output.html"));
    }
}
