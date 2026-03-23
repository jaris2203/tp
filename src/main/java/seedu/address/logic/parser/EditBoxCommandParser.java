package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_BOX;

import java.util.stream.Stream;

import seedu.address.logic.commands.EditBoxCommand;
import seedu.address.logic.commands.EditBoxCommand.EditBoxDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Box;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new EditBoxCommand object
 */
public class EditBoxCommandParser implements Parser<EditBoxCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBoxCommand
     * and returns an EditBoxCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBoxCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_BOX, PREFIX_NEW_BOX, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_BOX) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBoxCommand.MESSAGE_USAGE));
        }

        Name subscriberName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String oldBoxName = argMultimap.getValue(PREFIX_BOX).get().trim();

        if (!Box.isValidBoxName(oldBoxName)) {
            throw new ParseException(Box.MESSAGE_CONSTRAINTS);
        }

        EditBoxDescriptor editBoxDescriptor = new EditBoxDescriptor();

        if (argMultimap.getValue(PREFIX_NEW_BOX).isPresent()) {
            editBoxDescriptor.setBoxName(
                    argMultimap.getValue(PREFIX_NEW_BOX).get().trim()
            );
        }
        if (argMultimap.getValue(PREFIX_EXPIRY_DATE).isPresent()) {
            editBoxDescriptor.setExpiryDate(
                    ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get())
            );
        }

        if (!editBoxDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBoxCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBoxCommand(subscriberName, oldBoxName, editBoxDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
