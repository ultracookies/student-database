package com.sdb.db;

import lombok.Data;
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
    private String rank;

    @Column("dateBegan")
    private LocalDate dateBegan;

    @Column("weight")
    private Integer weight;

    @Column("height")
    private Integer height;

    public Student(String rank, LocalDate dateBegan, Integer weight, Integer height) {
        this.rank = rank;
        this.dateBegan = dateBegan;
        this.weight = weight;
        this.height = height;
    }
}
