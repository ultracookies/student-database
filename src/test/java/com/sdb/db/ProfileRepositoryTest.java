package com.sdb.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void checkInsertionOfBothProfileStudent() {
        // given
        Student student = new Student("White", LocalDate.MIN, 200, 100);
        Profile profile = new Profile("John", "Doe", "Johnny", LocalDate.MAX, 'M', student);

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
