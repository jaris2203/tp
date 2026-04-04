package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import org.junit.jupiter.api.Test;

public class GeographicalComparatorTest {

    private final GeographicalComparator comparator = new GeographicalComparator();

    @Test
    public void compare_westPersonVsEastPerson_returnsNegative() {
        // ELLE (D22, rank 0) vs BENSON (D19, rank 22)
        assertTrue(comparator.compare(ELLE, BENSON) < 0);
    }

    @Test
    public void compare_eastPersonVsWestPerson_returnsPositive() {
        // BENSON (D19, rank 22) vs ELLE (D22, rank 0)
        assertTrue(comparator.compare(BENSON, ELLE) > 0);
    }

    @Test
    public void compare_sameObject_returnsZero() {
        assertEquals(0, comparator.compare(ALICE, ALICE));
    }

    @Test
    public void compare_sameDistrict_returnsZero() {
        // CARL (D11, rank 5) vs ALICE (D12, rank 5)
        assertEquals(0, comparator.compare(CARL, ALICE));
    }
}
