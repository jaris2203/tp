package seedu.address.model.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.model.delivery.exceptions.DriverNotFoundException;
import seedu.address.model.person.Person;

/**
 * Singleton class that stores and manages delivery assignments between {@code Driver}s
 * and {@code Person}s.
 *
 * <p>This class maintains a mapping of each {@code Driver} to a list of {@code Person}s
 * assigned to them. It provides methods to assign persons to drivers, retrieve delivery
 * lists, and clear all existing assignments.</p>
 *
 * <p>This class follows the Singleton pattern and cannot be instantiated directly.</p>
 */
public class DeliveryAssignmentHashMap {

    private static DeliveryAssignmentHashMap instance = null;
    private static Map<Driver, List<Person>> assignments;

    /**
     * Private constructor to prevent external instantiation.
     */
    private DeliveryAssignmentHashMap() {
        assignments = new HashMap<>();
    }

    /**
     * Returns the singleton instance of {@code DeliveryAssignmentHashMap}.
     *
     * @return the singleton instance
     */
    public static DeliveryAssignmentHashMap getInstance() {
        if (instance == null) {
            instance = new DeliveryAssignmentHashMap();
        }
        return instance;
    }

    /**
     * Assigns the specified {@code Person} to the given {@code Driver}.
     *
     * <p>If the driver does not yet have any assigned persons, a new list will be created.
     * The person will then be added to the driver's assignment list.</p>
     *
     * @param d the {@code Driver} to assign the person to
     * @param p the {@code Person} to be assigned
     */
    public void assign(Driver d, Person p) {
        List<Person> list = assignments.computeIfAbsent(d, k -> new ArrayList<>());

        if (!list.contains(p)) {
            list.add(p);
        }
    }

    /**
     * Returns a set of all {@code Driver}s that currently have delivery assignments.
     *
     * <p>The returned set is unmodifiable, so external code cannot add or remove drivers
     * from the internal assignments map. This method is useful for iterating over all
     * drivers without exposing the full map.</p>
     *
     * @return an unmodifiable {@link Set} of all {@code Driver}s with assignments
     */
    public Set<Driver> getDriversKeySet() {
        // Return an unmodifiable view so external code cannot alter the internal map
        return Collections.unmodifiableSet(assignments.keySet());
    }

    /**
     * Returns the list of {@code Person}s assigned to the specified {@code Driver}.
     *
     * <p>If the driver does not have any assigned persons, a {@link DriverNotFoundException}
     * is thrown.</p>
     *
     * @param d the {@code Driver} whose delivery list is to be retrieved
     * @return a list of {@code Person}s assigned to the driver
     * @throws DriverNotFoundException if the driver does not exist in the assignments map
     */
    public List<Person> getDeliveryListFor(Driver d) {
        List<Person> list = assignments.get(d);
        if (list != null) {
            return list;
        } else {
            throw new DriverNotFoundException();
        }
    }

    public static boolean isExportable() {
        return !assignments.isEmpty();
    }

    /**
     * Clears all existing delivery assignments.
     *
     * <p>After calling this method, no drivers will have any assigned persons.</p>
     */
    public static void clearAssignments() {
        assignments = new HashMap<>();
    }
}
