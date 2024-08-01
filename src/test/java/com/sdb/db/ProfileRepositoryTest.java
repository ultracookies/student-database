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
    void checkIfPromotionIsInsertedAssociatedWithStudent() {
        // given
        Student student = new Student("White", LocalDate.now());

        Profile profile = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student);
        Long id = profileRepository.save(profile).getId();

        Optional<Profile> optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());

        Profile savedProfile = optional.get();
        Student savedStudent = savedProfile.getStudent();

        // when
        Promotion promotion = new Promotion(student.getRank(), "Yellow", LocalDate.now(), savedStudent.getId());
        savedStudent.addPromotion(promotion);
        student.addPromotion(promotion);

        // then
        assertEquals(student, savedStudent);
    }

    @Test
    void checkIfAddressCanBeAddedAfterProfileInsertion() {
        // given
        Student student = new Student("White", LocalDate.now());
        Profile profile = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student);
        Long id = profileRepository.save(profile).getId();

        // when
        Optional<Profile> optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());
        Profile savedProfile = optional.get();
        Address address = new Address("123 Main St.", "New York", "New York", "1234");
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
    void checkDataInsertMultipleProfilesAndExtraneousData() {
        // given
        Student student1 = new Student("Yellow", LocalDate.of(1980, Month.DECEMBER, 25));
        Profile profile1 = new Profile("John", "Doe", LocalDate.now(), 'M', student1);
        Address address1 = new Address("123 Main St.", "New York", "NY", "1234");
        profile1.setAddress(address1);
        profile1.getStudent().setWeight(200);
        profile1.getStudent().setHeight(100);

        Student student2 = new Student("Green", LocalDate.now());
        Profile profile2 = new Profile("Jane", "Doe", LocalDate.now(), 'F', student2);
        Address address2 = new Address("92 Bel-Air", "Los Angeles", "CA", "4321");
        address2.setAddressLine2("idk what goes in these fields tbh");
        profile2.setAddress(address2);

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
        Address address = new Address("123 Main St.", "New York", "New York", "1234");
        Student student = new Student("White", LocalDate.now());
        Profile profile = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student);
        profile.setAddress(address);
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
        Student student = new Student("White", LocalDate.now());
        Profile profile = new Profile("John", "Doe", LocalDate.MAX, 'M', student);

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
        Student student = new Student("White", LocalDate.now());
        Profile profile = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student);

        // when
        Long id = profileRepository.save(profile).getId();
        Optional<Profile> optional = profileRepository.findById(id);

        // then
        assertTrue(optional.isPresent());
        Profile savedProfile = optional.get();
        assertEquals(profile, savedProfile);
    }
}
