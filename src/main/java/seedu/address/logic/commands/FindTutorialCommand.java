package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TutorialContainsKeywordsPredicate;

/**
 * Command that filters out the people based on their tutorial
 */
public class FindTutorialCommand extends Command {
    public static final String COMMAND_WORD = "searchtutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tutorials contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "CS2100/G07";

    private final TutorialContainsKeywordsPredicate predicate;

    public FindTutorialCommand(TutorialContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTutorialCommand)) {
            return false;
        }

        FindTutorialCommand otherFindCommand = (FindTutorialCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
