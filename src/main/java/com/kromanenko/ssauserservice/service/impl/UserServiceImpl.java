package com.kromanenko.ssauserservice.service.impl;

import java.util.Optional;

import com.kromanenko.ssauserservice.model.User;
import com.kromanenko.ssauserservice.reposotiry.UserRepository;
import com.kromanenko.ssauserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Optional<User> getUserById(final String id) {
    return userRepository.findById(id);
  }

  @Override
  public User createUser(final User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUserById(final String id) {
    userRepository.deleteById(id);
  }
}
