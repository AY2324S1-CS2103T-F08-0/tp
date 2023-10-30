package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("--name");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ROLE = new Prefix("--role");
    public static final Prefix PREFIX_CONTACT = new Prefix("--contact");
    public static final Prefix PREFIX_COURSE = new Prefix("--course");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("--tutorial");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ADD = new Prefix("--add");
    public static final Prefix PREFIX_DELETE = new Prefix("--delete");
    public static final Prefix PREFIX_CHANGE = new Prefix("--change");

}
