package com.kromanenko.ssauserservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

  @Id
  private String id;

  private String email;
}