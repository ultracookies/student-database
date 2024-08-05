package com.sdb.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Promotions")
@Data
@Setter(AccessLevel.NONE)
public class Promotion {

    @Column("oldRank")
    @NonNull
    private final String oldRank;

    @Column("newRank")
    @NonNull
    private final String newRank;

    @Column("date")
    @NonNull
    private final LocalDate date;
}
