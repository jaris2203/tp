package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.ClusterAssigner;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.Driver;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.DriverTag;
import seedu.address.model.tag.Tag;

/**
 * Assigned declared drivers to existing subscribers in the address book.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_DUPLICATE_DRIVER = "There are duplicate drivers!";
    public static final String MESSAGE_SUCCESS = "Drivers added and assigned successfully!";
    public static final String MESSAGE_FAIL = "Assignment of drivers failed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns drivers to subscriber clusters.\n"
            + "Parameters: n/NAME p/PHONE [n/NAME p/PHONE]...\n"
            + "Example: " + COMMAND_WORD + " n/John Doe p/91234567 n/Jane Tan p/98765432";

    private final Driver[] drivers;
    private final DeliveryAssignmentHashMap assignments = DeliveryAssignmentHashMap.getInstance();

    /**
     * Creates an AssignCommand to tag all {@code Person}s to a {@code Driver}
     */
    public AssignCommand(Driver... inputDrivers) throws CommandException {
        DeliveryAssignmentHashMap.clearAssignments();

        this.drivers = new Driver[inputDrivers.length];

        for (int i = 0; i < inputDrivers.length; i++) {
            Driver toAdd = inputDrivers[i];
            if (inputHasDuplicate(toAdd, i)) {
                throw new CommandException(MESSAGE_DUPLICATE_DRIVER);
            } else {
                drivers[i] = toAdd;
            }

        }
    }

    private boolean inputHasDuplicate(Driver toAdd, int limit) {
        for (int i = 0; i < limit; i++) {
            if (drivers[i].equals(toAdd)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<List<Person>> sortedSubscribers = ClusterAssigner.groupIntoClusters(
                    model.getFilteredPersonList(),
                    drivers.length);

        if (sortedSubscribers.size() != drivers.length) {
            // End here is algorithm is wrong (mapped to wrong no. of drivers)
            return new CommandResult(MESSAGE_FAIL);
        }
        for (int i = 0; i < sortedSubscribers.size(); i++) {
            Driver assignedDriver = drivers[i];
            for (Person personInSameCluster : sortedSubscribers.get(i)) {
                Person assignedPerson = createPersonWithDriver(personInSameCluster, assignedDriver);
                model.setPerson(personInSameCluster, assignedPerson);
                // TODO: Update partitioned list in AddressBook store for export/filter command
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);

    }

    private Person createPersonWithDriver(Person personToAssign, Driver assignedDriver) {
        Name nameCopy = personToAssign.getName();
        Phone phoneCopy = personToAssign.getPhone();
        Email emailCopy = personToAssign.getEmail();
        Address addressCopy = personToAssign.getAddress();
        DeliveryStatus statusCopy = personToAssign.getDeliveryStatus();
        Set<Box> boxesCopy = personToAssign.getBoxes();
        Remark remarkCopy = personToAssign.getRemark();
        Set<Tag> tagsCopy = new HashSet<>(personToAssign.getTags()); // have modifiable tags
        ExpiryDate expiryCopy = personToAssign.getExpiryDate();
        DriverTag driverTag = new DriverTag(assignedDriver.getName() + ":" + assignedDriver.getPhone());

        // Negate prior assignments
        removeExistingDriverTag(tagsCopy);
        // Add driverTag to tags
        tagsCopy.add(driverTag);

        Person assignedPerson = new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, expiryCopy,
                statusCopy, tagsCopy);

        // Keep the assignment store in sync with the updated Person instance in the model.
        assignments.assign(assignedDriver, assignedPerson);

        return assignedPerson;
    }

    /**
     * Finds and removes any {@code DriverTag} in given set of tags
     * @param tags
     */
    private void removeExistingDriverTag(Set<Tag> tags) {
        tags.removeIf(tag -> tag.tagName.contains("DRIVER: "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return Arrays.equals(drivers, otherAssignCommand.drivers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssignDrivers", drivers)
                .toString();
    }
}
