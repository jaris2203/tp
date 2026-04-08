package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddBoxCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.person.Box;

/**
 * Parses input arguments and creates a new AddBoxCommand object
 */
public class AddBoxCommandParser implements Parser<AddBoxCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBoxCommand
     * and returns an AddBoxCommand object for execution.
     * @throws ParseException if the user does not conform to the expected format
     */
    public AddBoxCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_BOX);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_BOX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBoxCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Box> boxesToAdd = ParserUtil.parseBoxesWithNumOfMonths(argMultimap.getAllValues(PREFIX_BOX));

        return new AddBoxCommand(name, boxesToAdd);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
