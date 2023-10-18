package seedu.address.logic.commands;
import seedu.address.model.person.Role;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Tutorial;
import seedu.address.model.person.Course;
import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import java.util.ArrayList;
import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    AddressBook ab = new AddressBook();
    public static final String COMMAND_WORD = "list";
    public static final String enter = "\n      ";
    public static int numPeople = 0;
    public static String s = "";
    ArrayList<Object> nameList = new ArrayList<>(); 
    ArrayList<Set<Role>> roleList = new ArrayList<>();
    ArrayList<Set<Contact>> contactList = new ArrayList<>();
    ArrayList<Set<Course>> courseList = new ArrayList<>();
    ArrayList<Set<Tutorial>> tutorialList = new ArrayList<>();

    String roleString(Set<Role> roleList) {
        String begin = "Role: ";
        StringBuilder lst = new StringBuilder(begin);
        roleList.forEach(role -> lst.append(role.toString()).append(", "));
        if(lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    String contactString(Set<Contact> contactList) {
        String begin = "Contact: ";
        StringBuilder lst = new StringBuilder(begin);
        contactList.forEach(contact -> lst.append(contact.toString()).append(", "));
        if(lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    String courseString(Set<Course> courseList) {
        String begin = "Courses: ";
        StringBuilder lst = new StringBuilder(begin);
        courseList.forEach(course -> lst.append(course.toString()).append(", "));
        if(lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

    String tutorialString(Set<Tutorial> tutorialList) {
        String begin = "Tutorials: ";
        StringBuilder lst = new StringBuilder(begin);
        tutorialList.forEach(tutorial-> lst.append(tutorial.toString()).append(", "));
        if(lst.length() > begin.length()) {
            lst.setLength(lst.length() - 2);
        }
        return lst.toString();
    }

  
    

    @Override
    public CommandResult execute(Model model) {
        String peopleList = "";
        
        numPeople = model.getFilteredPersonList().size();
        s = numPeople == 1? "": "s";
        model.getFilteredPersonList().forEach((people) -> {
            nameList.add(people.getName());
            roleList.add(people.getRoles());
            contactList.add(people.getContacts());
            courseList.add(people.getCourses());
            tutorialList.add(people.getTutorials());
        });;

        for (int i = 0; i < nameList.size(); i++) {
            peopleList += (i + 1) +".  Name: " + nameList.get(i) + enter 
            + roleString(roleList.get(i)) + enter  + contactString(contactList.get(i)) + enter 
            + courseString(courseList.get(i)) + enter  + tutorialString(tutorialList.get(i)) + "\n";
        }

        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("You have " + numPeople + " profile" + s + " in your list\n" + peopleList);
    }
}
