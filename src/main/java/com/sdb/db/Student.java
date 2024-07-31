package com.sdb.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Student")
@Data
@AllArgsConstructor
public class Student {

    @Column("studentId")
    @Id
    private Long id;

    @Column("rank")
    private String rank;

    @Column("dateBegan")
    private LocalDate dateBegan;

    @Column("weight")
    private Integer weight;

    @Column("height")
    private Integer height;
}
