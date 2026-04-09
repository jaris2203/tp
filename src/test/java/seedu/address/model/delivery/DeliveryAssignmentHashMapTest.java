package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.exceptions.DriverNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeliveryAssignmentHashMapTest {

    private static final Driver DRIVER_A = new Driver(new Name("Alice Driver"), new Phone("91111111"));
    private static final Driver DRIVER_B = new Driver(new Name("Bob Driver"), new Phone("92222222"));

    @BeforeEach
    public void setUp() {
        DeliveryAssignmentHashMap.clearAssignments();
    }

    @Test
    public void getInstance_returnsSameInstance() {
        DeliveryAssignmentHashMap first = DeliveryAssignmentHashMap.getInstance();
        DeliveryAssignmentHashMap second = DeliveryAssignmentHashMap.getInstance();
        assertSame(first, second);
    }

    @Test
    public void assign_newDriver_createsNewList() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();

        map.assign(DRIVER_A, person);

        List<Person> list = map.getDeliveryListFor(DRIVER_A);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertTrue(list.contains(person));
    }

    @Test
    public void assign_existingDriver_appendsToList() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person p1 = new PersonBuilder().withName("Alice Test").withEmail("alice@test.com").build();
        Person p2 = new PersonBuilder().withName("Bob Test").withEmail("bob@test.com").build();

        map.assign(DRIVER_A, p1);
        map.assign(DRIVER_A, p2);

        List<Person> list = map.getDeliveryListFor(DRIVER_A);
        assertEquals(2, list.size());
        assertTrue(list.contains(p1));
        assertTrue(list.contains(p2));
    }

    @Test
    public void assign_duplicatePerson_notAddedTwice() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();

        map.assign(DRIVER_A, person);
        map.assign(DRIVER_A, person); // add same person again

        List<Person> list = map.getDeliveryListFor(DRIVER_A);
        assertEquals(1, list.size());
    }

    @Test
    public void getDeliveryListFor_existingDriver_returnsList() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();
        map.assign(DRIVER_A, person);

        List<Person> list = map.getDeliveryListFor(DRIVER_A);
        assertEquals(1, list.size());
    }

    @Test
    public void getDeliveryListFor_unknownDriver_throwsDriverNotFoundException() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        assertThrows(DriverNotFoundException.class, () -> map.getDeliveryListFor(DRIVER_A));
    }

    @Test
    public void getDriverForPerson_assignedPerson_returnsDriver() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();
        map.assign(DRIVER_A, person);

        Driver result = map.getDriverForPerson(person);
        assertEquals(DRIVER_A, result);
    }

    @Test
    public void getDriverForPerson_unassignedPerson_throwsDriverNotFoundException() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();

        assertThrows(DriverNotFoundException.class, () -> map.getDriverForPerson(person));
    }

    @Test
    public void clearAssignments_removesAll() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();
        map.assign(DRIVER_A, person);

        DeliveryAssignmentHashMap.clearAssignments();

        assertFalse(DeliveryAssignmentHashMap.isExportable());
    }

    @Test
    public void isExportable_emptyMap_returnsFalse() {
        assertFalse(DeliveryAssignmentHashMap.isExportable());
    }

    @Test
    public void isExportable_nonEmptyMap_returnsTrue() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person person = new PersonBuilder().build();
        map.assign(DRIVER_A, person);

        assertTrue(DeliveryAssignmentHashMap.isExportable());
    }

    @Test
    public void getDriversKeySet_containsAssignedDrivers() {
        DeliveryAssignmentHashMap map = DeliveryAssignmentHashMap.getInstance();
        Person p1 = new PersonBuilder().withName("Alice Test").withEmail("alice@test.com").build();
        Person p2 = new PersonBuilder().withName("Bob Test").withEmail("bob@test.com").build();
        map.assign(DRIVER_A, p1);
        map.assign(DRIVER_B, p2);

        assertTrue(map.getDriversKeySet().contains(DRIVER_A));
        assertTrue(map.getDriversKeySet().contains(DRIVER_B));
        assertEquals(2, map.getDriversKeySet().size());
    }
}
