package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
import seedu.address.model.person.Tutorial;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONTACT, PREFIX_COURSE, PREFIX_TUTORIAL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CONTACT, PREFIX_COURSE, PREFIX_TUTORIAL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        parseContactsForEdit(argMultimap.getAllValues(PREFIX_CONTACT)).ifPresent(editPersonDescriptor::setContacts);
        parseCoursesForEdit(argMultimap.getAllValues(PREFIX_COURSE)).ifPresent(editPersonDescriptor::setCourses);

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

        //        Collection<String> tutorialSet = (tutorials.size() == 1 && tutorials.contains(""))
        //            ? Collections.emptySet() : tutorials;

        return Optional.of(ParserUtil.parseTutorials(courses));
    }
}
