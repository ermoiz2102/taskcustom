package com.example.customer.authJwt.Repository;

import com.example.customer.authJwt.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface userRepository extends JpaRepository<User,Integer> {


    @Query(value = "select * from users where email=?1",nativeQuery = true)
    Optional<User> findByEmail(String email);
}
