package com.h1totsu.ssauserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SsaUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SsaUserServiceApplication.class, args);
  }
}
