package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.model.person.DeliveryStatus;

/**
 * Tests for MarkCommandParser.
 */
public class MarkCommandParserTest {

    private final MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        // Updated to include delivery status
        assertParseSuccess(parser, "1 DELIVERED",
                new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.DELIVERED));

        assertParseSuccess(parser, "1 PACKED",
                new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.PACKED));

        assertParseSuccess(parser, "1 PENDING",
                new MarkCommand(INDEX_FIRST_PERSON, DeliveryStatus.PENDING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid index
        assertParseFailure(parser, "a DELIVERED",
                String.format(ParserUtil.MESSAGE_INVALID_INDEX, MarkCommand.MESSAGE_USAGE));

        // missing delivery status
        assertParseFailure(parser, "1",
                String.format(DeliveryStatus.MESSAGE_CONSTRAINTS, MarkCommand.MESSAGE_USAGE));

        // invalid delivery status
        assertParseFailure(parser, "1 INVALIDSTATUS",
                String.format(DeliveryStatus.MESSAGE_CONSTRAINTS, MarkCommand.MESSAGE_USAGE));
    }
}
