package com.sdb.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Table("Profile")
@Data
public class Profile {

    @Column("profileId")
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("firstName")
    @NonNull
    private String firstName;

    @Column("middleName")
    private String middleName;

    @Column("lastName")
    @NonNull
    private String lastName;

    @Column("preferredName")
    private String preferredName;

    @Column("dateOfBirth")
    @NonNull
    @Setter(AccessLevel.NONE)
    private LocalDate dateOfBirth;

    @Column("sex")
    @NonNull
    private Character sex;

    @Column("phoneNumber")
    private String phoneNumber;

    @Column("studentId")
    @NonNull
    @Setter(AccessLevel.NONE)
    private Student student;

    @Column("addressId")
    private Address address;

    @MappedCollection(idColumn = "PROFILE_KEY", keyColumn = "index")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<Payment> payments = new LinkedList<>();

    public ListIterator<Payment> paymentIterator() {
        return this.payments.listIterator();
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }
}
