package com.dev.safranys.services;

import com.dev.safranys.dtos.UserDto;
import com.dev.safranys.exceptions.UserNotFoundException;
import com.dev.safranys.modeles.User;
import com.dev.safranys.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<User, String> crudRepo;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.crudRepo = (CrudRepository<User, String>) userRepository;
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
        return crudRepo.save(newUser);
    }

    /**
     * Updates the existing User with new values (full update).
     */
    @Transactional
    public Optional<User> updateUser(String userId, UserDto userDto) {
        Optional<User> optionalUser = crudRepo.findById(userId);
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
        User saved = crudRepo.save(updatedUser);
        return Optional.of(saved);
    }

    @Transactional
    public void deleteUserById(String userId) {
        Optional<User> optionalUser = crudRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("No user found for id: " + userId);
        }
        crudRepo.delete(optionalUser.get());
    }

    public List<User> getAllUsers() {
        return crudRepo.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(String id) {
        return crudRepo.findById(id);
    }

    public List<String> getUserIds() {
        return crudRepo.findAll()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}