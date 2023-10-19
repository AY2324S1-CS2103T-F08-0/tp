package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
        .withRoles("Student").withContacts("alice@example.com")
        .withCourses("CS1101").withTutorials("CS1101/T03E").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
        .withRoles("TA").withContacts("johnd@example.com")
        .withCourses("CS2101").withTutorials("CS2101/G06").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
        .withRoles("Professor").withContacts("heinz@example.com")
        .withCourses("CS2103T").withTutorials("CS2103T/F08").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
        .withRoles("Student").withContacts("cornelia@example.com")
        .withCourses("ES2660").withTutorials("ES2660/SG22").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
        .withRoles("TA").withContacts("werner@example.com")
        .withCourses("CS1101").withTutorials("CS1101/T03E").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
        .withRoles("Professor").withContacts("lydia@example.com")
        .withCourses("CS2101").withTutorials("CS2101/G06").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
        .withRoles("Student").withContacts("anna@example.com")
        .withCourses("ES2660").withTutorials("ES2660/SG22").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
        .withRoles("TA").withContacts("stefan@example.com")
        .withCourses("CS2101").withTutorials("CS2101/G06").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
        .withRoles("Professor").withContacts("hans@example.com")
        .withCourses("CS2103T").withTutorials("CS2103T/F08").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
        .withRoles(VALID_ROLE_AMY).withContacts(VALID_CONTACT_AMY)
        .withCourses(VALID_COURSE_AMY).withTutorials(VALID_TUTORIAL_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
        .withRoles(VALID_ROLE_BOB).withContacts(VALID_CONTACT_BOB)
        .withCourses(VALID_COURSE_BOB).withTutorials(VALID_TUTORIAL_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
