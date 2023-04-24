package com.kromanenko.ssauserservice.enums;

import lombok.Getter;

@Getter
public enum SignUpErrorCode {
  EMAIL_EXISTS("email_exists"), SERVICE_ERROR("service_error");

  private final String value;

  SignUpErrorCode(String value) {
    this.value = value;
  }
}
