package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.AddBoxCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteBoxCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditBoxCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

/**
 * Computes inline command assistance for the command box.
 */
public class CommandAssistant {

    private static final Map<String, AssistanceProvider> ASSISTANCE_BY_COMMAND = createAssistanceByCommand();

    /**
     * Returns the ghost-text suffix that should be shown for the current user input.
     */
    public String getSuggestion(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            return "";
        }

        String trimmedLeading = userInput.stripLeading();
        int firstWhitespace = indexOfWhitespace(trimmedLeading);
        if (firstWhitespace == -1) {
            return suggestFromCommandFragment(trimmedLeading.toLowerCase());
        }

        String commandWord = trimmedLeading.substring(0, firstWhitespace).toLowerCase();
        AssistanceProvider provider = ASSISTANCE_BY_COMMAND.get(commandWord);
        if (provider == null) {
            return "";
        }

        String arguments = trimmedLeading.substring(firstWhitespace);
        return provider.suggest(arguments);
    }

    private String suggestFromCommandFragment(String fragment) {
        AssistanceProvider exactMatch = ASSISTANCE_BY_COMMAND.get(fragment);
        if (exactMatch != null) {
            return exactMatch.suggest("");
        }

        List<String> matches = ASSISTANCE_BY_COMMAND.keySet().stream()
                .filter(commandWord -> commandWord.startsWith(fragment))
                .toList();

        if (matches.size() != 1) {
            return "";
        }

        String commandWord = matches.get(0);
        return commandWord.substring(fragment.length()) + ASSISTANCE_BY_COMMAND.get(commandWord).suggest("");
    }

    private static int indexOfWhitespace(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (Character.isWhitespace(input.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private static Map<String, AssistanceProvider> createAssistanceByCommand() {
        Map<String, AssistanceProvider> assistanceByCommand = new LinkedHashMap<>();
        assistanceByCommand.put(AddCommand.COMMAND_WORD, CommandAssistant::suggestForAdd);
        assistanceByCommand.put(AddBoxCommand.COMMAND_WORD, CommandAssistant::suggestForAddBox);
        assistanceByCommand.put(AssignCommand.COMMAND_WORD, CommandAssistant::suggestForAssign);
        assistanceByCommand.put(ClearCommand.COMMAND_WORD, arguments -> "");
        assistanceByCommand.put(DeleteCommand.COMMAND_WORD, CommandAssistant::suggestForDelete);
        assistanceByCommand.put(DeleteBoxCommand.COMMAND_WORD, CommandAssistant::suggestForDeleteBox);
        assistanceByCommand.put(EditCommand.COMMAND_WORD, CommandAssistant::suggestForEdit);
        assistanceByCommand.put(EditBoxCommand.COMMAND_WORD, CommandAssistant::suggestForEditBox);
        assistanceByCommand.put(ExitCommand.COMMAND_WORD, arguments -> "");
        assistanceByCommand.put(ExportCommand.COMMAND_WORD, CommandAssistant::suggestForExport);
        assistanceByCommand.put(FilterCommand.COMMAND_WORD, CommandAssistant::suggestForFilter);
        assistanceByCommand.put(FindCommand.COMMAND_WORD, CommandAssistant::suggestForFind);
        assistanceByCommand.put(HelpCommand.COMMAND_WORD, arguments -> "");
        assistanceByCommand.put(ImportCommand.COMMAND_WORD, CommandAssistant::suggestForImport);
        assistanceByCommand.put(ListCommand.COMMAND_WORD, arguments -> "");
        assistanceByCommand.put(MarkCommand.COMMAND_WORD, CommandAssistant::suggestForMark);
        assistanceByCommand.put(RemarkCommand.COMMAND_WORD, CommandAssistant::suggestForRemark);
        return assistanceByCommand;
    }

    private static String suggestForAdd(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_BOX, PREFIX_REMARKS, PREFIX_TAG);

        StringBuilder suggestion = new StringBuilder();
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NAME, " n/NAME");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_PHONE, " p/PHONE");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_EMAIL, " e/EMAIL");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_ADDRESS, " a/ADDRESS");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_BOX, " b/BOX_NAME:EXPIRY_DATE");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_REMARKS, " [r/REMARKS]");
        suggestion.append(" [t/TAG]...");
        return suggestion.toString();
    }

    private static String suggestForAddBox(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_BOX);

        StringBuilder suggestion = new StringBuilder();
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NAME, " n/NAME");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_BOX, " b/BOX_NAME:EXPIRY_DATE");
        if (hasPrefix(argMultimap, PREFIX_BOX)) {
            suggestion.append(" [b/BOX_NAME:EXPIRY_DATE]...");
        }
        return suggestion.toString();
    }

    private static String suggestForAssign(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_PHONE);
        int nameCount = argMultimap.getAllValues(PREFIX_NAME).size();
        int phoneCount = argMultimap.getAllValues(PREFIX_PHONE).size();

        StringBuilder suggestion = new StringBuilder();
        if (nameCount == 0 && phoneCount == 0) {
            return " n/NAME p/PHONE [n/NAME p/PHONE]...";
        }
        if (nameCount <= phoneCount) {
            suggestion.append(" n/NAME");
        }
        if (phoneCount < nameCount) {
            suggestion.append(" p/PHONE");
        }
        suggestion.append(" [n/NAME p/PHONE]...");
        return suggestion.toString();
    }

    private static String suggestForDelete(String arguments) {
        return arguments.trim().isEmpty() ? " INDEX" : "";
    }

    private static String suggestForDeleteBox(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_BOX);

        StringBuilder suggestion = new StringBuilder();
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NAME, " n/NAME");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_BOX, " b/BOX_NAME");
        if (hasPrefix(argMultimap, PREFIX_BOX)) {
            suggestion.append(" [b/BOX_NAME]...");
        }
        return suggestion.toString();
    }

    private static String suggestForEdit(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_REMARKS, PREFIX_TAG);

        StringBuilder suggestion = new StringBuilder();
        if (argMultimap.getPreamble().isEmpty()) {
            suggestion.append(" INDEX");
        }
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NAME, " [n/NAME]");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_PHONE, " [p/PHONE]");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_EMAIL, " [e/EMAIL]");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_ADDRESS, " [a/ADDRESS]");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_REMARKS, " [r/REMARKS]");
        suggestion.append(" [t/TAG]...");
        return suggestion.toString();
    }

    private static String suggestForEditBox(String arguments) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_BOX, PREFIX_NEW_BOX,
                PREFIX_EXPIRY_DATE);

        StringBuilder suggestion = new StringBuilder();
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NAME, " n/NAME");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_BOX, " b/OLD_BOX_NAME");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_NEW_BOX, " [nb/NEW_BOX_NAME]");
        appendMissingPrefix(suggestion, argMultimap, PREFIX_EXPIRY_DATE, " [ex/EXPIRY_DATE]");
        return suggestion.toString();
    }

    private static String suggestForExport(String arguments) {
        return arguments.trim().isEmpty() ? " [FILE_NAME.html]" : "";
    }

    private static String suggestForFilter(String arguments) {
        if (arguments.trim().isEmpty()) {
            return " BOX_NAME [MORE_BOX_NAMES]... or d/DRIVER_NAME [d/MORE_DRIVER_NAMES]...";
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_DRIVER);
        if (hasPrefix(argMultimap, PREFIX_DRIVER)) {
            return " [d/MORE_DRIVER_NAMES]...";
        }
        return " [MORE_BOX_NAMES]...";
    }

    private static String suggestForFind(String arguments) {
        return arguments.trim().isEmpty() ? " KEYWORD [MORE_KEYWORDS]..." : "";
    }

    private static String suggestForImport(String arguments) {
        return arguments.trim().isEmpty() ? " FILE_NAME.csv" : "";
    }

    private static String suggestForMark(String arguments) {
        String trimmed = arguments.trim();
        if (trimmed.isEmpty()) {
            return " INDEX STATUS";
        }
        return trimmed.contains(" ") ? "" : " STATUS";
    }

    private static String suggestForRemark(String arguments) {
        String trimmed = arguments.trim();
        if (trimmed.isEmpty()) {
            return " INDEX REMARK";
        }
        return trimmed.contains(" ") ? "" : " REMARK";
    }

    private static void appendMissingPrefix(StringBuilder suggestion, ArgumentMultimap argMultimap,
                                            Prefix prefix, String displayText) {
        if (!hasPrefix(argMultimap, prefix)) {
            suggestion.append(displayText);
        }
    }

    private static boolean hasPrefix(ArgumentMultimap argMultimap, Prefix prefix) {
        List<String> values = argMultimap.getAllValues(prefix);
        return !values.isEmpty();
    }

    @FunctionalInterface
    private interface AssistanceProvider {
        String suggest(String arguments);
    }
}
