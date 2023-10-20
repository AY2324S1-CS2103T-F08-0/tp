package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String NAME_BEGIN_STRING = "Name: ";
    private static final String CONTACTS_BEGIN_STRING = "Contacts: ";
    private static final String COURSES_BEGIN_STRING = "Courses: ";
    private static final String TUTORIAL_BEGIN_STRING = "Tutorials: ";
    private static final String ROLES_BEGIN_STRING = "Roles: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https:// github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label contacts;
    @FXML
    private Label courses;
    @FXML
    private Label tutorials;
    @FXML
    private Label roles;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(NAME_BEGIN_STRING + person.getName().fullName);

        contacts.setText(CONTACTS_BEGIN_STRING + person.getContacts().stream()
                .map((contact) -> contact.toString())
                .reduce("", (current, next) -> current + next));

        courses.setText(COURSES_BEGIN_STRING + person.getCourses().stream()
                .map((course) -> course.toString())
                .reduce("", (current, next) -> current + next));

        tutorials.setText(TUTORIAL_BEGIN_STRING + person.getTutorials().stream()
                .map((tutorial) -> tutorial.toString())
                .reduce("", (current, next) -> current + next));

        roles.setText(ROLES_BEGIN_STRING + person.getRoles().stream().map((roles) -> roles.toString())
                .reduce("", (current, next) -> current + next));
    }
}
