package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.AppMode;
import seedu.address.model.person.Person;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";
    private AppMode currentMode = AppMode.LOCKED;

    @FXML
    private Label placeholder;
    @FXML
    private StackPane placeholderContainer;
    @FXML
    private VBox detailsContainer;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    /**
     * Instantiates a new {@code PersonDetailPanel}.
     */
    public PersonDetailPanel() {
        super(FXML);
        tags.prefWrapLengthProperty().bind(detailsContainer.widthProperty());
        clearPerson();
    }

    public void setPerson(Person person) {
        assert person != null;

        showPersonDetails();
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        if (currentMode == AppMode.UNLOCKED) {
            status.setText(person.getStatus() + " Contact");
        }

        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Updates the internal mode and toggles visibility of restricted fields.
     *
     * @param mode The current {@code AppMode} of the application.
     */
    public void updateMode(AppMode mode) {
        this.currentMode = mode;
        boolean isUnlocked = (mode == AppMode.UNLOCKED);

        status.setVisible(isUnlocked); // setVisible(false) hides the field
        status.setManaged(isUnlocked); // setManaged(false) ensures it doesn't take up layout space
    }

    /**
     * Clears the person details from the panel.
     */
    public void clearPerson() {
        showPlaceholder();
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        status.setText("");
        tags.getChildren().clear();
    }

    private void showPlaceholder() {
        placeholder.setVisible(true);
        placeholder.setManaged(true);
        placeholderContainer.setVisible(true);
        placeholderContainer.setManaged(true);
        detailsContainer.setVisible(false);
        detailsContainer.setManaged(false);
    }

    private void showPersonDetails() {
        placeholder.setVisible(false);
        placeholder.setManaged(false);
        placeholderContainer.setVisible(false);
        placeholderContainer.setManaged(false);
        detailsContainer.setVisible(true);
        detailsContainer.setManaged(true);
    }
}
