package com.gmail.korobacz.projectmanagement.repository;

import com.gmail.korobacz.projectmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT DISTINCT r FROM Role r")
    List<Role> findAllDistinct();

    List<Role> findByNameIn(List<String> names);

    Role findByName(String name);
}
