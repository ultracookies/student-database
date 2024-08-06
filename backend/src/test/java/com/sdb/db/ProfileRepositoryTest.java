package com.sdb.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void checkIfMultiplePaymentsAreAdded() {
        // given
        Student student1 = new Student("Green", LocalDate.of(2022, Month.SEPTEMBER, 16));
        Profile profile1 = new Profile("John", "Doe",
                LocalDate.of(2000, Month.FEBRUARY, 27), 'M', student1);

        Payment payment1 = new Payment(320f, LocalDate.now());
        Payment payment2 = new Payment(420f, LocalDate.now());
        profile1.addPayment(payment1);

        // when
        Long id1 = profileRepository.save(profile1).getId();

        Optional<Profile> optional1 = profileRepository.findById(id1);

        assertTrue(optional1.isPresent());

        Profile savedProfile1 = optional1.get();
        savedProfile1.addPayment(payment2);
        profileRepository.save(savedProfile1);

        optional1 = profileRepository.findById(id1);
        assertTrue(optional1.isPresent());

        savedProfile1 = optional1.get();

        var iterator = savedProfile1.paymentIterator();

        Payment savedPayment1 = iterator.next();
        assertAll(
                () -> assertEquals(payment1.getAmount(), savedPayment1.getAmount()),
                () -> assertEquals(payment1.getDate(), savedPayment1.getDate()),
                () -> assertEquals(0, savedPayment1.getIndex())
        );

        var savedPayment2 = iterator.next();
        assertAll(
                () -> assertEquals(payment2.getAmount(), savedPayment2.getAmount()),
                () -> assertEquals(payment2.getDate(), savedPayment2.getDate()),
                () -> assertEquals(1, savedPayment2.getIndex())
        );
    }

    @Test
    void checkIfPaymentIsAdded() {
        // given
        Student student = new Student("Green", LocalDate.of(2002, Month.SEPTEMBER, 16));
        Profile profile = new Profile("John", "Doe",
                LocalDate.of(2000, Month.FEBRUARY, 27), 'M', student);

        Payment payment = new Payment(120f, LocalDate.now());
        profile.addPayment(payment);
        Long id = profileRepository.save(profile).getId();

        // when
        Optional<Profile> optional = profileRepository.findById(id);

        // then
        assertTrue(optional.isPresent());
        Payment savedPayment = optional.get().paymentIterator().next();

        assertAll(
                () -> assertEquals(payment.getAmount(), savedPayment.getAmount()),
                () -> assertEquals(payment.getDate(), savedPayment.getDate()),
                () -> assertEquals(0, savedPayment.getIndex())
        );
    }

    @Test
    void checkIfPromotionOrderIsMaintained() {
        Promotion promotion1 = new Promotion("White", "Yellow", LocalDate.now());
        Promotion promotion2 = new Promotion("Yellow", "Green",
                LocalDate.of(2025, Month.JANUARY, 1));
        Promotion promotion3 = new Promotion("Green", "Purple",
                LocalDate.of(2026, Month.MARCH, 25));
        Promotion promotion4 = new Promotion("Purple", "Brown",
                LocalDate.of(2026, Month.AUGUST, 22));

        List<Promotion> expected = new LinkedList<>();
        expected.add(promotion1);
        expected.add(promotion2);
        expected.add(promotion3);
        expected.add(promotion4);

        Student student = new Student("White", LocalDate.now());
        student.addPromotion(promotion1);
        Profile profile = new Profile("John", "Doe",
                LocalDate.of(1980, Month.JANUARY, 1), 'M', student);
        Long id = profileRepository.save(profile).getId();

        Optional<Profile> optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());
        profile = optional.get();

        profile.getStudent().addPromotion(promotion2);
        profileRepository.save(profile);

        optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());

        profile = optional.get();
        profile.getStudent().addPromotion(promotion3);
        profile.getStudent().addPromotion(promotion4);
        profileRepository.save(profile);

        optional = profileRepository.findById(id);
        assertTrue(optional.isPresent());

        student = optional.get().getStudent();

        var expectedIterator = expected.listIterator();
        var actualIterator = student.promotionsIterator();
        while (expectedIterator.hasNext()) {
            var e = expectedIterator.next();
            var a = actualIterator.next();

            assertAll(
                    () -> assertEquals(e.getOldRank(), a.getOldRank()),
                    () -> assertEquals(e.getNewRank(), a.getNewRank()),
                    () -> assertEquals(e.getDate(), a.getDate())
            );
        }

        assertEquals(expectedIterator.hasNext(), actualIterator.hasNext());
    }

    @Test
    void checkIfPromotionTableReferencesRespectiveStudents() {
        // given
        Student student1 = new Student("White", LocalDate.now());
        Promotion promotion1 = new Promotion(student1.getRank(), "Green", LocalDate.now());
        student1.addPromotion(promotion1);
        Profile profile1 = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student1);

        Student student2 = new Student("Yellow", LocalDate.now());
        Promotion promotion2 = new Promotion(student1.getRank(), "Purple", LocalDate.now());
        student2.addPromotion(promotion2);
        Profile profile2 = new Profile("Jane", "Doe", LocalDate.of(1980, Month.FEBRUARY, 1), 'F', student2);

        // when
        Long id1 = profileRepository.save(profile1).getId();
        Long id2 = profileRepository.save(profile2).getId();
        Optional<Profile> optional1 = profileRepository.findById(id1);
        Optional<Profile> optional2 = profileRepository.findById(id2);

        // then
        assertTrue(optional1.isPresent());
        assertTrue(optional2.isPresent());

        Student savedStudent1 = optional1.get().getStudent();
        Student savedStudent2 = optional2.get().getStudent();

        Promotion savedPromotion1 = savedStudent1.promotionsIterator().next();
        Promotion savedPromotion2 = savedStudent2.promotionsIterator().next();

        assertAll(
                () -> assertEquals(promotion1.getOldRank(), savedPromotion1.getOldRank()),
                () -> assertEquals(promotion1.getNewRank(), savedPromotion1.getNewRank()),
                () -> assertEquals(promotion1.getDate(), savedPromotion1.getDate()),

                () -> assertEquals(promotion2.getOldRank(), savedPromotion2.getOldRank()),
                () -> assertEquals(promotion2.getNewRank(), savedPromotion2.getNewRank()),
                () -> assertEquals(promotion2.getDate(), savedPromotion2.getDate())
        );
    }

    @Test
    void checkIfPromotionIsInsertedAssociatedWithStudent() {
        // given
        Student student = new Student("White", LocalDate.now());
        Promotion promotion = new Promotion(student.getRank(), "Yellow", LocalDate.now());
        student.addPromotion(promotion);
        Profile profile = new Profile("John", "Doe", LocalDate.of(1980, Month.JANUARY, 1), 'M', student);

        Long id = profileRepository.save(profile).getId();

        // when
        Optional<Profile> optional = profileRepository.findById(id);

        // then
        assertTrue(optional.isPresent());

        Profile savedProfile = optional.get();
        Student savedStudent = savedProfile.getStudent();

        Promotion savedPromotion = savedStudent.promotionsIterator().next();

        assertAll(
                () -> assertEquals(promotion.getOldRank(), savedPromotion.getOldRank()),
                () -> assertEquals(promotion.getNewRank(), savedPromotion.getNewRank()),
                () -> assertEquals(promotion.getDate(), savedPromotion.getDate())
        );
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
