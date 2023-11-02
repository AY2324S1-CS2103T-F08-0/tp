package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;
//import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
//import seedu.address.logic.commands.EditCommand;
//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCourseCommand;
import seedu.address.logic.commands.FindRoleCommand;
import seedu.address.logic.commands.FindTutorialCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CourseContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
//import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.model.person.TutorialContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person), model);
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, model) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", model) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), model);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    /*
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder(ALICE).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        String des = PersonUtil.getEditPersonDescriptorDetails(descriptor);
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + des, model);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
    */

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                model);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findRole() throws Exception {
        List<String> keywords = Arrays.asList("Student", "TA", "Professor");
        FindRoleCommand command = (FindRoleCommand) parser.parseCommand(
                FindRoleCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                model);
        assertEquals(new FindRoleCommand(new RoleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findCourse() throws Exception {
        List<String> keywords = Arrays.asList("CS2103/F08", "CS2101/G06", "CS2100/T20");
        FindCourseCommand command = (FindCourseCommand) parser.parseCommand(
                FindCourseCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                model);
        assertEquals(new FindCourseCommand(new CourseContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findTutorial() throws Exception {
        List<String> keywords = Arrays.asList("T01", "T02", "T03");
        FindTutorialCommand command = (FindTutorialCommand) parser.parseCommand(
                FindTutorialCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                model);
        assertEquals(new FindTutorialCommand(new TutorialContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_favourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), model);
    }

    @Test
    public void parseCommand_unfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), model);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, model) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", model) instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("", model));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand",
                model));
    }
}

