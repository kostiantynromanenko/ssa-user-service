package com.kromanenko.ssauserservice.exception;

import com.kromanenko.ssauserservice.enums.SignUpErrorCode;
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
