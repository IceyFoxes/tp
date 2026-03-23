package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<Person> personListView;

    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        // Disable default tab traversal for the list so it doesn't steal focus
        personListView.setFocusTraversable(false);
    }

    /**
     * Selects the next person in the list, wrapping around to the first if at the end.
     */
    public void selectNext() {
        int size = personListView.getItems().size();
        if (size == 0) return;

        int nextIndex = (personListView.getSelectionModel().getSelectedIndex() + 1) % size;
        personListView.getSelectionModel().select(nextIndex);
        personListView.scrollTo(nextIndex);
    }

    /**
     * Selects the previous person in the list, wrapping around to the last if at the start.
     */
    public void selectPrevious() {
        int size = personListView.getItems().size();
        if (size == 0) return;

        int currentIndex = personListView.getSelectionModel().getSelectedIndex();
        int prevIndex = (currentIndex <= 0) ? size - 1 : currentIndex - 1;
        personListView.getSelectionModel().select(prevIndex);
        personListView.scrollTo(prevIndex);
    }

    /**
     * Selects the first person in the list.
     */
    public void selectFirst() {
        if (personListView.getItems().isEmpty()) return;
        personListView.getSelectionModel().selectFirst();
        personListView.scrollTo(0);
    }

    /**
     * Selects the last person in the list.
     */
    public void selectLast() {
        if (personListView.getItems().isEmpty()) return;
        personListView.getSelectionModel().selectLast();
        personListView.scrollTo(personListView.getItems().size() - 1);
    }

    /**
     * Returns true if a person is currently selected.
     */
    public boolean isAnySelected() {
        return personListView.getSelectionModel().getSelectedIndex() >= 0;
    }

    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}