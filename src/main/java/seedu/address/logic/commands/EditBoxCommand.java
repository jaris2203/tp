package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_BOX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.delivery.Driver;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of a box of an existing subscriber in the address book.
 */
public class EditBoxCommand extends Command {

    public static final String COMMAND_WORD = "editbox";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of a box of the person identified "
            + "by the name used in the displayed person list. "
            + "Box is specified by the box name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_BOX + "OLD_BOX_NAME "
            + "[" + PREFIX_NEW_BOX + "NEW_BOX_NAME] "
            + "[" + PREFIX_EXPIRY_DATE + "MONTHS_SUBSCRIBED]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_BOX + "box-1 "
            + PREFIX_NEW_BOX + "box-2"
            + PREFIX_EXPIRY_DATE + "3";

    public static final String MESSAGE_EDIT_BOX_SUCCESS = "Edited box %1$s of Person: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOX = "This box already exists for the subscriber.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No subscriber with the given name was found.";
    public static final String MESSAGE_BOX_NOT_FOUND = "This box does not exist for this subscriber.";


    private final Name subscriberName;
    private final String boxName;
    private final EditBoxDescriptor editBoxDescriptor;

    /**
     * @param subscriberName of the subscriber in the filtered person list to edit
     * @param boxName of the box of the subscriber specified
     * @param editBoxDescriptor details of the new box to edit the subscriber's box with
     */
    public EditBoxCommand(Name subscriberName, String boxName, EditBoxDescriptor editBoxDescriptor) {
        requireNonNull(subscriberName);
        requireNonNull(boxName);
        requireNonNull(editBoxDescriptor);

        this.subscriberName = subscriberName;
        this.boxName = boxName;
        this.editBoxDescriptor = new EditBoxDescriptor(editBoxDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = null;
        for (Person person : lastShownList) {
            if (person.getName().equals(this.subscriberName)) {
                personToEdit = person;
                break;
            }
        }

        if (personToEdit == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Box boxToEdit = null;
        for (Box box : personToEdit.getBoxes()) {
            if (box.boxName.equals(this.boxName)) {
                boxToEdit = box;
                break;
            }
        }
        if (boxToEdit == null) {
            throw new CommandException(MESSAGE_BOX_NOT_FOUND);
        }

        Box editedBox = createEditedBox(boxToEdit, editBoxDescriptor);
        Person editedPerson = createEditedPerson(personToEdit, boxToEdit, editedBox);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_BOX_SUCCESS, editedBox, subscriberName));
    }

    /**
     * Creates and returns a {@code Box} with the details of {@code boxToEdit}
     * edited with {@code editBoxDescriptor}
     */
    private static Box createEditedBox(Box boxToEdit, EditBoxDescriptor editBoxDescriptor) {
        assert boxToEdit != null;

        String updatedBoxName = editBoxDescriptor.getBoxName().orElse(boxToEdit.boxName);
        ExpiryDate updatedExpiryDate = editBoxDescriptor.getExpiryDate().orElse(boxToEdit.expiryDate);

        return new Box(updatedBoxName, updatedExpiryDate);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} and the
     * updated box {@code editedBox}
     */
    private static Person createEditedPerson(Person personToEdit, Box boxToReplace, Box editedBox)
            throws CommandException {
        assert personToEdit != null;
        assert boxToReplace != null;
        assert editedBox != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Remark remark = personToEdit.getRemark();
        DeliveryStatus deliveryStatus = personToEdit.getDeliveryStatus();
        Set<Tag> tags = personToEdit.getTags();
        Driver driver = personToEdit.hasDriver() ? personToEdit.getAssignedDriver() : null;

        Set<Box> updatedBoxes = new TreeSet<>(personToEdit.getBoxes());
        updatedBoxes.remove(boxToReplace);

        boolean duplicateBoxName = updatedBoxes.stream()
                .anyMatch(box -> box.boxName.equals(editedBox.boxName));
        if (duplicateBoxName) {
            throw new CommandException(MESSAGE_DUPLICATE_BOX);
        }

        updatedBoxes.add(editedBox);
        return new Person(name, phone, email, address, updatedBoxes, remark, deliveryStatus, tags, driver);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBoxCommand)) {
            return false;
        }

        EditBoxCommand otherEditBoxCommand = (EditBoxCommand) other;
        return subscriberName.equals(otherEditBoxCommand.subscriberName)
                && boxName.equals(otherEditBoxCommand.boxName)
                && editBoxDescriptor.equals(otherEditBoxCommand.editBoxDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subscriberName", subscriberName)
                .add("boxName", boxName)
                .add("editBoxDescriptor", editBoxDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the box with. Each non-empty field value will replace the corresponding
     * field value of the specified box under the specified subscriber.
     */
    public static class EditBoxDescriptor {
        private String boxName;
        private ExpiryDate expiryDate;

        public EditBoxDescriptor() {};

        /**
         * Copy constructor.
         */
        public EditBoxDescriptor(EditBoxDescriptor toCopy) {
            setBoxName(toCopy.boxName);
            setExpiryDate(toCopy.expiryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(boxName, expiryDate);
        }

        public void setBoxName(String boxName) {
            this.boxName = boxName;
        }

        public Optional<String> getBoxName() {
            return Optional.ofNullable(boxName);
        }

        public void setExpiryDate(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBoxDescriptor)) {
                return false;
            }

            EditBoxDescriptor otherEditBoxDescriptor = (EditBoxDescriptor) other;
            return Objects.equals(boxName, otherEditBoxDescriptor.boxName)
                    && Objects.equals(expiryDate, otherEditBoxDescriptor.expiryDate);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("boxName", boxName)
                    .add("expiryDate", expiryDate)
                    .toString();
        }
    }
}
