package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.color.CMMException;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Favourites a person identified using it's displayed index from the address book.
 */
public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD= "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the person identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + " Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Favourited Person: %1$s";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFavourite = lastShownList.get(targetIndex.getZeroBased());
        model.favouritePerson(personToFavourite);
        return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS,
                Messages.format(personToFavourite)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        FavouriteCommand otherFavouriteCommand = (FavouriteCommand) other;
        return targetIndex.equals(otherFavouriteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
