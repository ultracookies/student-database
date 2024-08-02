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

@Table("Student")
@Data
public class Student {

    @Column("studentId")
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("rank")
    @NonNull
    private String rank;

    @Column("dateBegan")
    @NonNull
    @Setter(AccessLevel.NONE)
    private LocalDate dateBegan;

    @Column("weight")
    private Integer weight;

    @Column("height")
    private Integer height;

    @MappedCollection(idColumn = "STUDENT_KEY", keyColumn = "index")
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<Promotion> promotions = new LinkedList<>();

    public void addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
    }

    public ListIterator<Promotion> promotionsIterator() {
        return this.promotions.listIterator();
    }
}
