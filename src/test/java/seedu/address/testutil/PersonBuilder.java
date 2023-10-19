package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String[] DEFAULT_ROLES = new String[]{"Student"};
    public static final String[] DEFAULT_CONTACTS = new String[]{"amy@gmail.com"};
    public static final String[] DEFAULT_COURSES = new String[]{"CS1101"};
    public static final String[] DEFAULT_TUTORIALS = new String[]{"CS1101/T03E"};

    private Name name;
    private Set<Role> roles;
    private Set<Contact> contacts;
    private Set<Course> courses;
    private Set<Tutorial> tutorials;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        roles = new HashSet<>(Arrays.stream(DEFAULT_ROLES).map(Role::new).collect(Collectors.toSet()));
        contacts = new HashSet<>(Arrays.stream(DEFAULT_CONTACTS).map(Contact::new).collect(Collectors.toSet()));
        courses = new HashSet<>(Arrays.stream(DEFAULT_COURSES).map(Course::new).collect(Collectors.toSet()));

        // We want course, tutorial name pairs with exactly one course name and one tutorial name.
        String[][] courseTutorialNames = Arrays.stream(DEFAULT_TUTORIALS)
            .map(Tutorial::splitCourseTutorialName)
            .filter((courseTutorialName) -> courseTutorialName.length == 2)
            .toArray(String[][]::new);

        tutorials = new HashSet<>();
        for (String[] courseTutorialName : courseTutorialNames) {
            String courseName = courseTutorialName[0];
            String tutorialName = courseTutorialName[1];

            Course relevantCourse = null;
            for (Course course : courses) {
                if (course.getCourseName().equals(courseName)) {
                    relevantCourse = course;
                    break;
                }
            }

            if (relevantCourse != null) {
                tutorials.add(new Tutorial(relevantCourse, tutorialName));
            }
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        roles = new HashSet<>(personToCopy.getRoles());
        contacts = new HashSet<>(personToCopy.getContacts());
        courses = new HashSet<>(personToCopy.getCourses());
        tutorials = new HashSet<>(personToCopy.getTutorials());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code Roles} into a {@code Set<Role>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    /**
     * Parses the {@code Contacts} into a {@code Set<Contact>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withContacts(String ... contacts) {
        this.contacts = SampleDataUtil.getContactSet(contacts);
        return this;
    }

    /**
     * Parses the {@code Courses} into a {@code Set<Course>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCourses(String ... courses) {
        this.courses = SampleDataUtil.getCourseSet(Arrays.stream(courses).map(Course::new).toArray(Course[]::new));
        return this;
    }

    /**
     * Parses the {@code Tutorials} into a {@code Set<Tutorial>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTutorials(String ... tutorialStrings) {
        String[][] courseTutorialNames = Arrays.stream(tutorialStrings)
            .map(Tutorial::splitCourseTutorialName)
            .filter((courseTutorialName) -> courseTutorialName.length == 2)
            .toArray(String[][]::new);

        tutorials = new HashSet<>();
        for (String[] courseTutorialName : courseTutorialNames) {
            String courseName = courseTutorialName[0];
            String tutorialName = courseTutorialName[1];

            Course relevantCourse = null;
            for (Course course : courses) {
                if (course.getCourseName().equals(courseName)) {
                    relevantCourse = course;
                    break;
                }
            }

            if (relevantCourse != null) {
                tutorials.add(new Tutorial(relevantCourse, tutorialName));
            }
        }
        return this;
    }

    public Person build() {
        return new Person(name, roles, contacts, courses, tutorials);
    }

}
