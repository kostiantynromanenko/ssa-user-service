package com.h1totsu.ssauserservice.service;

import java.util.UUID;

import com.h1totsu.ssauserservice.dto.UserSignUpRequest;
import com.h1totsu.ssauserservice.model.User;

public interface UserService {

  User getUserById(UUID id);

  void signUp(UserSignUpRequest signUpRequest);

  User createUser(User user);

  void deleteUser(UUID id);
}
