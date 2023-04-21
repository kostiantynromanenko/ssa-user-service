package com.h1totsu.ssauserservice.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDto {

  private UUID id;
  private String username;
  private String email;
}
