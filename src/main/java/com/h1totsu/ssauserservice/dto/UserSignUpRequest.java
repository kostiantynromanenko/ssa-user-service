package com.h1totsu.ssauserservice.dto;

import com.h1totsu.ssauserservice.annotation.Password;
import com.h1totsu.ssauserservice.annotation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserSignUpRequest {

  @Username
  private String username;

  @NotBlank
  @Email
  private String email;

  @Password
  private String password;
}
