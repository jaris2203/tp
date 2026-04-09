package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonHasBoxPredicateTest {

    @Test
    public void test_personWithMatchingBox_returnsTrue() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-1"));
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personWithNoMatchingBox_returnsFalse() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-2"));
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_partialKeywordMatch_returnsTrue() {
        // "box" is a substring of "box-1"
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box"));
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_caseInsensitiveMatch_returnsTrue() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("BOX-1"));
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_emptyKeywords_returnsFalse() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Collections.emptyList());
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_multipleKeywords_matchesAny() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-2", "box-1"));
        Person person = new PersonBuilder().withBoxes("box-1:2").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords = Arrays.asList("box-1", "box-2");
        PersonHasBoxPredicate a = new PersonHasBoxPredicate(keywords);
        PersonHasBoxPredicate b = new PersonHasBoxPredicate(keywords);
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        PersonHasBoxPredicate a = new PersonHasBoxPredicate(Arrays.asList("box-1"));
        PersonHasBoxPredicate b = new PersonHasBoxPredicate(Arrays.asList("box-2"));
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-1"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_null_returnsFalse() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-1"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void toString_containsKeywords() {
        PersonHasBoxPredicate predicate = new PersonHasBoxPredicate(Arrays.asList("box-1"));
        assertTrue(predicate.toString().contains("box-1"));
    }
}
