package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class ClusterAssignerTest {

    @Test
    public void getPostalCodeStringFromAddress_validAddress_returnsPostalCode() {
        String address = "Blk 123 Sengkang Street 11, Singapore 123456";
        assertEquals("123456", ClusterAssigner.getPostalCodeStringFromAddress(address));
    }

    @Test
    public void getPostalCodeStringFromAddress_addressWithoutPostalCode_throwsException() {
        String address = "Blk 123, Singapore";
        assertThrows(IllegalStateException.class, () ->
                ClusterAssigner.getPostalCodeStringFromAddress(address));
    }

    @Test
    public void getPostalCodeStringFromAddress_nullAddress_throwsException() {
        assertThrows(NullPointerException.class, () ->
                ClusterAssigner.getPostalCodeStringFromAddress(null));
    }

    @Test
    public void getPostalCodeIntegerFromAddress_validAddress_returnsPostalCodeAsInt() {
        String address = "Blk 123, Singapore 123456";
        assertEquals(123456, ClusterAssigner.getPostalCodeIntegerFromAddress(address));
    }

    @Test
    public void getPostalCodeIntegerFromAddress_addressWithoutPostalCode_throwsException() {
        String address = "Blk 123, Singapore";
        assertThrows(IllegalStateException.class, () ->
                ClusterAssigner.getPostalCodeIntegerFromAddress(address));
    }

    @Test
    public void getPostalCodeIntegerFromAddress_nullAddress_throwsException() {
        assertThrows(NullPointerException.class, () ->
                ClusterAssigner.getPostalCodeIntegerFromAddress(null));
    }

    @Test
    public void getPostalPrefixFromAddress_validAddress_returnsPrefix() {
        String address = "Blk 123, Singapore 123456";
        assertEquals(12, ClusterAssigner.getPostalPrefixFromAddress(address));
    }

    @Test
    public void getPostalPrefixFromAddress_postalCodeWithLeadingZeros_returnsPrefix() {
        String address = "Blk 123, Singapore 012345";
        assertEquals(1, ClusterAssigner.getPostalPrefixFromAddress(address));
    }

    @Test
    public void getPostalPrefixFromAddress_shortPostalCode_throwsException() {
        String address = "Blk 123, Singapore 12345";
        assertThrows(IllegalStateException.class, () ->
                ClusterAssigner.getPostalPrefixFromAddress(address));
    }

    @Test
    public void getPostalPrefixFromPerson_typicalPerson_returnsPrefix() {
        assertEquals(12, ClusterAssigner.getPostalPrefixFromPerson(TypicalPersons.ALICE));

        assertEquals(54, ClusterAssigner.getPostalPrefixFromPerson(TypicalPersons.BENSON));

        assertEquals(11, ClusterAssigner.getPostalPrefixFromPerson(TypicalPersons.CARL));
    }

    @Test
    public void getPostalPrefixFromPerson_nullPerson_throwsException() {
        assertThrows(NullPointerException.class, () ->
                ClusterAssigner.getPostalPrefixFromPerson(null));
    }

    @Test
    public void getPostalPrefixFromPerson_personWithInvalidAddress_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Person person = new PersonBuilder().withAddress("Sengkang").build();
            ClusterAssigner.getPostalPrefixFromPerson(person);
        });
    }

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

        assertEquals(1, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(0)));
        assertEquals(11, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(1)));
        assertEquals(12, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(2)));
        assertEquals(54, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(3)));
        assertEquals(64, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(4)));
        assertEquals(65, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(5)));
        assertEquals(74, ClusterAssigner.getPostalPrefixFromPerson(groupedPersons.get(6)));

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
