package com.sdb.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    @Column("promotionId")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<Promotion> promotions = new LinkedList<>();

    public void addPromotion(Promotion promotion) { promotions.add(promotion); }
}
