package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DistrictMapperTest {

    @Test
    public void getDistrictFromPrefix_prefixOne_returnsDistrictOne() {
        assertEquals(1, DistrictMapper.getDistrictFromPrefix(1));
    }

    @Test
    public void getDistrictFromPrefix_prefixSix_returnsDistrictOne() {
        assertEquals(1, DistrictMapper.getDistrictFromPrefix(6));
    }

    @Test
    public void getDistrictFromPrefix_prefixSeven_returnsDistrictTwo() {
        assertEquals(2, DistrictMapper.getDistrictFromPrefix(7));
    }

    @Test
    public void getDistrictFromPrefix_prefixEight_returnsDistrictTwo() {
        assertEquals(2, DistrictMapper.getDistrictFromPrefix(8));
    }

    @Test
    public void getDistrictFromPrefix_prefixEightyOne_returnsDistrictSeventeen() {
        assertEquals(17, DistrictMapper.getDistrictFromPrefix(81));
    }

    @Test
    public void getDistrictFromPrefix_prefixEightyTwo_returnsDistrictNineteen() {
        assertEquals(19, DistrictMapper.getDistrictFromPrefix(82));
    }

    @Test
    public void getDistrictFromPrefix_prefixSeventyNine_returnsDistrictTwentyEight() {
        assertEquals(28, DistrictMapper.getDistrictFromPrefix(79));
    }

    @Test
    public void getDistrictFromPrefix_prefixEighty_returnsDistrictTwentyEight() {
        assertEquals(28, DistrictMapper.getDistrictFromPrefix(80));
    }

    @Test
    public void getDistrictFromPrefix_invalidPrefix_returnsNegativeOne() {
        assertEquals(-1, DistrictMapper.getDistrictFromPrefix(99));
    }

    @Test
    public void getDistrictFromPrefix_zeroPrefix_returnsNegativeOne() {
        assertEquals(-1, DistrictMapper.getDistrictFromPrefix(0));
    }

    @Test
    public void getDistrictFromPrefix_negativePrefix_returnsNegativeOne() {
        assertEquals(-1, DistrictMapper.getDistrictFromPrefix(-1));
    }

    @Test
    public void getDistrictFromPrefix_prefix83_returnsNegativeOne() {
        // 83 is just above the valid maximum of 82
        assertEquals(-1, DistrictMapper.getDistrictFromPrefix(83));
    }

    @Test
    public void getDistrictFromPrefix_prefix82_returnsNineteen() {
        // 82 is the maximum valid prefix
        assertEquals(19, DistrictMapper.getDistrictFromPrefix(82));
    }

    @Test
    public void getDistrictFromPrefix_prefix01_returnsOne() {
        // 1 is the minimum valid prefix
        assertEquals(1, DistrictMapper.getDistrictFromPrefix(1));
    }
}
