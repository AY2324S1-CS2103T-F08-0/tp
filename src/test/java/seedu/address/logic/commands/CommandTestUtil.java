package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
//import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
//import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
//import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CHARLIE = "Charlie";
    public static final String VALID_NAME_DANNY = "Danny";
    public static final String VALID_ROLE_STUDENT = "Student";
    public static final String VALID_ROLE_TA = "TA";
    public static final String VALID_ROLE_PROFESSOR = "Professor";
    public static final String VALID_CONTACT_AMY = "amy@example.com";
    public static final String VALID_CONTACT_BOB = "bob@example.com";
    public static final String VALID_PHONE_CONTACT_CHARLIE = "91234567";
    public static final String VALID_EMAIL_CONTACT_CHARLIE = "charlie@example.com";
    public static final String VALID_TELE_CONTACT_CHARLIE = "@charlieee";
    public static final Course VALID_COURSE_1 = new Course("CS2103T");
    public static final Course VALID_COURSE_2 = new Course("CS2100");
    public static final Course VALID_COURSE_3 = new Course("CS2109S");
    public static final Course VALID_COURSE_4 = new Course("CS2106");
    public static final Tutorial VALID_TUTORIAL_1 = new Tutorial(VALID_COURSE_1,
            VALID_COURSE_1.courseName + "/F08");
    public static final Tutorial VALID_TUTORIAL_2 = new Tutorial(VALID_COURSE_2,
            VALID_COURSE_2.courseName + "/T32");
    public static final Tutorial VALID_TUTORIAL_3 = new Tutorial(VALID_COURSE_3,
            VALID_COURSE_3.courseName + "/T31");
    public static final Tutorial VALID_TUTORIAL_4 = new Tutorial(VALID_COURSE_4,
            VALID_COURSE_4.courseName + "/T04");

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String NAME_DESC_CHARLIE = " " + PREFIX_NAME + " " + VALID_NAME_CHARLIE;
    public static final String NAME_DESC_DANNY = " " + PREFIX_NAME + " " + VALID_NAME_DANNY;
    public static final String ROLE_DESC_STUDENT = " " + PREFIX_ROLE + " " + VALID_ROLE_STUDENT;
    public static final String ROLE_DESC_TA = " " + PREFIX_ROLE + " " + VALID_ROLE_TA;
    public static final String ROLE_DESC_PROFESSOR = " " + PREFIX_ROLE + " " + VALID_ROLE_PROFESSOR;
    public static final String ROLE_DESC_MULTIPLE = " " + PREFIX_ROLE + " " + VALID_ROLE_STUDENT + ", "
            + VALID_ROLE_TA;
    public static final String CONTACT_DESC_AMY = " " + PREFIX_CONTACT + " " + VALID_CONTACT_AMY;
    public static final String CONTACT_DESC_BOB = " " + PREFIX_CONTACT + " " + VALID_CONTACT_BOB;
    public static final String CONTACT_DESC_CHARLIE_PHONE = " " + PREFIX_CONTACT + " " + VALID_PHONE_CONTACT_CHARLIE;
    public static final String CONTACT_DESC_CHARLIE_EMAIL = " " + PREFIX_CONTACT + " " + VALID_EMAIL_CONTACT_CHARLIE;
    public static final String CONTACT_DESC_CHARLIE_TELE = " " + PREFIX_CONTACT + " " + VALID_TELE_CONTACT_CHARLIE;
    public static final String CONTACT_DESC_MULTIPLE = " " + PREFIX_CONTACT + " " + VALID_PHONE_CONTACT_CHARLIE
            + ", " + VALID_EMAIL_CONTACT_CHARLIE + ", " + VALID_TELE_CONTACT_CHARLIE;

    // COURSE_DESC is used only when a Course is added without a Tutorial
    public static final String COURSE_DESC_1 = " " + PREFIX_COURSE + " " + VALID_COURSE_1;
    public static final String COURSE_DESC_2 = " " + PREFIX_COURSE + " " + VALID_COURSE_2;
    public static final String COURSE_DESC_3 = " " + PREFIX_COURSE + " " + VALID_COURSE_3;
    public static final String COURSE_DESC_4 = " " + PREFIX_COURSE + " " + VALID_COURSE_4;
    public static final String COURSE_DESC_MULTIPLE = " " + PREFIX_COURSE + " " + VALID_COURSE_1 + ", " + VALID_COURSE_2
            + ", " + VALID_COURSE_3 + ", " + VALID_COURSE_4;

    // when using COURSE_TUTORIAL_DESC, COURSE_DESC should not be used
    public static final String COURSE_TUTORIAL_DESC_1 = " " + PREFIX_COURSE + " " + VALID_TUTORIAL_1;
    public static final String COURSE_TUTORIAL_DESC_2 = " " + PREFIX_COURSE + " " + VALID_TUTORIAL_2;
    public static final String COURSE_TUTORIAL_DESC_3 = " " + PREFIX_COURSE + " " + VALID_TUTORIAL_3;
    public static final String COURSE_TUTORIAL_DESC_4 = " " + PREFIX_COURSE + " " + VALID_TUTORIAL_4;
    public static final String COURSE_TUTORIAL_DESC_MULTIPLE = " " + PREFIX_COURSE + " " + VALID_TUTORIAL_1
            + ", " + VALID_TUTORIAL_2 + ", " + VALID_TUTORIAL_3 + ", " + VALID_TUTORIAL_4;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "
            + "James&"; // '&' not allowed in names
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + " "
            + "Teacher"; // Teacher is not a supported role
    public static final String INVALID_CONTACT_DESC = " " + PREFIX_CONTACT + " "; // empty contact
    public static final String INVALID_COURSE_DESC = " " + PREFIX_COURSE
            + "  "; // empty string not allowed for courses
    public static final String INVALID_TUTORIAL_DESC = " " + PREFIX_TUTORIAL
            + "/F08"; // a Course is required for a tutorial

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withRoles(VALID_ROLE_STUDENT).withContacts(VALID_CONTACT_AMY)
                .withCourses(VALID_COURSE_1.toString()).withFavourite(false).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withRoles(VALID_ROLE_TA).withContacts(VALID_CONTACT_BOB)
                .withCourses(VALID_COURSE_2.courseName, VALID_COURSE_3.courseName)
                .withFavourite(false).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

    }

    /**
     * Executes the given {@code command} with a dummy model and confirms that
     * the resulting {@link CommandResult} contains the expected feedback to the user.
     *
     * @param command              The command to be executed.
     * @param dummyModel           A dummy model to execute the command with.
     * @param expectedCommandResult The expected feedback to be returned by the command.
     *
     * @throws AssertionError if the execution of the command fails or if the actual
     *                      feedback from the command does not match the expected feedback.
     */
    public static void assertCommandSuccess(Command command, Model dummyModel, String expectedCommandResult) {
        try {
            CommandResult result = command.execute(dummyModel);
            assertEquals(expectedCommandResult, result.getFeedbackToUser());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    public static void asserCommandSuccess() {

    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
