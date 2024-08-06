package com.sdb.db;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @Query("SELECT * FROM \"Address\" WHERE \"addressId\"= :id")
    List<Address> findByID(@Param("id") Long id);
}
