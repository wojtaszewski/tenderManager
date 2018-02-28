package com.gmail.korobacz.projectmanagement.repository;

import com.gmail.korobacz.projectmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT DISTINCT r FROM Role r")
    List<Role> findAllDistinct();

    List<Role> findByNameIn(List<String> names);

    Optional<Role> findByName(String name);

    @Transactional
    void deleteByName(String name);

}
