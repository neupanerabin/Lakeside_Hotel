package com.lakesidedemo.lakesideHotel.repository;
import com.lakesidedemo.lakesideHotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/*
 * @author : rabin
 */


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existByEmail(String email);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);
}

