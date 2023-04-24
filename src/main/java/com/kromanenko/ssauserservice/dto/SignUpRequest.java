package com.kromanenko.ssauserservice.dto;

import com.kromanenko.ssauserservice.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SignUpRequest {

  @NotBlank
  @Email
  private String email;

  @Password
  private String password;

  private String firstName;
  private String lastName;
}
