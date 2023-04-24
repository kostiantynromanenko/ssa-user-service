package com.kromanenko.ssauserservice.enums;

public enum OktaErrorCode {
  API_VALIDATION("E0000001");

  private final String code;

  OktaErrorCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
