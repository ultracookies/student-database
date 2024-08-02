package com.sdb.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

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
}
