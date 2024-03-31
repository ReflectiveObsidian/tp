package seedu.address.logic.relationship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersonsUuid.getTypicalAddressBook;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.relationship.Relationship;

class DeleteRelationshipCommandParserTest {
    private AddRelationshipCommandParser parser = new AddRelationshipCommandParser();
    private Model model;
    private Model expectedModel;
    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    void parse_invalidInputLength_throwsParseException() {
        String userInput = ("1234 workmates");
        Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_validInputDeleteRelationTypes_success() {
        String testOriginUuid = "0001";
        String testTargetUuid = "0005";
        String relationshipDescriptor = "housemates";
        UUID person1Uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID person2Uuid = UUID.fromString("00000000-0000-0000-0000-000000000005");
        model.addRelationship(new Relationship(person1Uuid, person2Uuid, relationshipDescriptor));
        expectedModel.addRelationship(new Relationship(person1Uuid, person2Uuid, relationshipDescriptor));
        expectedModel.deleteRelationship(new Relationship(person1Uuid, person2Uuid, relationshipDescriptor));
        String expectedMessage = "Delete successful";
        DeleteRelationshipCommand deleteRelationshipCommand =
                new DeleteRelationshipCommand(testOriginUuid, testTargetUuid, relationshipDescriptor);
        expectedModel.deleteRelationType(relationshipDescriptor);
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void parse_invalidFamilialRelationshipDescriptor_throwsParseException() {
        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();
        String userInput = "family";
        Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Expected parse method to throw ParseException for 'family' relationship descriptor");
    }

    @Test
    void parse_invalidFamilialRelationshipDescriptorCaseInsensitive_throwsParseException() {
        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();
        String userInput = "Family";
        Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Expected parse method to throw ParseException for 'Family' relationship descriptor");
    }

    @Test
    void execute_sameOriginAndTargetUuidsButNotTestUuids_throwsCommandException() {
        String userInput = "/0001 /0001 /siblings";

        Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    void parse_invalidPredefinedRelationshipDescriptor_throwsParseException() {
        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();
        String userInput = "/friend";
        ParseException exception = Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(exception.getMessage().contains(
                Messages.MESSAGE_INVALID_PREDEFINED_RELATIONSHIP_DESCRIPTOR));

        DeleteRelationshipCommandParser parser2 = new DeleteRelationshipCommandParser();
        String userInput2 = "/siblings";
        ParseException exception2 = Assertions.assertThrows(ParseException.class, () -> parser2.parse(userInput2));
        assertTrue(exception2.getMessage().contains(
                Messages.MESSAGE_INVALID_PREDEFINED_RELATIONSHIP_DESCRIPTOR));

        DeleteRelationshipCommandParser parser3 = new DeleteRelationshipCommandParser();
        String userInput3 = "/bioparents";
        ParseException exception3 = Assertions.assertThrows(ParseException.class, () -> parser3.parse(userInput3));
        assertTrue(exception3.getMessage().contains(
                Messages.MESSAGE_INVALID_PREDEFINED_RELATIONSHIP_DESCRIPTOR));

        DeleteRelationshipCommandParser parser4 = new DeleteRelationshipCommandParser();
        String userInput4 = "/spouses";
        ParseException exception4 = Assertions.assertThrows(ParseException.class, () -> parser4.parse(userInput4));
        assertTrue(exception4.getMessage().contains(
                Messages.MESSAGE_INVALID_PREDEFINED_RELATIONSHIP_DESCRIPTOR));
    }

    @Test
    void parse_invalidFamilialRelationshipDescriptors_throwsParseException() {
        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();
        String userInput = "/1233 /1234 /family";
        ParseException exception = Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(exception.getMessage().contains("Please specify the type of familial "
                + "relationship instead of 'Family'.\n"
                + " Valid familial relations are: [bioParents, siblings, spouses]"));
    }

    @Test
    void parseInvalidFamilialRelationshipDescriptorsRoleless_throwsParseException() {
        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();
        String userInput = "/family";
        ParseException exception = Assertions.assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertTrue(exception.getMessage().contains("Please specify the type of familial "
                + "relationship instead of 'Family'.\n"
                + " Valid familial relations are: [bioParents, siblings, spouses]"));
    }

    @Test
    public void relationKeysAndValuesRetrieveKeyAtIndex_success() {
        LinkedHashMap<String, String> relationshipMap = new LinkedHashMap<>();
        relationshipMap.put("uuid1", "role1");
        relationshipMap.put("uuid2", "role2");

        DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();

        // Test retrieving the key at a valid index
        assertEquals("uuid1", ParserUtil.relationKeysAndValues(relationshipMap, 0, false));
        assertEquals("uuid2", ParserUtil.relationKeysAndValues(relationshipMap, 1, false));
    }

    @Test
    public void relationKeysAndValuesRetrieveValueAtIndex_success() {
        LinkedHashMap<String, String> relationshipMap = new LinkedHashMap<>();
        relationshipMap.put("uuid1", "role1");
        relationshipMap.put("uuid2", "role2");

        // Test retrieving the value at a valid index
        assertEquals("role1", ParserUtil.relationKeysAndValues(relationshipMap, 0, true));
        assertEquals("role2", ParserUtil.relationKeysAndValues(relationshipMap, 1, true));
    }

    @Test
    void parse_validInputDeleteRelationTypeRoleless_success() {
        String relationshipDescriptor = "housemates";
        UUID person1Uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID person2Uuid = UUID.fromString("00000000-0000-0000-0000-000000000004");
        expectedModel.addRelationship(new Relationship(person1Uuid, person2Uuid, relationshipDescriptor));
        expectedModel.deleteRelationship(new Relationship(person1Uuid, person2Uuid, relationshipDescriptor));
        String expectedMessage = "Delete successful";
        DeleteRelationshipCommand deleteRelationshipCommand =
                new DeleteRelationshipCommand(relationshipDescriptor, true);
        assertCommandSuccess(deleteRelationshipCommand, model, expectedMessage, expectedModel);
    }
}
