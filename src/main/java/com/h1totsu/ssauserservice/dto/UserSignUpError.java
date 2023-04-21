package com.h1totsu.ssauserservice.dto;

import static java.util.Collections.emptyList;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class UserSignUpError extends ApiError {
  private String errorCode;

  public UserSignUpError(int status, String errorCode) {
    super(status, StringUtils.EMPTY, emptyList());
    this.errorCode = errorCode;
  }
}
