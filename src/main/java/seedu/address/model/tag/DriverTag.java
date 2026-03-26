package seedu.address.model.tag;

/**
 * A specific type of {@code Tag} that allows us to differentiate between normal tags
 */
public class DriverTag extends Tag {

    /**
     * Takes in a String and forms a tag that is also of type {@code DriverTag}
     * @param tag
     */
    public DriverTag(String tag) {
        super("DRIVER: " + tag);
    }

}
