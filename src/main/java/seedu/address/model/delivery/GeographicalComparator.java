package seedu.address.model.delivery;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares persons by their geographical location from west to east.
 */
public class GeographicalComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        int rank1 = getGeographicalRank(p1);
        int rank2 = getGeographicalRank(p2);
        return Integer.compare(rank1, rank2);
    }

    private int getGeographicalRank(Person person) {
        int prefix = person.getAddress().getPostalPrefixFromAddress();
        int district = DistrictMapper.getDistrictFromPrefix(prefix);
        return DistrictRanker.getRank(district);
    }
}
