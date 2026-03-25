package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BOX_DESC_BOX1;
import static seedu.address.logic.commands.CommandTestUtil.BOX_DESC_BOX2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteBoxCommand;
import seedu.address.model.person.Name;

public class DeleteBoxCommandParserTest {

    private DeleteBoxCommandParser parser = new DeleteBoxCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        Name targetName = new Name("Amy Bee");
        Set<String> targetBoxNames = Set.of("box-1");
        String userInput = PREAMBLE_WHITESPACE + NAME_DESC_AMY + BOX_DESC_BOX1;
        DeleteBoxCommand expectedCommand = new DeleteBoxCommand(targetName, targetBoxNames);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleBoxes_success() {
        Name targetName = new Name("Amy Bee");
        Set<String> targetBoxNames = Set.of("box-1", "box-2");
        String userInput = PREAMBLE_WHITESPACE + NAME_DESC_AMY + BOX_DESC_BOX1 + BOX_DESC_BOX2;
        DeleteBoxCommand expectedCommand = new DeleteBoxCommand(targetName, targetBoxNames);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingName_failure() {
        String userInput = PREAMBLE_WHITESPACE + BOX_DESC_BOX1;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBoxCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingBox_failure() {
        String userInput = PREAMBLE_WHITESPACE + NAME_DESC_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBoxCommand.MESSAGE_USAGE));
    }
}
