package seedu.address.logic.commands.util;

import seedu.address.model.commons.name.Name;
import seedu.address.model.delivery.Driver;
import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CommandPersonUtil {

    public static Optional<Person> findPersonByName(List<Person> persons, Name name) {
        return persons.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst();
    }

    public static Person withBoxes(Person person, Set<Box> updatedBoxes) {
        assert person != null;
        assert updatedBoxes != null;

        Driver driver = person.hasDriver() ? person.getAssignedDriver() : null;
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), updatedBoxes,
                person.getRemark(), person.getDeliveryStatus(), person.getTags(), driver);
    }
}
