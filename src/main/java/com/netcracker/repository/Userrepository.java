package com.netcracker.repository;

import com.netcracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;


public interface Userrepository extends JpaRepository<User,Integer> {

    @Query(value = "select*from testusers e where e.fname=:name and e.lname=:lastname ",nativeQuery = true)
    List<User> findbynameadlastname(@Param("name") String name,@Param("lastname") String lastname);

    @Query(value = "select mailadress from testusers",nativeQuery = true)
    List<String> findAllEmail();


}
