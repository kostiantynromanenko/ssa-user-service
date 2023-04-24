package com.kromanenko.ssauserservice.model;

import lombok.Data;

@Data
public class OktaUser {

  private String externalId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
}
