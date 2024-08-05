package com.sdb.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("Payments")
@Data
@Setter(AccessLevel.NONE)
public class Payment {

    @Column("index")
    private Integer index;

    @Column("amount")
    @NonNull
    private final Float amount;

    @Column("date")
    @NonNull
    private final LocalDate date;
}
