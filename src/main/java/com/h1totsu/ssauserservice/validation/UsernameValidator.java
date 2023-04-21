package com.h1totsu.ssauserservice.validation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.regex.Pattern;

import com.h1totsu.ssauserservice.annotation.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

  private static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
  private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

  @Override
  public void initialize(Username constraintAnnotation) {
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    if (isBlank(username)) {
      return false;
    }
    return USERNAME_PATTERN.matcher(username).matches();
  }
}