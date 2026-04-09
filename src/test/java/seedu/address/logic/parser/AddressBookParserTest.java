package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddBoxCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteBoxCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditBoxCommand;
import seedu.address.logic.commands.EditBoxCommand.EditBoxDescriptor;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.Driver;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasBoxPredicate;
import seedu.address.model.person.Remark;
import seedu.address.testutil.DateTestUtil;
import seedu.address.testutil.EditBoxDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @BeforeEach
    public void setUpClock() {
        DateTestUtil.useFixedClock();
        DeliveryAssignmentHashMap.clearAssignments();
    }

    @AfterEach
    public void resetClock() {
        DateTestUtil.resetClock();
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addBox() throws Exception {
        AddBoxCommand command = (AddBoxCommand) parser.parseCommand(AddBoxCommand.COMMAND_WORD + " "
            + "n/Amy b/box-1:2");
        assertEquals(new AddBoxCommand(new Name("Amy"), Set.of(new Box("box-1",
                new ExpiryDate("2026-06-30")))), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteBox() throws Exception {
        DeleteBoxCommand command = (DeleteBoxCommand) parser.parseCommand(
                DeleteBoxCommand.COMMAND_WORD + " " + PREFIX_NAME + "Amy Bee " + PREFIX_BOX + "box-1");
        assertEquals(new DeleteBoxCommand(new Name("Amy Bee"), Set.of("box-1")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editBox() throws Exception {
        Box box = new Box("box-2", new ExpiryDate("2026-07-31"));
        EditBoxDescriptor descriptor = new EditBoxDescriptorBuilder(box).build();
        EditBoxCommand command = (EditBoxCommand) parser.parseCommand(EditBoxCommand.COMMAND_WORD + " "
            + " n/Amy Bee b/box-1 nb/box-2 ex/3");
        assertEquals(new EditBoxCommand(new Name("Amy Bee"), "box-1", descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(
                RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " prefers morning delivery");
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("prefers morning delivery")), command);
    }

    @Test
    public void parseCommand_assign() throws Exception {
        AssignCommand command = (AssignCommand) parser.parseCommand(
                AssignCommand.COMMAND_WORD + " n/John Doe p/91234567");
        assertEquals(new AssignCommand(new Driver(new Name("John Doe"), new Phone("91234567"))), command);
    }

    @Test
    public void parseCommand_export() throws Exception {
        assertTrue(parser.parseCommand(ExportCommand.COMMAND_WORD + " output.html") instanceof ExportCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        assertTrue(parser.parseCommand(ImportCommand.COMMAND_WORD + " test.csv") instanceof ImportCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " box-1");
        assertEquals(new FilterCommand(new PersonHasBoxPredicate(Arrays.asList("box-1"))), command);
    }

    @Test
    public void parseCommand_mark() throws Exception {
        MarkCommand command = (MarkCommand) parser.parseCommand(
                MarkCommand.COMMAND_WORD + " 1 DELIVERED");
        assertEquals(new MarkCommand(Index.fromOneBased(1), DeliveryStatus.DELIVERED), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
