package com.kromanenko.ssauserservice.facade;

import static com.kromanenko.ssauserservice.enums.OktaErrorCode.API_VALIDATION;
import static com.kromanenko.ssauserservice.enums.SignUpErrorCode.EMAIL_EXISTS;
import static com.kromanenko.ssauserservice.enums.SignUpErrorCode.SERVICE_ERROR;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.kromanenko.ssauserservice.configuration.UserConfig;
import com.kromanenko.ssauserservice.dto.SignUpRequest;
import com.kromanenko.ssauserservice.exception.SignUpException;
import com.kromanenko.ssauserservice.model.OktaUser;
import com.kromanenko.ssauserservice.model.User;
import com.kromanenko.ssauserservice.service.OktaService;
import com.kromanenko.ssauserservice.service.UserService;
import com.okta.sdk.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

  private final OktaService oktaService;
  private final UserService userService;
  private final UserConfig userConfig;

  public void signUp(final SignUpRequest request) throws SignUpException {
    String userId = null;
    String email = request.getEmail();

    try {
      var user = new User();
      user.setEmail(email);

      var createdUser = userService.createUser(user);
      userId = createdUser.getId();

      var oktaUser = new OktaUser();
      oktaUser.setExternalId(userId);
      oktaUser.setEmail(email);
      oktaUser.setPassword(request.getPassword());
      oktaUser.setFirstName(request.getFirstName());
      oktaUser.setLastName(request.getLastName());

      oktaService.createUser(oktaUser, userConfig.getGroups());
    } catch (ResourceException e) {
      log.error("User cannot be created", e);

      if (e.getError().getCode().equals(API_VALIDATION.getCode())) {
        throw new SignUpException("User cannot be created", EMAIL_EXISTS, e);
      }

      handleServiceError(e);
    } catch (Exception e) {
      log.error("User cannot be created", e);

      if (isNotBlank(userId)) {
        userService.deleteUserById(userId);
      }

      handleServiceError(e);
    }
  }

  // TODO refactor
  private void handleServiceError(Exception e) {
    throw new SignUpException("User cannot be created", SERVICE_ERROR, e);
  }
}
