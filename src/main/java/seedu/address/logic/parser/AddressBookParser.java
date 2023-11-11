package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CheckedFunction;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavListCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCourseCommand;
import seedu.address.logic.commands.FindRoleCommand;
import seedu.address.logic.commands.FindTutorialCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for the initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Maps each command's Command Word to a lambda that returns the appropriate (parsed) command.
     * The function takes in an input (userinput) and returns that command parsed with that user
     * input.
     */
    private static final Map<String,
            CheckedFunction<String, Command, ParseException>> wordToCommandMap = new HashMap<>();


    /**
     * Initialize the word to command map. Remember each command word maps to a lambda function
     * that will return the parsed Command object. This command object will then be executed.
     */
    public AddressBookParser() {
        wordToCommandMap.put(AddCommand.COMMAND_WORD, (arguments) -> new AddCommandParser().parse(arguments));
        wordToCommandMap.put(DeleteCommand.COMMAND_WORD, (arguments) -> new DeleteCommandParser().parse(arguments));
        wordToCommandMap.put(ClearCommand.COMMAND_WORD, (arguments) -> new ClearCommand());

        wordToCommandMap.put(FavouriteCommand.COMMAND_WORD, (arguments) ->
                new FavouriteCommandParser().parse(arguments));

        wordToCommandMap.put(UnfavouriteCommand.COMMAND_WORD, (arguments) ->
                new UnfavouriteCommandParser().parse(arguments));

        wordToCommandMap.put(FavListCommand.COMMAND_WORD, (arguments) -> new FavListCommand());

        wordToCommandMap.put(FindCommand.COMMAND_WORD, (arguments) -> new FindCommandParser().parse(arguments));
        wordToCommandMap.put(ListCommand.COMMAND_WORD, (arguments) -> new ListCommand());
        wordToCommandMap.put(FindRoleCommand.COMMAND_WORD, (arguments) ->
                new FindRoleCommandParser().parse(arguments));

        wordToCommandMap.put(FindCourseCommand.COMMAND_WORD, (arguments) ->
                new FindCourseCommandParser().parse(arguments));

        wordToCommandMap.put(FindTutorialCommand.COMMAND_WORD, (arguments) ->
                new FindTutorialCommandParser().parse(arguments));

        wordToCommandMap.put(ExitCommand.COMMAND_WORD, (arguments) -> new ExitCommand());
        wordToCommandMap.put(HelpCommand.COMMAND_WORD, (arguments) -> new HelpCommand());
    }

    /**
     * Returns a set of all command words.
     *
     * @return the set of all command words.
     */
    public Set<String> getCommandWords() {
        return wordToCommandMap.keySet();
    }

    /**
     * Parses user input into a command for execution.
     *
     * @param userInput the full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if (commandWord.equals("list") && !arguments.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        if (commandWord.equals("clear") && !arguments.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
        if (commandWord.equals("help") && !arguments.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        if (commandWord.equals("favlist") && !arguments.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavListCommand.MESSAGE_USAGE));
        }
        // Note to developers: Change the log level in config.json to enable lower-level
        // (i.e., FINE, FINER, and lower) log messages such as the one below.
        // Lower-level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        Optional<CheckedFunction<String, Command, ParseException>> commandParserFunctionOptional =
            Optional.ofNullable(wordToCommandMap.get(commandWord));

        Command parsedCommand = commandParserFunctionOptional.orElseThrow(() -> {
            return new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }).apply(arguments);

        return parsedCommand;
    }
}

