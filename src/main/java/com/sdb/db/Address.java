package com.sdb.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Address")
@Data
@AllArgsConstructor
public class Address {

    @Column("addressId")
    @Id
    private Long id;

    @Column("addressLine1")
    private String addressLine1;

    @Column("addressLine2")
    private String addressLine2;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("zipcode")
    private String zipcode;
}
