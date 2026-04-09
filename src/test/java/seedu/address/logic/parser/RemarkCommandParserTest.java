package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

/**
 * Tests for RemarkCommandParser.
 */
public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArgs_returnsRemarkCommand() {
        assertParseSuccess(parser, "1 r/prefers morning delivery",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("prefers morning delivery")));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsRemarkCommand() {
        assertParseSuccess(parser, "   1    r/prefers morning delivery   ",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("prefers morning delivery")));
    }

    @Test
    public void parse_blankRemark_returnsRemarkCommandWithDefault() {
        // r/ with no value resets the remark
        assertParseSuccess(parser, "1 r/",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark()));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // No r/ prefix at all
        assertParseFailure(parser, "1 prefers morning delivery",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a r/prefers morning delivery",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
