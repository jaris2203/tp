package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("#friend")); // hash symbol not allowed
        assertFalse(Tag.isValidTagName("tag-1")); // hyphen not allowed
        assertFalse(Tag.isValidTagName("tag!")); // exclamation mark not allowed
        assertFalse(Tag.isValidTagName("@home")); // @ not allowed

        // valid tag names — VALIDATION_REGEX = "[\\p{Alnum} :]+"
        assertTrue(Tag.isValidTagName("friends")); // alphanumeric only
        assertTrue(Tag.isValidTagName("1234")); // digits only
        assertTrue(Tag.isValidTagName("hello world")); // space allowed
        assertTrue(Tag.isValidTagName("food:drinks")); // colon allowed
        assertTrue(Tag.isValidTagName("A")); // single character
        assertTrue(Tag.isValidTagName("  spaces")); // leading spaces allowed (unlike Name)
        assertTrue(Tag.isValidTagName("category: food")); // colon with space
    }

    @Test
    public void equals_sameTagName_returnsTrue() {
        Tag tag = new Tag("friends");
        assertTrue(tag.equals(new Tag("friends")));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Tag tag = new Tag("friends");
        assertTrue(tag.equals(tag));
    }

    @Test
    public void equals_differentTagName_returnsFalse() {
        Tag tag = new Tag("friends");
        assertFalse(tag.equals(new Tag("family")));
    }

    @Test
    public void equals_null_returnsFalse() {
        Tag tag = new Tag("friends");
        assertFalse(tag.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Tag tag = new Tag("friends");
        assertFalse(tag.equals("friends"));
    }

    @Test
    public void hashCode_equalTags_sameHash() {
        Tag t1 = new Tag("friends");
        Tag t2 = new Tag("friends");
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void hashCode_differentTags_differentHash() {
        Tag t1 = new Tag("friends");
        Tag t2 = new Tag("family");
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void toString_returnsTagNameInBrackets() {
        Tag tag = new Tag("friends");
        assertEquals("[friends]", tag.toString());
    }

    @Test
    public void toString_tagWithColon_returnsCorrectFormat() {
        Tag tag = new Tag("food:drinks");
        assertEquals("[food:drinks]", tag.toString());
    }

}
