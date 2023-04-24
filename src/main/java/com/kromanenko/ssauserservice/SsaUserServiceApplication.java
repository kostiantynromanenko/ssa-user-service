package com.kromanenko.ssauserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class SsaUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SsaUserServiceApplication.class, args);
  }
}
