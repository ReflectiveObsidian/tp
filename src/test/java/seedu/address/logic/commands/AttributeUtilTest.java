package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.attribute.Attribute;

public class AttributeUtilTest {

    @Test
    public void createAttribute_validNameAttribute_returnsNameAttribute() throws CommandException {
        Attribute actualAttribute = AttributeUtil.createAttribute("Name", "John Doe");
        assertTrue(actualAttribute.getName().toString().equals("Name"));
    }

    @Test
    public void createAttribute_validPhoneAttribute_returnsPhoneAttribute() throws CommandException {
        Attribute actualAttribute = AttributeUtil.createAttribute("phone", "12345678");
        assertTrue(actualAttribute.getName().toString().equals("Phone"));
    }

    @Test
    public void createAttribute_validBirthdayAttribute_returnsBirthdayAttribute() throws CommandException {
        Attribute actualAttribute = AttributeUtil.createAttribute("Birthday", "2000-01-01");
        assertTrue(actualAttribute.getName().toString().equals("Birthday"));

    }

    @Test
    public void createAttribute_invalidPhoneAttribute_throwsCommandException() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("phone", "not a number"));
    }

    @Test
    public void createAttribute_invalidBirthdayAttribute_throwsCommandException() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("birthday", "not a date"));
    }
    @Test
    public void capitalizeAttributeName_generalName_returnsCapitalizedName() {
        String actualName = AttributeUtil.capitalizeAttributeName("general");
        assertTrue(actualName.equals("General"));
    }
    @Test
    public void capitalizeAttributeName_emptyName_returnsEmptyString() {
        String actualName = AttributeUtil.capitalizeAttributeName("");
        assertTrue(actualName.equals(""));
    }
    @Test
    public void capitalizeAttributeName_singleLetterName_returnsCapitalizedLetter() {
        String actualName = AttributeUtil.capitalizeAttributeName("a");
        assertTrue(actualName.equals("A"));
    }
    @Test
    public void capitalizeAttributeName_numberName_returnsNull() {
        String actualName = AttributeUtil.capitalizeAttributeName("1");
        assertTrue(actualName.equals("1"));
    }

    @Test
    public void birthday_invalidBirthday_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("Birthday",
                "2032-01-01"));
    }

    @Test
    public void sex_attributeValueEmpty_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("Sex", ""));
    }

    @Test
    public void sex_invalidSex_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("Sex", "invalid"));
    }

    @Test
    public void sex_validSex_male() throws CommandException {
        Attribute male = AttributeUtil.createAttribute("Sex", "male");
        assertEquals("MALE", male.getValueAsString());
    }

    @Test
    public void sex_validSex_female() throws CommandException {
        Attribute male = AttributeUtil.createAttribute("Sex", "female");
        assertEquals("FEMALE", male.getValueAsString());
    }

    @Test
    public void sex_attributeValueNull_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("Sex", null));
    }

    @Test
    public void phone_invalidPhone_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("phone", "-1"));
    }

    @Test
    public void phone_invalidPhoneLength_throwsError() {
        assertThrows(CommandException.class, () -> AttributeUtil.createAttribute("phone", "123456789"));
    }
}
