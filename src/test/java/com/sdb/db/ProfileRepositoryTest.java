package com.sdb.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void checkIfAddressCanBeAddedAfterProfileInsertion() {
        // given
        Student student = new Student(null, "White", LocalDate.now(), 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.of(1980, Month.JANUARY, 1), 'M', student, null);
        Long id = profileRepository.save(profile).getId();

        // when
        Optional<Profile> optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());
        Profile savedProfile = optional.get();
        Address address = new Address(null, "123 Main St.", null, "New York", "New York", "1234");
        savedProfile.setAddress(address);
        profileRepository.save(savedProfile);

        // then
        optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());
        savedProfile = optional.get();
        profile.setAddress(address);

        assertEquals(profile, savedProfile);
    }

    @Test
    void checkDataInsertMultipleProfiles() {
        // given
        Address address1 = new Address(null, "123 Main St.", null, "New York", "NY", "1234");
        Student student1 = new Student(null, "Yellow", LocalDate.of(1980, Month.DECEMBER, 25), 200, 100);
        Profile profile1 = new Profile("John", "Doe", "Lil jon jon", LocalDate.now(), 'M', student1, address1);

        Address address2 = new Address(null, "92 Bel-Air", null, "Los Angeles", "CA", "4321");
        Student student2 = new Student(null, "Green", LocalDate.now(), 37, 420);

        Profile profile2 = new Profile("Jane", "Doe", "xylophone", LocalDate.now(), 'F', student2, address2);

        // when
        Long id1 = profileRepository.save(profile1).getId();
        Long id2 = profileRepository.save(profile2).getId();

        Optional<Profile> optional1 = profileRepository.findById(id1);
        Optional<Profile> optional2 = profileRepository.findById(id2);

        assertTrue(optional1.isPresent());
        assertTrue(optional2.isPresent());

        Profile savedProfile1 = optional1.get();
        Profile savedProfile2 = optional2.get();

        // then
        assertAll(
                () -> assertEquals(profile1, savedProfile1),
                () -> assertEquals(profile2, savedProfile2)
        );
    }

    @Test
    void checkOneProfileInsertionWithAddress() {
        // given
        Address address = new Address(null, "123 Main St.", null, "New York", "New York", "1234");
        Student student = new Student(null, "White", LocalDate.now(), 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.of(1980, Month.JANUARY, 1), 'M', student, address);

        // when
        Long id = profileRepository.save(profile).getId();
        Optional<Profile> savedOptionalProfile = profileRepository.findById(id);

        if (savedOptionalProfile.isEmpty()) fail();
        Profile savedProfile = savedOptionalProfile.get();
        Address savedAddress = savedProfile.getAddress();

        // then
        assertEquals(address, savedAddress);
    }

    @Test
    void checkIfEmptyAddressTableReturnsEmptyList() {
        // given
        Student student = new Student(null, "White", LocalDate.now(), 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student, null);

        // when
        Long id = profileRepository.save(profile).getId();
        List<Address> addresses = profileRepository.findByID(id);

        // then
        assertTrue(addresses.isEmpty());
    }

    // This test accounts for both Profile and Student data.
    @Test
    void checkInsertionOfBothProfileStudent() {
        // given
        Student student = new Student(null, "White", LocalDate.now(), 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.of(1980, Month.JANUARY, 1), 'M', student, null);

        // when
        Long id = profileRepository.save(profile).getId();
        Optional<Profile> optional = profileRepository.findById(id);

        // then
        assertTrue(optional.isPresent());
        Profile savedProfile = optional.get();
        assertEquals(profile, savedProfile);
    }
}
