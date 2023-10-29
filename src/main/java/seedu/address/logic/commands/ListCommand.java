package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Course;
import seedu.address.model.person.Role;
import seedu.address.model.person.Tutorial;



/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    public static final String MESSAGE_SUCCESS = "You have 0 profile in your list\n";
    public static final String COMMAND_WORD = "list";
    private static final String enter = "\n      ";
    private static int numPeople = 0;
    private static String s = "";
    private ArrayList<Object> nameList = new ArrayList<>();
    private ArrayList<Set<Role>> roleList = new ArrayList<>();
    private ArrayList<Set<Contact>> contactList = new ArrayList<>();
    private ArrayList<Set<Course>> courseList = new ArrayList<>();
    private ArrayList<Set<Tutorial>> tutorialList = new ArrayList<>();

    private String roleToString(Set<Role> roleList) {
        String begin = "Role: ";
        StringBuilder lst = new StringBuilder(begin);
        roleList.forEach(role -> lst.append(role.toString()).append(", "));
        if (lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    private String contactToString(Set<Contact> contactList) {
        String begin = "Contact: ";
        StringBuilder lst = new StringBuilder(begin);
        contactList.forEach(contact -> lst.append(contact.toString()).append(", "));
        if (lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    private String courseToString(Set<Course> courseList) {
        String begin = "Courses: ";
        StringBuilder lst = new StringBuilder(begin);
        courseList.forEach(course -> lst.append(course.toString()).append(", "));
        if (lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    private String tutorialToString(Set<Tutorial> tutorialList) {
        String begin = "Tutorials: ";
        StringBuilder lst = new StringBuilder(begin);
        courseList.forEach(tutorial-> lst.append(tutorial.toString()).append(", "));
        tutorialList.forEach(tutorial-> lst.append(tutorial.toString()).append(", "));
        if (lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        String peopleList = "";
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        numPeople = model.getFilteredPersonList().size();
        s = numPeople <= 1 ? "" : "s";
        model.getFilteredPersonList().forEach((people) -> {
            nameList.add(people.getName());
            roleList.add(people.getRoles());
            contactList.add(people.getContacts());
            courseList.add(people.getCourses());
            tutorialList.add(people.getTutorials());
        });;

        for (int i = 0; i < nameList.size(); i++) {
            peopleList += (i + 1) + ".  Name: " + nameList.get(i) + enter
                    + roleToString(roleList.get(i)) + enter + contactToString(contactList.get(i)) + enter
                    + courseToString(courseList.get(i)) + enter + tutorialToString(tutorialList.get(i)) + "\n";
        }
        return new CommandResult("You have " + numPeople + " profile" + s + " in your list\n" + peopleList);
    }
}
