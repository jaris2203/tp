package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.DeliveryStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_NUM_OF_MONTHS = "Number of months should be an integer.";

    private static Clock clock = Clock.systemDefaultZone();

    public static void setClock(Clock newClock) {
        clock = newClock;
    }

    public static void resetClock() {
        clock = Clock.systemDefaultZone();
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!isValidIndex(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    public static boolean isValidIndex(String str) {
        return StringUtil.isNonZeroUnsignedInteger(str);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.getValidationMessage(trimmedAddress));
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String expiryDate} into an {@code ExpiryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expiryDate} is invalid.
     */
    public static ExpiryDate parseExpiryDate(String numOfMonthsString) throws ParseException {
        try {
            int numOfMonths = Integer.parseInt(numOfMonthsString.trim());
            LocalDate expiry = LocalDate.now(clock)
                    .plusMonths(numOfMonths)
                    .with(TemporalAdjusters.lastDayOfMonth());
            return new ExpiryDate(expiry.format(ExpiryDate.STANDARD_FORMAT));
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_NUM_OF_MONTHS);
        }
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String Remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String deliveryStatus} into a {@code DeliveryStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deliveryStatus} is invalid.
     */
    public static DeliveryStatus parseDeliveryStatus(String deliveryStatus) throws ParseException {
        requireNonNull(deliveryStatus);
        String trimmedDeliveryStatus = deliveryStatus.trim();

        try {
            return DeliveryStatus.fromString(trimmedDeliveryStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(DeliveryStatus.MESSAGE_CONSTRAINTS);
        }
    }
    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String box} into a {@code String} for name-only parsing.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code box} is invalid.
     */
    public static String parseBoxName(String box) throws ParseException {
        requireNonNull(box);
        String trimmedBox = box.trim();
        if (!Box.isValidBoxName(trimmedBox)) {
            throw new ParseException(Box.MESSAGE_CONSTRAINTS);
        }
        return trimmedBox;
    }

    /**
     * Parses {@code String boxWithExpiry} into a {@code Box}
     */
    public static Box parseBoxWithNumOfMonths(String boxWithMonths) throws ParseException {
        requireNonNull(boxWithMonths);
        String trimmed = boxWithMonths.trim();

        int separatorIndex = trimmed.lastIndexOf(":");
        if (separatorIndex <= 0 || separatorIndex == trimmed.length() - 1) {
            throw new ParseException(Box.MESSAGE_INVALID_BOX_WITH_EXPIRY_FORMAT);
        }

        String boxName = trimmed.substring(0, separatorIndex).trim();
        String numOfMonthsString = trimmed.substring(separatorIndex + 1).trim();

        if (!Box.isValidBoxName(boxName)) {
            throw new ParseException(Box.MESSAGE_CONSTRAINTS);
        }

        ExpiryDate expiryDate = parseExpiryDate(numOfMonthsString);
        return new Box(boxName, expiryDate);
    }

    /**
     * Parses {@code Collection<String> boxes} into a {@code Set<Box>}
     */
    public static Set<Box> parseBoxesWithNumOfMonths(Collection<String> boxesWithNumOfMonths) throws ParseException {
        requireNonNull(boxesWithNumOfMonths);
        final Set<Box> boxSet = new TreeSet<>();
        for (String boxWithNumOfMonths: boxesWithNumOfMonths) {
            boxSet.add(parseBoxWithNumOfMonths(boxWithNumOfMonths));
        }
        return boxSet;
    }
}
