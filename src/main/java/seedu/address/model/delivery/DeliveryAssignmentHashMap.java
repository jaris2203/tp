package seedu.address.model.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.delivery.exceptions.DriverNotFoundException;
import seedu.address.model.person.Person;


/**
 * Stores and manages delivery assignments between {@code Driver}s and {@code Person}s.
 *
 * <p>This class maintains a mapping of each {@code Driver} to a list of {@code Person}s
 * assigned to them. It provides methods to assign persons to drivers and to clear all
 * existing assignments.</p>
 *
 * <p>This class is not meant to be instantiated.</p>
 */
public class DeliveryAssignmentHashMap {

    /**
     * Mapping of drivers to the list of persons assigned to them.
     */
    private static Map<Driver, List<Person>> assignments = new HashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private DeliveryAssignmentHashMap() {}

    /**
     * Returns the current mapping of drivers to their assigned persons.
     *
     * @return a map where each key is a {@code Driver} and the value is a list of
     *     {@code Person}s assigned to that driver
     */
    public static Map<Driver, List<Person>> getAssignments() {
        return assignments;
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
        if (!assignments.containsKey(d)) {
            assignments.put(d, new ArrayList<Person>());
        }
        List<Person> subscriberList = assignments.get(d);
        subscriberList.add(p);
    }

    /**
     * Returns the list of {@code Person}s assigned to the specified {@code Driver}.
     *
     * <p>If the driver does not have any assigned persons, a {@link DriverNotFoundException}
     * is thrown.</p>
     *
     * @param d the {@code Driver} whose delivery list is to be retrieved
     * @return a list of {@code Person}s assigned to the driver
     * @throws DriverNotFoundException if the driver has does not exist in hashmap
     */
    public List<Person> getDeliveryListFor(Driver d) {
        if (assignments.containsKey(d)) {
            return assignments.get(d);
        } else {
            throw new DriverNotFoundException();
        }
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
