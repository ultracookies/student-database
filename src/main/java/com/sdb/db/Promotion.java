package com.sdb.db;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Promotions")
@Data
public class Promotion {
    @Column("promotionId")
    @Id
    private Long id;

    @Column("oldRank")
    @NonNull
    private String oldRank;

    @Column("newRank")
    @NonNull
    private String newRank;

    @Column("date")
    @NonNull
    private LocalDate date;

    @Column("STUDENT_KEY")
    @NonNull
    private Long studentId;
}
