package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddBoxCommand;
import seedu.address.model.person.Box;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Name;

public class AddBoxCommandParserTest {

    private AddBoxCommandParser parser = new AddBoxCommandParser();

    @Test
    public void execute_allFieldsPresent_success() {
        String userInput = " n/Amy b/box-1 ex/2026-12-31";
        AddBoxCommand expectedCommand = new AddBoxCommand(new Name("Amy"),
                Set.of(new Box("box-1", new ExpiryDate("2026-12-31"))));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void execute_duplicateName_failure() {
        String userInput = PREAMBLE_WHITESPACE + "n/Amy n/Bob b/box-1 ex/2026-12-31";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBoxCommand.MESSAGE_USAGE));
    }
}
