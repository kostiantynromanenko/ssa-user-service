package com.h1totsu.ssauserservice.enums;

import lombok.Getter;

@Getter
public enum SignUpErrorCode {
  USERNAME_EXISTS("username_exists"), EMAIL_EXISTS("email_exists"), SERVICE_ERROR("service_error");

  private String value;

  SignUpErrorCode(String value) {
    this.value = value;
  }
}
