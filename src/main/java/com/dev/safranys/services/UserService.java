package com.dev.safranys.services;

import com.dev.safranys.dtos.UserDto;
import com.dev.safranys.exceptions.UserNotFoundException;
import com.dev.safranys.modeles.User;
import com.dev.safranys.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save user
     */
    @Transactional
    public User saveUser(UserDto userDto) {
        User newUser = new User(
                null,
                userDto.email(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.age(),
                userDto.city()
        );
        return userRepository.save(newUser);
    }

    /**
     * Updates the existing User with new values (full update).
     */
    @Transactional
    public Optional<User> updateUser(String userId, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User existingUser = optionalUser.get();
        User updatedUser = new User(
                existingUser.getId(),
                userDto.email(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.age(),
                userDto.city()
        );
        User saved = userRepository.save(updatedUser);
        return Optional.of(saved);
    }

    @Transactional
    public void deleteUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("No user found for id: " + userId);
        }
        userRepository.delete(optionalUser.get());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<String> getUserIds() {
        return userRepository.getUserIds();
    }
}