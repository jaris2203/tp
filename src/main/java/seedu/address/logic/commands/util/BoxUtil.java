package seedu.address.logic.commands.util;

import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BoxUtil {

    public static Set<String> getBoxNames(Person person) {
        return person.getBoxes().stream()
                .map(Box::getBoxName)
                .collect(Collectors.toSet());
    }

    public static boolean hasMatchingBoxNames(Person person, Set<Box> boxesToAdd) {
        Set<String> existingBoxNames = getBoxNames(person);
        return boxesToAdd.stream()
                .map(Box::getBoxName)
                .anyMatch(existingBoxNames::contains);
    }

    public static Set<String> getNonExistentBoxNames(Person person, Set<String> targetBoxNames) {
        Set<String> existingBoxNames = getBoxNames(person);
        return targetBoxNames.stream()
                .filter(boxName -> !existingBoxNames.contains(boxName))
                .collect(Collectors.toSet());
    }

    public static Set<Box> addBoxes(Person person, Set<Box> boxesToAdd) {
        Set<Box> updatedBoxes = new TreeSet<>(person.getBoxes());
        updatedBoxes.addAll(boxesToAdd);
        return updatedBoxes;
    }

    public static Set<Box> removeBoxes(Person person, Set<String> targetBoxNames) {
        return person.getBoxes().stream()
                .filter(box -> !targetBoxNames.contains(box.boxName))
                .collect(Collectors.toSet());
    }
}
