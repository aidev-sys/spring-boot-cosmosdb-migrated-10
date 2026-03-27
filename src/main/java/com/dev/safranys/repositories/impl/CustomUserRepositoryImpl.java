package com.dev.safranys.repositories.impl;

import com.dev.safranys.modeles.User;
import com.dev.safranys.repositories.CustomUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User saveUser(User user) {
        System.out.println("Saving user: " + user);
        try {
            entityManager.merge(user);
            return user;
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}