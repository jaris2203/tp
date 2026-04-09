package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.Driver;

/**
 * Parses input arguments and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    private static final String DRIVER_NAME_REGEX = "n/[^/]+";
    private static final String DRIVER_PHONE_REGEX = "p/\\S+";
    private static final String ASSIGN_ARGS_REGEX = "\\s*" + DRIVER_NAME_REGEX + "\\s+" + DRIVER_PHONE_REGEX
            + "(\\s+" + DRIVER_NAME_REGEX + "\\s+" + DRIVER_PHONE_REGEX + ")*\\s*";

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AssignCommand parse(String args) throws ParseException {
        validateRawArguments(args);

        ArgumentMultimap argMultimap = tokenize(args);
        validateTokenizedArguments(argMultimap);

        List<Driver> drivers = parseDrivers(argMultimap);
        return buildAssignCommand(drivers);
    }

    private void validateRawArguments(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty() || !trimmedArgs.matches(ASSIGN_ARGS_REGEX)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }
    }

    private ArgumentMultimap tokenize(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);
    }

    private void validateTokenizedArguments(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE);

        if (names.size() != phones.size()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }
    }

    private List<Driver> parseDrivers(ArgumentMultimap argMultimap) throws ParseException {
        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        List<String> phones = argMultimap.getAllValues(PREFIX_PHONE);

        assert names.size() == phones.size() : "Names and phones should be paired after validation";

        List<Driver> drivers = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            drivers.add(parseDriver(names.get(i), phones.get(i)));
        }

        return drivers;
    }

    private Driver parseDriver(String nameValue, String phoneValue) throws ParseException {
        Name name = ParserUtil.parseName(nameValue);
        Phone phone = ParserUtil.parsePhone(phoneValue);
        return new Driver(name, phone);
    }

    private AssignCommand buildAssignCommand(List<Driver> drivers) throws ParseException {
        try {
            return new AssignCommand(drivers.toArray(new Driver[0]));
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
