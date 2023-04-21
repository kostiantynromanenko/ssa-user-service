package com.h1totsu.ssauserservice.repository;

import com.h1totsu.ssauserservice.model.User;
import com.h1totsu.ssauserservice.reposotiry.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private UserRepository userRepository;

  @Test
  public void whenCreateUser_thenCreatedUser() {
    // given
    User user = new User();
    user.setUsername("userTest");
    user.setEmail("testingUser@email.com");

    // when
    User createdUser = userRepository.save(user);

    // then
    User dbUser = entityManager.find(User.class, createdUser.getId());
    assertEquals(dbUser.getId(), createdUser.getId());
    assertEquals(dbUser.getUsername(), createdUser.getUsername());
  }
}
