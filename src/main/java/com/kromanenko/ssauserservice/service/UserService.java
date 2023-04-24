package com.kromanenko.ssauserservice.service;

import java.util.Optional;

import com.kromanenko.ssauserservice.model.User;

public interface UserService {

  Optional<User> getUserById(String id);

  User createUser(User user);

  void deleteUserById(String id);
}
