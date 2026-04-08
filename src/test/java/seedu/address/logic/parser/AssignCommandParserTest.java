package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.Driver;

public class AssignCommandParserTest {

    private final AssignCommandParser parser = new AssignCommandParser();

    @BeforeEach
    public void setUp() {
        DeliveryAssignmentHashMap.clearAssignments();
    }

    @Test
    public void parse_validSingleDriver_success() throws Exception {
        Driver expected = new Driver(new Name("John Doe"), new Phone("91234567"));
        AssignCommand expectedCommand = new AssignCommand(expected);
        assertParseSuccess(parser, " n/John Doe p/91234567", expectedCommand);
    }

    @Test
    public void parse_validMultipleDrivers_success() throws Exception {
        Driver d1 = new Driver(new Name("John Doe"), new Phone("91234567"));
        Driver d2 = new Driver(new Name("Jane Tan"), new Phone("98765432"));
        AssignCommand expectedCommand = new AssignCommand(d1, d2);
        assertParseSuccess(parser, " n/John Doe p/91234567 n/Jane Tan p/98765432", expectedCommand);
    }

    @Test
    public void parse_missingNamePrefix_throwsParseException() {
        assertParseFailure(parser, " p/91234567",
                String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPhonePrefix_throwsParseException() {
        assertParseFailure(parser, " n/John Doe",
                String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateDrivers_throwsParseException() {
        // Same name and phone twice → AssignCommand constructor throws CommandException,
        // which the parser wraps as ParseException
        assertParseFailure(parser, " n/John Doe p/91234567 n/John Doe p/91234567",
                AssignCommand.MESSAGE_DUPLICATE_DRIVER);
    }
}
