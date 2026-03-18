package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Box;

public class BoxCard extends UiPart<Region> {

    private static final String FXML = "BoxCard.fxml";

    public final Box box;

    @FXML
    private Label boxName;
    @FXML
    private Label expiryDate;

    public BoxCard(Box box) {
        super(FXML);
        this.box = box;
        boxName.setText(box.boxName);
        expiryDate.setText(box.expiryDate.toString());
    }
}
