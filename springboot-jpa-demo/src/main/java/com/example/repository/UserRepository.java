package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT * FROM m_users u WHERE u.username = :username AND u.password = crypt(:password, password)
            """, nativeQuery = true)
    User findByUsernameAndPassword(String username, String password);

    boolean existsByToken(String token);

    User findByToken(String token);

}
