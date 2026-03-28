package seedu.address.model.delivery;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.person.Person;

/**
 * Utility class for exporting delivery assignments as HTML.
 */
public class ExportUtil {

    /**
     * Exports delivery assignments to a nicely formatted HTML file.
     * <p>
     * @param assignments the map of drivers to assigned persons
     * @param filePath the file path to write the output to
     * @throws IOException if file writing fails
     */
    public static void exportAssignmentsAsHtml(DeliveryAssignmentHashMap assignments, String filePath)
            throws IOException {

        String htmlContent = generateHtml(assignments);
        writeToFile(filePath, htmlContent);
    }

    private static String generateHtml(DeliveryAssignmentHashMap assignments) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n")
                .append("<title>Delivery Assignments</title>\n")
                .append("<style>\n").append(loadCss()).append("\n</style>\n")
                .append("</head>\n<body>\n");

        for (Driver driver : assignments.getDriversKeySet()) {
            html.append(generateDriverTable(driver, assignments.getDeliveryListFor(driver)));
        }

        html.append("</body>\n</html>");
        return html.toString();
    }

    private static String loadCss() throws IOException {
        try (var cssStream = ExportCommand.class.getResourceAsStream("/styles/styles.css")) {
            if (cssStream == null) {
                throw new IOException("Could not find styles.css in resources/styles/");
            }
            return new String(cssStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static String generateDriverTable(Driver driver, List<Person> persons) {
        StringBuilder table = new StringBuilder();
        table.append("<table>\n")
                .append("<caption>Driver: ").append(driver.getName().toString().toUpperCase())
                .append(" - ").append(driver.getPhone()).append("</caption>\n")
                .append("<thead>\n<tr>\n")
                .append("<th>Name</th><th>Phone</th><th>Email</th><th>Address</th><th>Boxes</th>\n")
                .append("</tr>\n</thead>\n<tbody>\n");

        for (Person p : persons) {
            table.append("<tr>\n")
                    .append("<td>").append(p.getName()).append("</td>")
                    .append("<td>").append(p.getPhone()).append("</td>")
                    .append("<td>").append(p.getEmail()).append("</td>")
                    .append("<td>").append(p.getAddress()).append("</td>")
                    .append("<td>").append(p.getBoxes()).append("</td>\n")
                    .append("</tr>\n");
        }

        table.append("</tbody>\n</table>\n<br>\n");
        return table.toString();
    }

    private static void writeToFile(String filePath, String content) throws IOException {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(filePath);
        java.nio.file.Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
    }
}
