package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ImportUtil;

/**
 * Imports subscribers from a CSV file and adds them to the model using AddCommand.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Imported %d/%d subscribers successfully.";
    public static final String MESSAGE_FAILURE = "Import failed: %s";

    private final String filePath;

    /**
     * Creates an ImportCommand to import subscribers from the specified CSV file path.
     *
     * @param filePath The path to the CSV file to be imported. Must not be null.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Executes the import command by reading subscriber data from a CSV file and adding valid entries
     * to the model.
     * <p>
     * Each row in the CSV file is parsed into a {@code Person} object. Invalid or duplicate rows
     * are skipped silently. The total number of successfully added subscribers is returned in the result.
     *
     * @param model The model which the imported subscribers will be added to. Must not be null.
     * @return A {@code CommandResult} containing a success message with the number of imported subscribers.
     * @throws CommandException If the CSV file cannot be read or parsed.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<String[]> rows;
        try {
            rows = ImportUtil.parseCsv(filePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }

        int addedCount = 0;
        List<String> failedImports = new ArrayList<>();

        for (String[] row : rows) {
            try {
                Person person = buildPerson(row);

                new AddCommand(person).execute(model);
                addedCount++;
            } catch (Exception e) {
                failedImports.add(formatFailedRow(row, e.getMessage()));
            }
        }

        return new CommandResult(buildResultMessage(addedCount, rows.size(), failedImports));
    }

    private Person buildPerson(String[] row) {
        Name name = new Name(row[1]);
        Phone phone = new Phone(row[2]);
        Email email = new Email(row[3]);
        Address address = new Address(row[4]);

        int remarkIndex = row.length - 2;

        Set<Box> boxes = new TreeSet<>();
        for (int i = 5; i < remarkIndex; i += 2) {
            String boxName = row[i].trim();

            if (boxName.isEmpty()) {
                continue; //skip box if blank (user did not order a 2nd, 3rd etc. bow)
            }

            int subscribedMonths = Integer.parseInt(row[i + 1].trim());
            LocalDate expiry = LocalDate.now()
                    .plusMonths(subscribedMonths)
                    .with(TemporalAdjusters.lastDayOfMonth());
            ExpiryDate expiryDate = new ExpiryDate(expiry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            boxes.add(new Box(boxName, expiryDate));
        }

        String remarkStr = row[remarkIndex].trim();

        Remark remark = remarkStr.isEmpty() ? new Remark(Remark.DEFAULT_REMARK) : new Remark(remarkStr);

        Set<Tag> tags = Set.of();

        return new Person(name, phone, email, address, boxes, remark, tags);
    }

    private String formatFailedRow(String[] row, String reason) {
        String summary = String.format(
                "  - Name: %s | Phone: %s | Email: %s | Address: %s | "
                        + "Boxes: %s | Expiry: %s | Remark: %s\n     Reason: %s\n",
                row.length > 1 ? row[1] : "?", row.length > 2 ? row[2] : "?",
                row.length > 3 ? row[3] : "?", row.length > 4 ? row[4] : "?",
                row.length > 5 ? row[5] : "?", row.length > 6 ? row[6] : "?",
                row.length > 7 ? row[7] : "?", reason);
        return summary;
    }

    private String buildResultMessage(int added, int total, List<String> failed) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(MESSAGE_SUCCESS, added, total));
        if (!failed.isEmpty()) {
            result.append(String.format("\n\n%d row(s) failed to import:\n", failed.size()));
            failed.forEach(msg -> result.append(msg).append("\n"));
        }
        return result.toString().trim();
    }
}
