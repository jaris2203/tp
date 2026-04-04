package seedu.address.model.delivery;

import java.util.Map;

/**
 * Ranks Singapore districts from west to east for geographical sorting.
 * Lower rank indicates more west, higher rank indicates more east.
 */
public class DistrictRanker {

    private static final Map<Integer, Integer> DISTRICT_RANK = Map.ofEntries(
            // west
            Map.entry(22, 0), // boon lay, jurong, tuas
            Map.entry(24, 1), // lim chu kang, tengah
            Map.entry(23, 2), // hillview, bukit panjang, choa chu kang
            Map.entry(21, 3), // clementi park, upper bukit timah
            Map.entry(5, 4), // buona vista, west coast, clementi
            Map.entry(3, 5), // alexandra, queenstown, tiong bahru
            Map.entry(4, 6), // harbourfront, telok blangah
            Map.entry(10, 7), // tanglin, holland, bukit timah

            // central
            Map.entry(9, 8), // orchard, river valley
            Map.entry(2, 9), // chinatown, tanjong pagar
            Map.entry(1, 10), // raffles place, marina, cbd
            Map.entry(6, 11), // city hall, clarke quay
            Map.entry(11, 12), // newton, novena
            Map.entry(7, 13), // bugis, beach road, rochor
            Map.entry(8, 14), // farrer park, little india
            Map.entry(12, 15), // balestier, toa payoh
            Map.entry(13, 16), // macpherson, potong pasir

            // north
            Map.entry(25, 17), // admiralty, woodlands
            Map.entry(26, 18), // upper thomson, mandai
            Map.entry(27, 19), // sembawang, yishun
            Map.entry(20, 20), // ang mo kio, bishan, thomson
            Map.entry(28, 21), // seletar, yio chu kang
            Map.entry(19, 22), // serangoon gardens, hougang, punggol

            // east
            Map.entry(14, 23), // geylang, eunos, paya lebar
            Map.entry(15, 24), // east coast, katong, marine parade
            Map.entry(16, 25), // bedok, upper east coast
            Map.entry(18, 26), // pasir ris, tampines
            Map.entry(17, 27) // changi, loyang
    );

    /**
     * Returns the rank of a district from west to east.
     * Lower rank indicates more west, higher rank indicates more east.
     *
     * @param district The district number (1-29)
     * @return The geographical rank, or Integer.MAX_VALUE if district not found
     */
    public static int getRank(int district) {
        return DISTRICT_RANK.getOrDefault(district, Integer.MAX_VALUE);
    }
}
