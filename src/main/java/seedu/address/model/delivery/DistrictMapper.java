package seedu.address.model.delivery;

/**
 * Maps postal code prefixes to their corresponding district numbers.
 */
public class DistrictMapper {

    public static int getDistrictFromPrefix(int prefix) {
        return switch (prefix) {
        case 1, 2, 3, 4, 5, 6 -> 1;
        case 7, 8 -> 2;
        case 9, 10 -> 4;
        case 11, 12, 13 -> 5;
        case 14, 15, 16 -> 3;
        case 17 -> 6;
        case 18, 19 -> 7;
        case 20, 21 -> 8;
        case 22, 23 -> 9;
        case 24, 25, 26, 27 -> 10;
        case 28, 29, 30 -> 11;
        case 31, 32, 33 -> 12;
        case 34, 35, 36, 37 -> 13;
        case 38, 39, 40, 41 -> 14;
        case 42, 43, 44, 45 -> 15;
        case 46, 47, 48 -> 16;
        case 49, 50, 81 -> 17;
        case 51, 52 -> 18;
        case 53, 54, 55, 82 -> 19;
        case 56, 57 -> 20;
        case 58, 59 -> 21;
        case 60, 61, 62, 63, 64 -> 22;
        case 65, 66, 67, 68 -> 23;
        case 69, 70, 71 -> 24;
        case 72, 73 -> 25;
        case 74, 75, 76 -> 27;
        case 77, 78 -> 26;
        case 79, 80 -> 28;

        default -> -1; // unknown
        };
    }
}
