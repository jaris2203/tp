package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ClusterAssignerTest {

    @Test
    public void groupIntoClusters_validInput_returnsCorrectNumberOfGroups() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 3);

        assertEquals(3, clusters.size());
    }

    @Test
    public void groupIntoClusters_evenDistribution_balancesCorrectly() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 3);

        // 7 person -> 3 groups: 3, 2, 2
        assertEquals(3, clusters.get(0).size());
        assertEquals(2, clusters.get(1).size());
        assertEquals(2, clusters.get(2).size());
    }

    @Test
    public void groupIntoClusters_sortsByPostalPrefixAscending() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 1);
        List<Person> groupedPersons = clusters.get(0);

        assertEquals(1, groupedPersons.get(0).getAddress().getPostalPrefixFromAddress());
        assertEquals(11, groupedPersons.get(1).getAddress().getPostalPrefixFromAddress());
        assertEquals(12, groupedPersons.get(2).getAddress().getPostalPrefixFromAddress());
        assertEquals(54, groupedPersons.get(3).getAddress().getPostalPrefixFromAddress());
        assertEquals(64, groupedPersons.get(4).getAddress().getPostalPrefixFromAddress());
        assertEquals(65, groupedPersons.get(5).getAddress().getPostalPrefixFromAddress());
        assertEquals(74, groupedPersons.get(6).getAddress().getPostalPrefixFromAddress());

    }

    @Test
    public void groupIntoClusters_nullList_returnsEmptyList() {
        List<List<Person>> result = ClusterAssigner.groupIntoClusters(null, 3);
        assertTrue(result.isEmpty());
    }

    @Test
    public void groupIntoClusters_emptyList_returnsEmptyList() {
        List<List<Person>> result = ClusterAssigner.groupIntoClusters(new ArrayList<>(), 3);
        assertTrue(result.isEmpty());
    }

    @Test
    public void groupIntoClusters_zeroDrivers_throwsException() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        assertThrows(IllegalArgumentException.class, () ->
                ClusterAssigner.groupIntoClusters(persons, 0));
    }

    @Test
    public void groupIntoClusters_negativeDrivers_throwsException() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        assertThrows(IllegalArgumentException.class, () ->
                ClusterAssigner.groupIntoClusters(persons, -1));
    }

    @Test
    public void groupIntoClusters_moreDriversThanPersons_createsEmptyGroups() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 8);

        assertEquals(8, clusters.size());
        assertEquals(1, clusters.get(0).size());
        assertEquals(1, clusters.get(1).size());
        assertEquals(1, clusters.get(2).size());
        assertEquals(1, clusters.get(3).size());
        assertEquals(1, clusters.get(4).size());
        assertEquals(1, clusters.get(5).size());
        assertEquals(1, clusters.get(6).size());
        assertTrue(clusters.get(7).isEmpty());
    }

    @Test
    public void groupIntoClusters_singleDriver_allPersonsInOneGroup() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 1);

        assertEquals(1, clusters.size());
        assertEquals(persons.size(), clusters.get(0).size());
    }

}
