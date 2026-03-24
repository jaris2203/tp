package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Box} matches any of the keywords given.
 */
public class PersonHasBoxPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonHasBoxPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getBoxes().stream()
                .map(Box::getBoxName)
                .anyMatch(boxName -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(boxName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasBoxPredicate)) {
            return false;
        }

        PersonHasBoxPredicate otherPersonHasBoxPredicate = (PersonHasBoxPredicate) other;
        return keywords.equals(otherPersonHasBoxPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
