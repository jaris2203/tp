package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotExportableException;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.util.ExportUtil;
import seedu.address.model.person.Person;

/**
 * Exports the current delivery assignments to a formatted text file.
 * <p>
 * Each driver will appear as a heading, followed by their assigned subscribers.
 * If no file path is provided, the command saves the export to the default file:
 * {@code data/delivery_assignments.txt}.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "File successfully saved to: %s";
    public static final String MESSAGE_FAILURE = "Failed to export delivery assignments.";

    private static final String DEFAULT_DIR = "data";
    private static final String DEFAULT_FILENAME = "delivery_assignments.html";

    private final String filePath;

    private final DeliveryAssignmentHashMap assignments = DeliveryAssignmentHashMap.getInstance();

    /**
     * Creates an ExportCommand with the specified file name.
     * <p>
     * If the provided file name is {@code null} or empty, the default file
     * {@code data/delivery_assignments.html} is used. The command ensures
     * that the {@code data/} directory exists before exporting.
     * <p>
     * Users are only allowed to provide a file name (no directories or paths).
     * All files are saved inside the {@code data/} folder. Providing a path,
     * directories, or {@code ..} in the file name will result in a {@link CommandException}.
     *
     * @param filePath the name of the file to export the delivery assignments to;
     *                 must be a valid HTML file name ending with {@code .html}
     * @throws CommandException if the file name contains path separators, {@code ..},
     *                          or does not end with {@code .html}
     */
    public ExportCommand(String filePath) throws CommandException {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (filePath == null || filePath.isBlank()) {
            this.filePath = "data" + File.separator + DEFAULT_FILENAME;
            return;
        }

        if (filePath.contains("/") || filePath.contains("\\") || filePath.contains("..")) {
            throw new CommandException("Invalid file name. Please provide a file name only, not a path.");
        }

        if (!filePath.toLowerCase().endsWith(".html")) {
            throw new CommandException("Invalid file type. Please provide a file name ending with .html");
        }

        this.filePath = "data" + File.separator + filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            getDriverMapping(model);
            ExportUtil.exportAssignmentsAsHtml(assignments, filePath);
        } catch (NotExportableException e) {
            throw new CommandException(MESSAGE_FAILURE + " " + e.getMessage());
        } catch (IOException e) {
            throw new CommandException("Export failed: " + e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    /**
     * Iterates through all the {@code Person}s in the address book and creates a mapping of the Driver to the Person
     * @param model
     * @throws NotExportableException
     */
    private void getDriverMapping(Model model) throws NotExportableException {
        requireNonNull(model);
        DeliveryAssignmentHashMap.clearAssignments();
        for (Person p : model.getAddressBook().getPersonList()) {
            if (!p.hasDriver()) {
                throw new NotExportableException("Not all subscribers have been assigned a driver!");
            }
            assignments.assign(p.getAssignedDriver(), p);
        }
    }
}
