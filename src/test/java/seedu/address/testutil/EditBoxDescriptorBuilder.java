package seedu.address.testutil;

import seedu.address.logic.commands.EditBoxCommand.EditBoxDescriptor;
import seedu.address.model.person.Box;
import seedu.address.model.person.ExpiryDate;

/**
 * A utility class to help with building EditBoxDescriptor objects.
 */
public class EditBoxDescriptorBuilder {

    private EditBoxDescriptor descriptor;

    public EditBoxDescriptorBuilder() {
        descriptor = new EditBoxDescriptor();
    }

    public EditBoxDescriptorBuilder(EditBoxDescriptor descriptor) {
        this.descriptor = new EditBoxDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBoxDescriptor} with fields containing {@code box}'s details
     * @param box
     */
    public EditBoxDescriptorBuilder(Box box) {
        descriptor = new EditBoxDescriptor();
        descriptor.setBoxName(box.boxName);
        descriptor.setExpiryDate(box.expiryDate);
    }

    /**
     * Sets the {@code boxName} of the {@code EditBoxDescriptor} that we are building.
     */
    public EditBoxDescriptorBuilder withBoxName(String boxName) {
        descriptor.setBoxName(boxName);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditBoxDescriptor} that we are building.
     */
    public EditBoxDescriptorBuilder withExpiryDate(String expiryDate) {
        descriptor.setExpiryDate(new ExpiryDate(expiryDate));
        return this;
    }

    public EditBoxDescriptor build() {
        return descriptor;
    }
}
