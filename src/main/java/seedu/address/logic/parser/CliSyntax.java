package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("--name");
    public static final Prefix PREFIX_ROLE = new Prefix("--role");
    public static final Prefix PREFIX_CONTACT = new Prefix("--contact");
    public static final Prefix PREFIX_CONTACT_CHANGE = new Prefix("--change--contact");
    public static final Prefix PREFIX_CONTACT_ADD = new Prefix("--add--contact");
    public static final Prefix PREFIX_CONTACT_DELETE = new Prefix("--delete--contact");

    public static final Prefix PREFIX_COURSE = new Prefix("--course");
    public static final Prefix PREFIX_CHANGE_NAME = new Prefix("--change--name");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("--tutorial");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_COURSE_ADD = new Prefix("--add--course");
    public static final Prefix PREFIX_COURSE_DELETE = new Prefix("--delete--course");
    public static final Prefix PREFIX_COURSE_CHANGE = new Prefix("--change--course");
    public static final Prefix PREFIX_ROLES_ADD = new Prefix("--add--roles");
    public static final Prefix PREFIX_ROLES_DELETE = new Prefix("--delete--roles");
    public static final Prefix PREFIX_ROLES_CHANGE = new Prefix("--change--roles");
    public static final Prefix PREFIX_TUTORIAL_ADD = new Prefix("--add--tutorial");
    public static final Prefix PREFIX_TUTORIAL_CHANGE = new Prefix("--change--tutorial");
    public static final Prefix PREFIX_TUTORIAL_DELETE = new Prefix("--delete--tutorial");




}
