package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Box;

/**
 * A UI component that displays information of a {@code Box}
 */
public class BoxCard extends UiPart<Region> {

    private static final String FXML = "BoxCard.fxml";

    public final Box box;

    @FXML
    private Label boxName;
    @FXML
    private Label expiredLabel;
    @FXML
    private Label expiryDate;

    /**
     * Creates a {@code BoxCard} with the given {@code Box}
     */
    public BoxCard(Box box) {
        super(FXML);
        this.box = box;
        boxName.setText(box.boxName);
        if (box.isExpired()) {
            expiredLabel.setVisible(true);
            expiredLabel.setManaged(true);
        }
        expiryDate.setText("Exp: " + box.expiryDate.toString());
    }
}
