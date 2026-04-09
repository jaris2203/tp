package seedu.address.logic.commands.util;

import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Utility class to clear all drivers assigned
 */
public class ClearDriversUtil {

    private ClearDriversUtil(){}

    /**
     * Clears all Driver assignments foe every subscriber in existing address book
     * @param model
     */
    public static void clearDriverAssignments(Model model) {
        requireNonNull(model);
        List<Person> persons = new ArrayList<>(model.getAddressBook().getPersonList());
        for (Person oldPerson : persons) {
            Person updatedPerson = createPersonWithoutDriver(oldPerson);
            model.setPerson(oldPerson, updatedPerson);
        }
    }

    /**
     * Creates a copy of the input Person without an assigned Driver
     * @param personToCopy
     * @return Person without {@code Driver} assigned
     */
    private static Person createPersonWithoutDriver(Person personToCopy) {
        Name nameCopy = personToCopy.getName();
        Phone phoneCopy = personToCopy.getPhone();
        Email emailCopy = personToCopy.getEmail();
        Address addressCopy = personToCopy.getAddress();
        DeliveryStatus statusCopy = personToCopy.getDeliveryStatus();
        Set<Box> boxesCopy = personToCopy.getBoxes();
        Remark remarkCopy = personToCopy.getRemark();
        Set<Tag> tagsCopy = new HashSet<>(personToCopy.getTags()); // have modifiable tags

        // Create new instance with Driver
        Person assignedPerson = new Person(nameCopy, phoneCopy, emailCopy, addressCopy,
                boxesCopy, remarkCopy, statusCopy, tagsCopy);

        return assignedPerson;
    }
}
