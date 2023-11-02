package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHANGE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLES_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLES_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLES_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_DELETE;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private Name argsName;
    private Set<Contact> argsContacts = new HashSet<>();
    private Set<Tutorial> argsTutorials = new HashSet<>();
    private Set<Course> argsCourses = new HashSet<>();
    private Set<Role> argsRoles = new HashSet<>();

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, Model model) throws ParseException {

        ObservableList<Person> personList = model.getFilteredPersonList();
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CHANGE_NAME, PREFIX_COURSE_ADD, PREFIX_COURSE_CHANGE,
                    PREFIX_COURSE_DELETE, PREFIX_COURSE, PREFIX_TUTORIAL, PREFIX_ROLES_ADD, PREFIX_ROLES_CHANGE,
                    PREFIX_ROLES_DELETE, PREFIX_TUTORIAL_ADD, PREFIX_TUTORIAL_CHANGE, PREFIX_CONTACT_ADD,
                    PREFIX_CONTACT_CHANGE, PREFIX_ROLES_DELETE, PREFIX_TUTORIAL_DELETE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        Name name = extractNameForIndex(index, personList);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CHANGE_NAME, PREFIX_COURSE_ADD,
                PREFIX_COURSE_CHANGE, PREFIX_COURSE_DELETE, PREFIX_COURSE, PREFIX_TUTORIAL, PREFIX_ROLES_ADD,
                PREFIX_ROLES_CHANGE, PREFIX_CONTACT_ADD,
                PREFIX_CONTACT_CHANGE, PREFIX_ROLES_DELETE,
                PREFIX_ROLES_DELETE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) { // why not present
            editPersonDescriptor.setName(argsName);
        }
        for (Person person: personList) {
            if (person.getName() == name) {
                this.argsContacts = person.getContacts();
                this.argsCourses = person.getCourses();
                this.argsRoles = person.getRoles();
                this.argsTutorials = person.getTutorials();
                // need to use argscourses and tutorials in a meaningful way
            }
        }
        if (!argMultimap.getValue(PREFIX_CHANGE_NAME).isEmpty()) {
            Name newName = parseNameForEdit(argMultimap.getValue(PREFIX_CHANGE_NAME).get()).orElse(null);
            editPersonDescriptor.setName(newName);
        }

        Set<Role> newRoles = parseRoleForEdit(argMultimap.getAllValues(PREFIX_ROLES_ADD)).orElse(null);
        Set<Role> updatedRoles = new HashSet<>();
        if (newRoles == null) {
            newRoles = updatedRoles;
        }
        newRoles.addAll(argsRoles);
        updatedRoles.addAll(newRoles);
        Set<Role> toDeleteRole = parseRoleForEdit(argMultimap.getAllValues(PREFIX_ROLES_DELETE)).orElse(null);

        if (toDeleteRole != null) {
            updatedRoles.removeAll(toDeleteRole);
        }

        Set<Role> toChangeRole = parseRoleForEdit(argMultimap.getAllValues(PREFIX_ROLES_CHANGE)).orElse(null);
        Role oldRole = null;
        Role newRole = null;
        if (toChangeRole != null) {
            Iterator<Role> iterator = toChangeRole.iterator();

            if (iterator.hasNext()) {
                oldRole = iterator.next();
            }

            if (iterator.hasNext()) {
                newRole = iterator.next();
            }
        } else {
            // Handle the case where toChangeCourse is null
        }

        if (updatedRoles.contains(oldRole)) {
            updatedRoles.remove(oldRole);
            updatedRoles.add(newRole);
        } else {
            //return error
        }
        editPersonDescriptor.setRoles(updatedRoles);

        //argMultimap.getAllValues(PREFIX_CONTACT) is a collection of String
        Set<Contact> newContacts = parseContactsForEdit(argMultimap
                .getAllValues(PREFIX_CONTACT_ADD)).orElse(null);
        Set<Contact> updatedContacts = new HashSet<>();
        if (newContacts == null) {
            newContacts = updatedContacts;
        }
        newContacts.addAll(argsContacts);
        updatedContacts.addAll(newContacts);

        Set<Contact> toDeleteContact = parseContactsForEdit(argMultimap
                .getAllValues(PREFIX_CONTACT_DELETE)).orElse(null);
        if (toDeleteContact != null) {
            updatedContacts.removeAll(toDeleteContact);
        }
        Set<Contact> toChangeContact = parseContactsForEdit(argMultimap.getAllValues(PREFIX_CONTACT_CHANGE))
                .orElse(null);
        Contact oldContact = null;
        Contact newContact = null;
        if (toChangeContact != null) {
            Iterator<Contact> iterator = toChangeContact.iterator();

            if (iterator.hasNext()) {
                oldContact = iterator.next();
            }

            if (iterator.hasNext()) {
                newContact = iterator.next();
            }
        } else {
            //Handle the case where toChangeCourse is null
        }
        if (updatedContacts.contains(oldContact)) {
            updatedContacts.remove(oldContact);
            updatedContacts.add(newContact);
        } else {
            //return error
        }

        editPersonDescriptor.setContacts(updatedContacts);

        Set<Course> newCourses = parseCoursesForEdit(argMultimap.getAllValues(PREFIX_COURSE_ADD)).orElse(null);
        Set<Course> updatedCourses = new HashSet<>();
        if (newCourses == null) {
            newCourses = updatedCourses;
        }
        newCourses.addAll(argsCourses);
        updatedCourses.addAll(newCourses);

        Set<Course> toDeleteCourse = parseCoursesForEdit(argMultimap
                .getAllValues(PREFIX_COURSE_DELETE)).orElse(null);
        if (toDeleteCourse != null) {
            updatedCourses.removeAll(toDeleteCourse);
        }

        Set<Course> toChangeCourse = parseCoursesForEdit(argMultimap
                .getAllValues(PREFIX_COURSE_CHANGE)).orElse(null);

        Course oldCourse = null;
        Course newCourse = null;

        if (toChangeCourse != null) {
            Iterator<Course> iterator = toChangeCourse.iterator();

            if (iterator.hasNext()) {
                newCourse = iterator.next();
            }

            if (iterator.hasNext()) {
                oldCourse = iterator.next();
            }
        } else {
            // Handle the case where toChangeCourse is null
        }

        if (updatedCourses.contains(oldCourse)) {
            updatedCourses.remove(oldCourse);
            updatedCourses.add(newCourse);
        } else {
            //return error
        }

        Set<Tutorial> newTutorials = parseTutorialsForEdit(updatedCourses,
                argMultimap.getAllValues(PREFIX_TUTORIAL_ADD)).orElse(null);
        Set<Tutorial> updatedTutorials = new HashSet<>();
        if (newTutorials == null) {
            newTutorials = updatedTutorials;
        }
        newTutorials.addAll(argsTutorials);
        updatedTutorials.addAll(newTutorials);
        Set<Tutorial> toDeleteTutorial = parseTutorialsForEdit(updatedCourses,
                argMultimap.getAllValues(PREFIX_TUTORIAL_DELETE)).orElse(null);

        if (toDeleteTutorial != null) {
            updatedTutorials.removeAll(toDeleteTutorial);
        }

        Set<Tutorial> toChangeTutorial = parseTutorialsForEdit(updatedCourses,
                argMultimap.getAllValues(PREFIX_TUTORIAL_CHANGE)).orElse(null);
        Tutorial oldTutorial = null;
        Tutorial newTutorial = null;
        if (toChangeTutorial != null) {
            Iterator<Tutorial> iterator = toChangeTutorial.iterator();

            if (iterator.hasNext()) {
                oldTutorial = iterator.next();
            }

            if (iterator.hasNext()) {
                newTutorial = iterator.next();
            }
        } else {
            // Handle the case where toChangeCourse is null
        }

        if (updatedTutorials.contains(oldTutorial)) {
            updatedTutorials.remove(oldTutorial);
            updatedTutorials.add(newTutorial);
        } else {
            //return error
        }
        editPersonDescriptor.setTutorials(updatedTutorials);

        editPersonDescriptor.setCourses(updatedCourses);
        //parseContactsForEdit(argMultimap.getAllValues(PREFIX_CONTACT)).ifPresent(editPersonDescriptor::setContacts);
        //parseCoursesForEdit(argMultimap.getAllValues(PREFIX_COURSE)).ifPresent(editPersonDescriptor::setCourses);

        parseTutorialsForEdit(
                editPersonDescriptor.getCourses().orElse(new HashSet<Course>()),
                argMultimap.getAllValues(PREFIX_COURSE)
                ).ifPresent(editPersonDescriptor::setTutorials);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editPersonDescriptor); // more args here
    }

    /**
     * Parses the given input string and returns an EditCommand.
     *
     * @param args The input string containing the command arguments.
     * @return An EditCommand object if the input is valid and can be parsed.
     * @throws ParseException If the input string is in an invalid format or cannot be parsed.
     */
    public EditCommand parse(String args) throws ParseException {
        Index index;
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONTACT_ADD, PREFIX_CONTACT_DELETE,
                        PREFIX_CONTACT_CHANGE, PREFIX_COURSE, PREFIX_TUTORIAL);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    private Name extractNameForIndex(Index index, List<Person> personList) throws ParseException {
        if (index.getZeroBased() >= 0 && index.getZeroBased() < personList.size()) {
            Person person = personList.get(index.getZeroBased());
            return person.getName();
        } else {
            throw new ParseException("Invalid index.");
        }
    }



    /**
     * Parses {@code Collection<String> contacts} into a {@code Set<Contact>} if {@code contacts} is non-empty.
     * If {@code contacts} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Contact>} containing zero contacts.
     */
    private Optional<Set<Contact>> parseContactsForEdit(Collection<String> contacts) throws ParseException {
        assert contacts != null;

        if (contacts.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> contactSet = contacts.size() == 1 && contacts.contains("")
            ? Collections.emptySet() : contacts;
        return Optional.of(ParserUtil.parseContacts(contactSet));
    }

    private Optional<Set<Role>> parseRoleForEdit(Collection<String> roles) throws ParseException {
        assert roles != null;
        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> rolesSet = roles.size() == 1 && roles.contains("")
                ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(rolesSet));
    }

    private Optional<Name> parseNameForEdit(String name) throws ParseException {
        assert name != null;

        if (name == null) {
            //THROW ERROR
        }
        return Optional.of(ParserUtil.parseName(name));

    }

    /**
     * Parses {@code Collection<String> courses} into a {@code Set<Course>} if {@code courses}
     * is non-empty. If {@code courses} contain only one element which is an empty string,
     * it will be parsed into a {@code Set<Course>} containing zero courses.
     */
    private Optional<Set<Course>> parseCoursesForEdit(Collection<String> courses) throws ParseException {
        assert courses != null;

        if (courses.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> courseSet = courses.size() == 1 && courses.contains("") ? Collections.emptySet() : courses;
        return Optional.of(ParserUtil.parseCourses(courseSet));
    }

    /**
     * Parses {@code Collection<String> tutorials} into a {@code Set<Tutorial>} if {@code tutorials} is non-empty.
     * If {@code tutorials} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tutorial>} containing zero tutorials.
     */
    private Optional<Set<Tutorial>> parseTutorialsForEdit(Set<Course> courses,
            Collection<String> tutorials) throws ParseException {
        assert tutorials != null;

        if (tutorials.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tutorialSet = (tutorials.size() == 1 && tutorials.contains(""))
                   ? Collections.emptySet() : tutorials;

        return Optional.of(ParserUtil.parseTutorials(tutorialSet));
    }
}
