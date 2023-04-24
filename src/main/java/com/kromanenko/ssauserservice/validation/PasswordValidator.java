package com.kromanenko.ssauserservice.validation;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

import java.util.regex.Pattern;

import com.kromanenko.ssauserservice.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  private static final String PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

  @Override
  public void initialize(Password constraintAnnotation) {
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return isNoneBlank(password) && PASSWORD_PATTERN.matcher(password).matches();
  }
}
