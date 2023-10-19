package seedu.address.testutil;

import java.util.HashSet; 
import java.util.Set; 
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Course;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setRoles(person.getRoles());
        descriptor.setContacts(person.getContacts());
        descriptor.setCourses(person.getCourses());
        descriptor.setTutorials(person.getTutorials());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRoles(String... roles) {
        Set<Role> roleSet = Stream.of(roles).map(Role::new).collect(Collectors.toSet());
        descriptor.setRoles(roleSet);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withContacts(String... contacts) {
        Set<Contact> contactSet = Stream.of(contacts).map(Contact::new).collect(Collectors.toSet());
        descriptor.setContacts(contactSet);
        return this;
    }

    /**
     * Sets the {@code Courses} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCourses(String... courses) {
        Set<Course> courseSet = Stream.of(courses).map(Course::new).collect(Collectors.toSet());
        descriptor.setCourses(courseSet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTutorials(String... tutorialStrings) {
        Set<Course> courseSet = descriptor.getCourses().orElse(new HashSet<>());

        String[][] courseTutorialNames = Stream.of(tutorialStrings)
            .map(Tutorial::splitCourseTutorialName)
            .filter((courseTutorialName) -> courseTutorialName.length == 2)
            .toArray(String[][]::new);

        Set<Tutorial> tutorials = new HashSet<>();
        for (String[] courseTutorialName : courseTutorialNames) {
            String courseName = courseTutorialName[0];
            String tutorialName = courseTutorialName[1];

            Course relevantCourse = null;
            for (Course course : courseSet) {
                if (course.getCourseName().equals(courseName)) {
                    relevantCourse = course;
                    break;
                }
            }

            if (relevantCourse != null) {
                tutorials.add(new Tutorial(relevantCourse, tutorialName));
            }
        }

        descriptor.setTutorials(tutorials);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
