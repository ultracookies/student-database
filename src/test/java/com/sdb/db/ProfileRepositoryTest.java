package com.sdb.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJdbcTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void checkDataInsertMultipleProfiles() {
        // given
        Address address1 = new Address(null, "123 Main St.", null, "New York", "NY", "1234");
        Student student1 = new Student("Yellow", LocalDate.of(1980, Month.DECEMBER, 25), 150, 150);
        Profile profile1 = new Profile("John", "Doe", "Lil jon jon", LocalDate.now(), 'M', student1, address1);

        Address address2 = new Address(null, "92 Bel-Air", null, "Los Angeles", "CA", "4321");
        Student student2 = new Student("Green", LocalDate.now(), 37, 420);
        Profile profile2 = new Profile("Jane", "Doe", "xylophone", LocalDate.MIN, 'F', student2, address2);

        // when
        profileRepository.save(profile1);
        profileRepository.save(profile2);

        Integer totalAddresses = profileRepository.countAddresses();

        // then
        Assertions.assertEquals(2, totalAddresses);

    }

    @Test
    void checkDataInsertionAddress() {
        // given
        Address address = new Address(null, "123 Main St.", null, "New York", "New York", "1234");
        Student student = new Student("White", LocalDate.MIN, 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student, address);

        // when
        Long id = profileRepository.save(profile).getId();
        Integer addressCount = profileRepository.countAddresses();

        // then
        Assertions.assertEquals(1, addressCount);

        Optional<Profile> savedOptionalProfile = profileRepository.findById(id);

        if (savedOptionalProfile.isEmpty()) Assertions.fail();

        Profile savedProfile = savedOptionalProfile.get();
        Address savedAddress = savedProfile.getAddress();

        Assertions.assertEquals(address.getAddressLine1(), savedAddress.getAddressLine1());
    }

    @Test
    void checkRowCountOfAddressTable() {
        // given
        Student student = new Student("White", LocalDate.MIN, 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student, null);

        // when
        profileRepository.save(profile);

        // then
        Integer addressCount = profileRepository.countAddresses();
        Assertions.assertEquals(0, addressCount);
    }

    @Test
    void checkIfEmptyAddressTableReturnsRow() {
        // given
        Student student = new Student("White", LocalDate.MIN, 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student, null);

        // when
        Long id = profileRepository.save(profile).getId();

        // then
        List<Address> addresses = profileRepository.findByID(id);

        if (addresses.size() != 0) Assertions.fail();
    }

    @Test
    void checkInsertionOfBothProfileStudent() {
        // given
        Student student = new Student("White", LocalDate.MIN, 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student, null);

        // when
        Long id = profileRepository.save(profile).getId();

        // then
        Optional<Profile> savedOptionalProfile = profileRepository.findById(id);

        if (savedOptionalProfile.isEmpty()) Assertions.fail();

        Profile savedProfile = savedOptionalProfile.get();
        Student savedStudent = savedProfile.getStudent();

        Assertions.assertEquals(student.getRank(), savedStudent.getRank());
    }
}
