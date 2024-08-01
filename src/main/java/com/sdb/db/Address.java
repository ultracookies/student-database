package com.sdb.db;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Address")
@Data
public class Address {

    @Column("addressId")
    @Id
    private Long id;

    @Column("addressLine1")
    @NonNull
    private String addressLine1;

    @Column("addressLine2")
    private String addressLine2;

    @Column("city")
    @NonNull
    private String city;

    @Column("state")
    @NonNull
    private String state;

    @Column("zipcode")
    @NonNull
    private String zipcode;
}
