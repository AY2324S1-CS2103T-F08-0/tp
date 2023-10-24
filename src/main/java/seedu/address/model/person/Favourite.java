package seedu.address.model.person;

/**
 * Represents the boolean Favourite in a Person.
 * Guarantees: immutable
 */
public class Favourite {

    public static final String MESSAGE_CONSTRAINTS = "Favourite should be either true or false.";

    public boolean isFavourite;

    /**
    * Constructs a {@code Favourite}.
    */
    public Favourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public static boolean isValidFavourite(String test) {
        return test.equals("true") || test.equals("false");
    }

    /**
     * Sets the favourite to true.
     */
    public void setFavourite() {
        this.isFavourite = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Favourite)) {
            return false;
        }

        Favourite otherFavourite = (Favourite) other;
        return isFavourite == otherFavourite.isFavourite;
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
