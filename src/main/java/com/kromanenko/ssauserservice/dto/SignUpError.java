package com.kromanenko.ssauserservice.dto;

import static java.util.Collections.emptyList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpError extends ApiError {
  private String errorCode;

  public SignUpError(int status, String errorCode) {
    super(status, StringUtils.EMPTY, emptyList());
    this.errorCode = errorCode;
  }
}

