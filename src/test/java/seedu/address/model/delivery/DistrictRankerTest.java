package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DistrictRankerTest {

    @Test
    public void getRank_districtTwentyTwo_returnsZero() {
        assertEquals(0, DistrictRanker.getRank(22));
    }

    @Test
    public void getRank_districtTwentyFour_returnsOne() {
        assertEquals(1, DistrictRanker.getRank(24));
    }

    @Test
    public void getRank_districtOne_returnsTen() {
        assertEquals(10, DistrictRanker.getRank(1));
    }

    @Test
    public void getRank_districtNine_returnsEight() {
        assertEquals(8, DistrictRanker.getRank(9));
    }

    @Test
    public void getRank_districtNineteen_returnsTwentyTwo() {
        assertEquals(22, DistrictRanker.getRank(19));
    }

    @Test
    public void getRank_districtTwentyFive_returnsSeventeen() {
        assertEquals(17, DistrictRanker.getRank(25));
    }

    @Test
    public void getRank_districtSeventeen_returnsTwentySeven() {
        assertEquals(27, DistrictRanker.getRank(17));
    }

    @Test
    public void getRank_districtEighteen_returnsTwentySix() {
        assertEquals(26, DistrictRanker.getRank(18));
    }

    @Test
    public void getRank_westRankLowerThanEast() {
        assertTrue(DistrictRanker.getRank(22) < DistrictRanker.getRank(17));
    }

    @Test
    public void getRank_westRankLowerThanCentral() {
        assertTrue(DistrictRanker.getRank(22) < DistrictRanker.getRank(1));
    }

    @Test
    public void getRank_centralRankLowerThanEast() {
        assertTrue(DistrictRanker.getRank(1) < DistrictRanker.getRank(17));
    }

    @Test
    public void getRank_unknownDistrict_returnsMaxValue() {
        assertEquals(Integer.MAX_VALUE, DistrictRanker.getRank(-1));
    }

    @Test
    public void getRank_districtNinetyNine_returnsMaxValue() {
        assertEquals(Integer.MAX_VALUE, DistrictRanker.getRank(99));
    }

    @Test
    public void getRank_districtZero_returnsMaxValue() {
        // 0 is not a valid district
        assertEquals(Integer.MAX_VALUE, DistrictRanker.getRank(0));
    }

    @Test
    public void getRank_districtTwentyEight_returnsFiniteRank() {
        // District 28 is valid (North-East); rank should not be MAX_VALUE
        assertTrue(DistrictRanker.getRank(28) < Integer.MAX_VALUE);
    }

    @Test
    public void getRank_allKnownDistrictsReturnFiniteRank() {
        // Districts 1-28 are all valid Singapore districts
        for (int d = 1; d <= 28; d++) {
            assertTrue(DistrictRanker.getRank(d) < Integer.MAX_VALUE,
                    "Expected finite rank for district " + d);
        }
    }

    @Test
    public void getRank_unknownDistinctsReturnMaxValue() {
        // Districts outside 1-28 are invalid
        assertEquals(Integer.MAX_VALUE, DistrictRanker.getRank(29));
        assertEquals(Integer.MAX_VALUE, DistrictRanker.getRank(100));
    }
}
