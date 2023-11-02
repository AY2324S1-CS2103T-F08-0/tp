package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC_SYMBOL;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PROFESSOR;
//import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_TA;
//import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_STUDENT;
//import static seedu.address.logic.commands.CommandTestUtil.COURSE_TUTORIAL_DESC_1;
//import static seedu.address.logic.commands.CommandTestUtil.COURSE_TUTORIAL_DESC_3;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_1;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_STUDENT;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_TA;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.model.person.Contact;
//import seedu.address.model.person.Course;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Role;
//import seedu.address.model.person.Tutorial;
//import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        // assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    /*
    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC_SYMBOL, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, "1" + INVALID_CONTACT_DESC, Contact.MESSAGE_CONSTRAINTS); // invalid contact
        assertParseFailure(parser, "1" + INVALID_COURSE_DESC, Course.MESSAGE_CONSTRAINTS); // invalid course
        assertParseFailure(parser, "1" + INVALID_TUTORIAL_DESC, Tutorial.MESSAGE_CONSTRAINTS); // invalid tutorial

        // invalid role followed by valid contact
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC + CONTACT_DESC_AMY, Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC_SYMBOL + INVALID_ROLE_DESC + VALID_CONTACT_AMY
            + INVALID_COURSE_DESC, Name.MESSAGE_CONSTRAINTS);
    }
    */

    /*
    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + ROLE_DESC_TA + CONTACT_DESC_AMY
                + COURSE_TUTORIAL_DESC_1;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withContacts(VALID_CONTACT_AMY, VALID_CONTACT_BOB)
                .withCourses(VALID_COURSE_1.courseName).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_TA + CONTACT_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRoles(VALID_ROLE_TA)
                .withContacts(VALID_CONTACT_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = targetIndex.getOneBased() + ROLE_DESC_STUDENT;
        descriptor = new EditPersonDescriptorBuilder().withRoles(VALID_ROLE_STUDENT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // contact
        userInput = targetIndex.getOneBased() + CONTACT_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withContacts(VALID_CONTACT_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // course
        userInput = targetIndex.getOneBased() + COURSE_DESC_2;
        descriptor = new EditPersonDescriptorBuilder().withCourses(VALID_COURSE_2.courseName).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_CONTACT_DESC + CONTACT_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + CONTACT_DESC_BOB + INVALID_CONTACT_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + CONTACT_DESC_AMY + ROLE_DESC_STUDENT + COURSE_DESC_1
                + COURSE_TUTORIAL_DESC_1 + CONTACT_DESC_AMY + ROLE_DESC_TA + COURSE_DESC_2 + ROLE_DESC_PROFESSOR
                + CONTACT_DESC_BOB + COURSE_DESC_2 + COURSE_TUTORIAL_DESC_3;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_CONTACT, PREFIX_COURSE,
                PREFIX_TUTORIAL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_CONTACT_DESC + INVALID_COURSE_DESC + INVALID_ROLE_DESC
                + INVALID_TUTORIAL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_CONTACT, PREFIX_COURSE,
                PREFIX_TUTORIAL));
    }
    */

}
