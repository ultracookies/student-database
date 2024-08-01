package com.sdb.db;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Student")
@Data
public class Student {

    @Column("studentId")
    @Id
    private Long id;

    @Column("rank")
    @NonNull
    private String rank;

    @Column("dateBegan")
    @NonNull
    private LocalDate dateBegan;

    @Column("weight")
    private Integer weight;

    @Column("height")
    private Integer height;
}
