package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
<<<<<<< HEAD
                new OrderDescription("2 iced coffees"),
=======
                getBoxSet("box1"),
                new Remark("2 iced coffees"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-31"),
                new DeliveryStatus("delivered"),
                getBoxSet("box-1"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
<<<<<<< HEAD
                new OrderDescription("3 cupcakes"),
=======
                getBoxSet("box1", "box2"),
                new Remark("3 cupcakes"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-30"),
                new DeliveryStatus("pending"),
                getBoxSet("box-1", "box-2"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
<<<<<<< HEAD
                new OrderDescription("1 vanilla cake"),
=======
                getBoxSet("box3"),
                new Remark("1 vanilla cake"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-29"),
                new DeliveryStatus("preparing"),
                getBoxSet("box-3"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
<<<<<<< HEAD
                new OrderDescription("4 chicken pies"),
=======
                getBoxSet("box4"),
                new Remark("4 chicken pies"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-27"),
                new DeliveryStatus("out-for-delivery"),
                getBoxSet("box-4"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
<<<<<<< HEAD
                new OrderDescription("2 baguettes"),
=======
                getBoxSet("box5"),
                new Remark("2 baguettes"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-11"),
                new DeliveryStatus("delivered"),
                getBoxSet("box-5"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
<<<<<<< HEAD
                new OrderDescription("6 donuts"),
=======
                getBoxSet("box6"),
                new Remark("6 donuts"),
>>>>>>> da280918 (Refactor order description)
                new ExpiryDate("2026-12-01"),
                new DeliveryStatus("pending"),
                getBoxSet("box-6"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a box set containing the list of strings given.
     */
    public static Set<Box> getBoxSet(String... strings) {
        return Arrays.stream(strings)
                .map(Box::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
