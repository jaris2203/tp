package seedu.address.model.delivery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Utility class for exporting delivery assignments to a formatted text file.
 * <p>
 * Provides methods to write all current driver-to-person assignments into
 * a human-readable file, grouping subscribers under each driver.
 */
public class ExportUtil {

    /**
     * Exports delivery assignments to a nicely formatted text file.
     * <p>
     * Each driver is printed as a heading, followed by their assigned subscribers.
     * <p>
     * @param assignments the map of drivers to assigned persons
     * @param filePath the file path to write the output to
     * @throws IOException if file writing fails
     */
    public static void exportAssignmentsFormatted(DeliveryAssignmentHashMap assignments, String filePath)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Driver driver : assignments.getDriversKeySet()) {

                // Driver header
                writer.write(driver.getName().toString() + " - " + driver.getPhone().toString());
                writer.newLine();
                writer.write("--------------------");
                writer.newLine();

                List<Person> people = assignments.getDeliveryListFor(driver);

                for (Person p : people) {
                    String line = String.format("%s | %s | %s | %s | Boxes: %d",
                            p.getName(),
                            p.getPhone(),
                            p.getEmail(),
                            p.getAddress(),
                            p.getBoxes().size());
                    writer.write(line);
                    writer.newLine();
                }

                writer.newLine(); // space between drivers
            }
        }
    }

    /**
     * Exports delivery assignments to a nicely formatted HTML file.
     * <p>
     * Each driver is printed as a heading, followed by their assigned subscribers.
     * <p>
     * @param assignments the map of drivers to assigned persons
     * @param filePath the file path to write the output to
     * @throws IOException if file writing fails
     */
    public static void exportAssignmentshtml(
            DeliveryAssignmentHashMap assignments, String filePath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>Delivery Assignments</title>\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n")
                .append("</head>\n<body>\n");

        // Iterate through each driver
        for (Driver driver : assignments.getDriversKeySet()) {
            html.append("<table>\n")
                    .append("<caption>Driver: ").append(driver.getName().toString().toUpperCase())
                    .append(" - ").append(driver.getPhone().toString()).append("</caption>\n")
                    .append("<thead>\n<tr>\n")
                    .append("<th>Name</th><th>Phone</th><th>Email</th><th>Address</th><th>Boxes</th>\n")
                    .append("</tr>\n</thead>\n<tbody>\n");

            for (Person p : assignments.getDeliveryListFor(driver)) {
                html.append("<tr>\n")
                        .append("<td>").append(p.getName().toString()).append("</td>")
                        .append("<td>").append(p.getPhone().toString()).append("</td>")
                        .append("<td>").append(p.getEmail().toString()).append("</td>")
                        .append("<td>").append(p.getAddress().toString()).append("</td>")
                        .append("<td>").append(p.getBoxes()).append("</td>\n")
                        .append("</tr>\n");
            }

            html.append("</tbody>\n</table>\n<br>\n");
        }

        html.append("</body>\n</html>");

        // Write to file
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(filePath);
        java.nio.file.Files.writeString(file.toPath(), html.toString());
    }
}
