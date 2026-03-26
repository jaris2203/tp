package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final Image ADDRESS_IMAGE =
            new Image(PersonCard.class.getResourceAsStream("/images/address_icon.png"));
    private static final Image PHONE_IMAGE =
            new Image(PersonCard.class.getResourceAsStream("/images/phone_icon.png"));

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label deliveryStatus;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane boxes;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        // Phone
        ImageView phoneIcon = new ImageView(PHONE_IMAGE);
        phoneIcon.setFitWidth(14);
        phoneIcon.setFitHeight(14);
        phone.setGraphic(phoneIcon);
        phone.setGraphicTextGap(2);
        phone.setText(person.getPhone().value);
        // Address
        ImageView addressIcon = new ImageView(ADDRESS_IMAGE);
        addressIcon.setFitWidth(14);
        addressIcon.setFitHeight(14);
        address.setGraphic(addressIcon);
        address.setGraphicTextGap(2);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText("Remark: " + person.getRemark().value);
        deliveryStatus.setText("Delivery Status: " + person.getDeliveryStatus().deliveryStatus);
        person.getBoxes().stream()
                .sorted(Comparator.comparing(box -> box.boxName))
                .forEach(box -> boxes.getChildren().add(new BoxCard(box).getRoot()));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (tag.tagName.startsWith("DRIVER:")) {
                        Image driverImage = new Image(getClass().getResourceAsStream("/images/driver_icon.png"));
                        ImageView driverIcon = new ImageView(driverImage);
                        driverIcon.setFitWidth(16);
                        driverIcon.setFitHeight(16);
                        tagLabel.setGraphic(driverIcon);
                        tagLabel.setGraphicTextGap(2);
                        tagLabel.getStyleClass().add("driver-tag");
                    } else {
                        tagLabel.getStyleClass().add("tag");
                    }
                    tags.getChildren().add(tagLabel);
                });


    }
}

