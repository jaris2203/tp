package seedu.address.logic.commands.exceptions;

/**
 * Signals that an export operation cannot be performed because
 * the delivery assignments are not in an exportable state.
 * <p>
 * This exception is thrown when an export command attempts to export
 * delivery assignments without finalizing the assignments
 */
public class NotExportableException extends Exception {

    public NotExportableException() {
        super("Delivery assignments are not exportable.");
    }

}
