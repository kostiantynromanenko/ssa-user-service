package com.h1totsu.ssauserservice.exception;

import com.h1totsu.ssauserservice.enums.SignUpErrorCode;
import lombok.Getter;

@Getter
public class SignUpException extends RuntimeException {
  private SignUpErrorCode errorCode;

  public SignUpException(String message, SignUpErrorCode errorCode, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public SignUpException(String message, Throwable cause) {
    super(message, cause);
  }
}
