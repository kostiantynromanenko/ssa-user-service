package com.h1totsu.ssauserservice.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoConfig {

  @Value(value = "${aws.access-key}")
  private String accessKey;

  @Value(value = "${aws.access-secret}")
  private String secretKey;

  @Value(value = "${aws.region}")
  private String region;

  @Value(value = "${aws.cognito.userPoolId}")
  private String userPoolId;

  @Bean
  public AWSCognitoIdentityProvider cognitoClient() {
    var awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

    return AWSCognitoIdentityProviderClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withRegion(region)
        .build();
  }

  public String getUserPoolId() {
    return userPoolId;
  }
}
