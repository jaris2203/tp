package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.driver.Driver;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_DUPLICATE_DRIVER = "There are duplicate drivers!";
    public static final String MESSAGE_SUCCESS = "Drivers added and assigned successfully!";
    public static final String MESSAGE_FAIL = "Assignment of drivers failed!";
    
    private Driver[] drivers;

    /**
     * Creates an AssignCommand to tag all {@code Person}s to a {@code Driver}
     */
    public AssignCommand(Driver... inputDrivers) throws CommandException {
        for (int i = 0; i < inputDrivers.length; i++) {
            Driver toAdd = inputDrivers[i];
            if (inputHasDuplicate(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_DRIVER);
            } else {
                drivers[i] = toAdd;
            }

        }
    }

    private boolean inputHasDuplicate(Driver toAdd) {
        for (Driver d : drivers) {
            if (d.equals(toAdd)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        // TODO: integrate method that returns sorted subscribers
        List<List<Person>> sortedSubscribers = new ArrayList<List<Person>>();
        
        if (sortedSubscribers.size() != drivers.length) {
            // Algorithm wrong
            return new CommandResult(MESSAGE_FAIL);
        }
        for (int i = 0; i < sortedSubscribers.size(); i++) {
            Driver assignedDriver = drivers[i];
            for (Person personInSameCluster : sortedSubscribers.get(i)) {
                Person assignedPerson = assignDriver(personInSameCluster, assignedDriver);
                model.setPerson(personInSameCluster, assignedPerson);
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);

    }

    private Person assignDriver(Person personToAssign, Driver assignedDriver) {
        Name nameCopy = personToAssign.getName();
        Phone phoneCopy = personToAssign.getPhone();
        Email emailCopy = personToAssign.getEmail();
        Address addressCopy = personToAssign.getAddress();
        DeliveryStatus statusCopy = personToAssign.getDeliveryStatus();
        Set<Box> boxesCopy = personToAssign.getBoxes();
        Remark remarkCopy = personToAssign.getRemark();
        Set<Tag> tagsCopy = personToAssign.getTags();
        ExpiryDate expiryCopy = personToAssign.getExpiryDate();
        Tag driverTag = new Tag(driver.getName() + ":" + driver.getNumber());

        // Add driverTag to tags
        // TODO: Possibly have a specific UI to differentiate driver tags
        tagsCopy.add(driverTag);

        return new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, expiryCopy,
                statusCopy, tagsCopy);
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
