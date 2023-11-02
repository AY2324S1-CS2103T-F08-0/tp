package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "--add--name [" + PREFIX_NAME + "NAME] "
        + "--add--contact [" + PREFIX_CONTACT + "CONTACT] "
        + "--add--course [" + PREFIX_COURSE + "COURSE] "
        + "--add--tutorial [" + PREFIX_TUTORIAL + "TUTORIAL]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + "--add--course" + "CS2101";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Set<Role> updatedRoles = editPersonDescriptor.getRoles().orElse(personToEdit.getRoles());
        Set<Contact> updatedContacts = editPersonDescriptor.getContacts().orElse(personToEdit.getContacts());
        Set<Course> updatedCourses = editPersonDescriptor.getCourses().orElse(personToEdit.getCourses());
        Set<Tutorial> updatedTutorials = editPersonDescriptor.getTutorials().orElse(personToEdit.getTutorials());
        Favourite updatedFavourite = editPersonDescriptor.getFavourite().orElse(personToEdit.getFavourite());

        return new Person(updatedName, updatedRoles, updatedContacts, updatedCourses, updatedTutorials,
                updatedFavourite);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
            && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editPersonDescriptor", editPersonDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Set<Role> roles;
        private Set<Contact> contacts;
        private Set<Course> courses;
        private Set<Tutorial> tutorials;
        private Favourite favourite;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setRoles(toCopy.roles);
            setContacts(toCopy.contacts); // problem might be here, not sure what this is doing
            setCourses(toCopy.courses);
            setTutorials(toCopy.tutorials);
            setFavourite(toCopy.favourite);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, roles, contacts, courses, tutorials);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new HashSet<>(roles) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code roles} is null.
         */
        public Optional<Set<Role>> getRoles() {
            return (roles != null) ? Optional.of(Collections.unmodifiableSet(roles)) : Optional.empty();
        }

        /**
         * Sets {@code contacts} to this object's {@code contacts}.
         * A defensive copy of {@code contacts} is used internally.
         */
        public void setContacts(Set<Contact> newContacts) {
            if (newContacts == null) {
                this.contacts = null;
            } else {
                this.contacts = new HashSet<>(newContacts);
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code contacts} is null.
         */
        public Optional<Set<Contact>> getContacts() {
            return (contacts != null) ? Optional.of(Collections.unmodifiableSet(contacts)) : Optional.empty();
        }

        /**
         * Sets {@code courses} to this object's {@code courses}.
         * A defensive copy of {@code courses} is used internally.
         */
        public void setCourses(Set<Course> newCourses) {
            if (newCourses == null) {
                this.courses = null;
            } else {
                this.courses = new HashSet<>(newCourses);
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code courses} is null.
         */
        public Optional<Set<Course>> getCourses() {
            return (courses != null) ? Optional.of(Collections.unmodifiableSet(courses)) : Optional.empty();
        }

        /**
         * Sets {@code tutorials} to this object's {@code tutorials}.
         * A defensive copy of {@code tutorials} is used internally.
         */
        public void setTutorials(Set<Tutorial> newTutorials) {
            if (newTutorials == null) {
                this.tutorials = null;
            } else {
                this.tutorials = new HashSet<>(newTutorials);
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tutorials} is null.
         */
        public Optional<Set<Tutorial>> getTutorials() {
            return (tutorials != null) ? Optional.of(Collections.unmodifiableSet(tutorials)) : Optional.empty();
        }

        public void setFavourite(Favourite favourite) {
            this.favourite = favourite;
        }

        public Optional<Favourite> getFavourite() {
            return Optional.ofNullable(favourite);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                && Objects.equals(roles, otherEditPersonDescriptor.roles)
                && Objects.equals(contacts, otherEditPersonDescriptor.contacts)
                && Objects.equals(courses, otherEditPersonDescriptor.courses)
                && Objects.equals(tutorials, otherEditPersonDescriptor.tutorials);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("roles", roles)
                .add("contacts", contacts)
                .add("courses", courses)
                .add("tutorials", tutorials)
                .toString();
        }
    }
}
