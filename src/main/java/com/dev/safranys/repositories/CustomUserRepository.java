package com.dev.safranys.repositories;

import com.dev.safranys.modeles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<User, Long> {
    User saveUser(User user);
}