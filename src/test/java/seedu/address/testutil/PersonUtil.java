package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Person;
/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        person.getRoles().stream().forEach(
                s -> sb.append(PREFIX_ROLE + s.toString() + " ")
        );
        person.getContacts().stream().forEach(
                s -> sb.append(PREFIX_CONTACT + s.contact + " ")
        );
        person.getCourses().stream().forEach(
                s -> sb.append(PREFIX_COURSE + s.courseName + " ")
        );
        person.getTutorials().stream().forEach(
                s -> sb.append(PREFIX_TUTORIAL + s.tutorialName + " ")
        );
        return sb.toString();
    }

}
