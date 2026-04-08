package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

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
    public void groupIntoClusters_sortsByWestToEast() {
        List<Person> persons = new ArrayList<>(TypicalPersons.getTypicalPersons());
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 1);
        List<Person> groupedPersons = clusters.get(0);

        System.out.println("ALICE prefix: " + ALICE.getAddress().getPostalPrefixFromAddress());
        System.out.println("CARL prefix: " + CARL.getAddress().getPostalPrefixFromAddress());
        System.out.println("ALICE rank: " + DistrictRanker.getRank(DistrictMapper.getDistrictFromPrefix(12)));
        System.out.println("CARL rank: " + DistrictRanker.getRank(DistrictMapper.getDistrictFromPrefix(11)));

        // expected west to east: ELLE, FIONA, CARL, ALICE, DANIEL, GEORGE, BENSON
        assertEquals(ELLE, groupedPersons.get(0));
        assertEquals(FIONA, groupedPersons.get(1));
        assertEquals(ALICE, groupedPersons.get(2));
        assertEquals(CARL, groupedPersons.get(3));
        assertEquals(DANIEL, groupedPersons.get(4));
        assertEquals(GEORGE, groupedPersons.get(5));
        assertEquals(BENSON, groupedPersons.get(6));
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

    @Test
    public void groupIntoClusters_twoPersonsTwoDrivers_eachGetOne() {
        List<Person> persons = new ArrayList<>();
        persons.add(ALICE);
        persons.add(BENSON);
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 2);

        assertEquals(2, clusters.size());
        assertEquals(1, clusters.get(0).size());
        assertEquals(1, clusters.get(1).size());
    }

    @Test
    public void groupIntoClusters_threePersonsTwoDrivers_distributesTwoPlusOne() {
        // 3 persons, 2 drivers -> sizes should be 2 and 1
        List<Person> persons = new ArrayList<>();
        persons.add(ALICE);
        persons.add(BENSON);
        persons.add(CARL);
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 2);

        assertEquals(2, clusters.size());
        int total = clusters.get(0).size() + clusters.get(1).size();
        assertEquals(3, total); // all persons assigned
        assertTrue(clusters.get(0).size() >= clusters.get(1).size()); // larger group first
    }

    @Test
    public void groupIntoClusters_singlePersonSingleDriver_oneGroupOfOne() {
        List<Person> persons = new ArrayList<>();
        persons.add(ALICE);
        List<List<Person>> clusters = ClusterAssigner.groupIntoClusters(persons, 1);

        assertEquals(1, clusters.size());
        assertEquals(1, clusters.get(0).size());
    }

}
