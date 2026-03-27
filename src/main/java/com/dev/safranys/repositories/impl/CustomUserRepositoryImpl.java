package com.dev.safranys.repositories.impl;

import com.dev.safranys.modeles.User;
import com.dev.safranys.repositories.CustomUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomUserRepositoryImpl extends SimpleJpaRepository<User, Long> implements CustomUserRepository {

    private final EntityManager entityManager;

    public CustomUserRepositoryImpl(EntityManager entityManager) {
        super(JpaEntityInformationSupport.getEntityInformation(User.class, entityManager), entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        System.out.println("Saving user: " + user);
        try {
            // Leverage SimpleJpaRepository's save method to handle persistence correctly
            return super.save(user);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}