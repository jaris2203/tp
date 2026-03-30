package seedu.address.model.delivery;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Assigns persons to balanced clusters based on postal code prefixes.
 */
public class ClusterAssigner {

    /**
     * Groups a list of Persons into clusters based on their postal code prefixes and distributes them among a specified
     * number of drivers.
     *
     * @param list The list of Persons to be grouped.
     * @param numDrivers The number of drivers to distribute the clusters among.
     * @return A list of lists, where each inner list contains the Persons assigned to a driver.
     * @throws IllegalArgumentException if numDrivers is less than or equal to 0.
     */
    public static List<List<Person>> groupIntoClusters(List<Person> list, int numDrivers) {
        if (numDrivers <= 0) {
            throw new IllegalArgumentException("Number of drivers must be a positive integer.");
        }

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<Person> sortedList = new ArrayList<>(list);
        sortedList.sort((person1, person2) -> {
            int prefix1 = person1.getAddress().getPostalPrefixFromAddress();
            int prefix2 = person2.getAddress().getPostalPrefixFromAddress();
            return Integer.compare(prefix1, prefix2); // sort persons by postal prefix ascending
        });

        List<List<Person>> driverAssignments = new ArrayList<>();
        int numPersons = sortedList.size();
        int minPerDriver = numPersons / numDrivers;
        int remainder = numPersons % numDrivers;

        int startIndex = 0;
        for (int i = 0; i < numDrivers; i++) {
            int groupSize = minPerDriver + (i < remainder ? 1 : 0);
            List<Person> driverList = new ArrayList<>(
                    sortedList.subList(startIndex, startIndex + groupSize)
            );
            driverAssignments.add(driverList);
            startIndex += groupSize;
        }

        return driverAssignments;
    }
}
