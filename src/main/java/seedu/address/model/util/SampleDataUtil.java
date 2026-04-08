package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40, Singapore 123456"),
                getBoxSet("box-1:2"),
                new Remark("2 iced coffees"),
                DeliveryStatus.fromString("delivered"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, Singapore 123456, #07-18"),
                    getBoxSet("box-1:2", "box-2:3"),
                new Remark("3 cupcakes"),
                DeliveryStatus.fromString("pending"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04, Singapore 123456"),
                getBoxSet("box-3:4"),
                new Remark("1 vanilla cake"),
                DeliveryStatus.fromString("packed"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43, Singapore 123456"),
                getBoxSet("box-4:5"),
                new Remark("4 chicken pies"),
                DeliveryStatus.fromString("delivered"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35, Singapore 123456"),
                getBoxSet("box-5:6"),
                new Remark("2 baguettes"),
                DeliveryStatus.fromString("delivered"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31, Singapore 123456"),
                getBoxSet("box-6:7"),
                new Remark("6 donuts"),
                DeliveryStatus.fromString("pending"),
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
                .map(boxWithMonths -> {
                    int separatorIndex = boxWithMonths.lastIndexOf(":");
                    if (separatorIndex <= 0 || separatorIndex == boxWithMonths.length() - 1) {
                        throw new IllegalArgumentException(Box.MESSAGE_INVALID_BOX_WITH_EXPIRY_FORMAT);
                    }

                    String boxName = boxWithMonths.substring(0, separatorIndex).trim();
                    String numOfMonthsString = boxWithMonths.substring(separatorIndex + 1).trim();

                    if (!Box.isValidBoxName(boxName)) {
                        throw new IllegalArgumentException(Box.MESSAGE_CONSTRAINTS);
                    }

                    try {
                        return new Box(boxName, ParserUtil.parseExpiryDate(numOfMonthsString));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(ParserUtil.MESSAGE_INVALID_NUM_OF_MONTHS);
                    }
                })
                .collect(Collectors.toCollection(TreeSet::new));
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
