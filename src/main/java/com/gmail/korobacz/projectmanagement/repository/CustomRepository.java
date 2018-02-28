package com.gmail.korobacz.projectmanagement.repository;

import com.gmail.korobacz.projectmanagement.model.Role;
import com.gmail.korobacz.projectmanagement.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomRepository {

    @PersistenceContext
    EntityManager em;

    public boolean isRoleActive(Role role) {
        Query query = em.createQuery("SELECT u FROM users u WHERE :role MEMBER OF u.roles");
        query.setParameter("role", role);
        List<User> users = query.getResultList();
        if(users.isEmpty()){
            return false;
        }else{
            return  true;
        }
    }

}
