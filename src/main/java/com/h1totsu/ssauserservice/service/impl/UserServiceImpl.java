package com.h1totsu.ssauserservice.service.impl;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;

import static com.h1totsu.ssauserservice.enums.SignUpErrorCode.EMAIL_EXISTS;
import static com.h1totsu.ssauserservice.enums.SignUpErrorCode.SERVICE_ERROR;
import static com.h1totsu.ssauserservice.enums.SignUpErrorCode.USERNAME_EXISTS;

import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import com.h1totsu.ssauserservice.configuration.CognitoConfig;
import com.h1totsu.ssauserservice.dto.UserSignUpRequest;
import com.h1totsu.ssauserservice.exception.SignUpException;
import com.h1totsu.ssauserservice.model.User;
import com.h1totsu.ssauserservice.reposotiry.UserRepository;
import com.h1totsu.ssauserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String USERS_EMAIL_KEY = "users_email_key";
  private static final String USERS_USERNAME_KEY = "users_username_key";
  private static final String EMAIL_VERIFIED_ATTR = "email_verified";
  private static final String EMAIL_ATTR = "email";
  private static final String CUSTOM_USER_ID_ATTR = "custom:user_id";

  private final AWSCognitoIdentityProvider cognitoClient;
  private final CognitoConfig cognitoConfig;
  private final UserRepository userRepository;

  @Override
  public User getUserById(final UUID id) {
    return userRepository.getReferenceById(id);
  }

  @Override
  public User createUser(final User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(final UUID id) {
    userRepository.deleteById(id);
  }

  @Override
  public void signUp(final UserSignUpRequest signUpRequest) throws SignUpException {
    UUID userId = null;

    try {
      final var user = new User();
      user.setUsername(signUpRequest.getUsername());
      user.setEmail(signUpRequest.getEmail());
      final var createdUser = createUser(user);
      userId = createdUser.getId();

      final var attributes = new AttributeType[] {
          new AttributeType().withName(EMAIL_ATTR).withValue(signUpRequest.getEmail()),
          new AttributeType().withName(EMAIL_VERIFIED_ATTR).withValue(TRUE.toString()),
          new AttributeType().withName(CUSTOM_USER_ID_ATTR).withValue(userId.toString())
      };

      final var userRequest = getUserRequest(signUpRequest, attributes);

      final var createUserResult = cognitoClient.adminCreateUser(userRequest);

      log.info("User {} is created. Status: {}", createUserResult.getUser().getUsername(),
          createUserResult.getUser().getUserStatus());

      final var adminSetUserPasswordRequest =
          new AdminSetUserPasswordRequest().withUsername(signUpRequest.getUsername())
              .withUserPoolId(cognitoConfig.getUserPoolId())
              .withPassword(signUpRequest.getPassword()).withPermanent(true);

      cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
    } catch (DataIntegrityViolationException e) {
      if (nonNull(e.getCause()) && e.getCause() instanceof ConstraintViolationException) {
        var constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
        if (USERS_EMAIL_KEY.equals(constraintName)) {
          log.error("User cannot be created: email ({}) already exists", signUpRequest.getEmail());
          throw new SignUpException("Email already exists", EMAIL_EXISTS, e);
        }
        if (USERS_USERNAME_KEY.equals(constraintName)) {
          log.error("User cannot be created: username ({}) already exists", signUpRequest.getUsername());
          throw new SignUpException("Username already exists", USERNAME_EXISTS, e);
        }
      } else {
        log.error("User cannot be created", e);
      }
      Optional.ofNullable(userId).ifPresent(this::deleteUser);
    } catch (AWSCognitoIdentityProviderException e) {
      log.error("Issue occurred in AWS Cognito", e);
      Optional.ofNullable(userId).ifPresent(this::deleteUser);
      throw new SignUpException("Internal service error", SERVICE_ERROR, e);
    } catch (Exception e) {
      log.error("Unexpected error during sign-up", e);
      Optional.ofNullable(userId).ifPresent(this::deleteUser);
      throw new SignUpException("Internal service error", SERVICE_ERROR, e);
    }
  }

  private AdminCreateUserRequest getUserRequest(
      UserSignUpRequest signUpRequest,
      AttributeType[] attributes) {
    return new AdminCreateUserRequest()
        .withUserPoolId(cognitoConfig.getUserPoolId()).withUsername(signUpRequest.getUsername())
        .withTemporaryPassword(signUpRequest.getPassword())
        .withUserAttributes(attributes)
        .withMessageAction(MessageActionType.SUPPRESS)
        .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);
  }
}
