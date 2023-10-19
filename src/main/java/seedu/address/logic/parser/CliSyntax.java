package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("--name");
    public static final Prefix PREFIX_ROLE = new Prefix("--role");
    public static final Prefix PREFIX_CONTACT = new Prefix("--contact");
    public static final Prefix PREFIX_COURSE = new Prefix("--course");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("s/");
}
