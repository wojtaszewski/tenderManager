package com.gmail.korobacz.projectmanagement.Repository;

import com.gmail.korobacz.projectmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public class UserRepository extends EntityRepository<Users> {
//
//    public Users findByEmail(String email){
//        return null;
//    }
//
//}

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}