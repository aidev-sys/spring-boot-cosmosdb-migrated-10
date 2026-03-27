package com.dev.safranys.repositories;

import com.dev.safranys.modeles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Return a list of user IDs
    @Query("SELECT u.id FROM User u")
    List<String> getUserIds();

    // getAllUsers is provided by JpaRepository's findAll()
    // The method is kept for compatibility with existing service code if needed.
    default List<User> getAllUsers() {
        return findAll();
    }
}