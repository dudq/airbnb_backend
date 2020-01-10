package com.airbnb.services;

import com.airbnb.messages.response.UserInformation;
import com.airbnb.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    User findById(Long id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    UserInformation findByIdCurrent(Long id);

}
