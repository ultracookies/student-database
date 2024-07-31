package com.sdb.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Profile")
@Data
public class Profile {

    @Column("profileId")
    @Id
    private Long id;
    @Column("firstName")
    private String firstName;

    @Column("middleName")
    private String middleName;

    @Column("lastName")
    private String lastName;

    @Column("preferredName")
    private String preferredName;

    @Column("dateOfBirth")
    private LocalDate dateOfBirth;

    @Column("sex")
    private Character sex;

    @Column("phoneNumber")
    private String phoneNumber;

    @Column("studentId")
    private Student student;

    public Profile(String firstName, String lastName, String preferredName, LocalDate dateOfBirth, Character sex, Student student) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.student = student;
    }
}
