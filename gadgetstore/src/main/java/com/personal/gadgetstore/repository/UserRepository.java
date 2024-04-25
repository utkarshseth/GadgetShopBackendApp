package com.personal.gadgetstore.repository;

import com.personal.gadgetstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    // Custom query methods can be defined here if needed
    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String name);

}
