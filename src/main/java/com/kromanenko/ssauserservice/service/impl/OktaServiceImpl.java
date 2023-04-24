package com.kromanenko.ssauserservice.service.impl;

import com.kromanenko.ssauserservice.model.OktaUser;
import com.kromanenko.ssauserservice.service.OktaService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.user.UserBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OktaServiceImpl implements OktaService {

  private static final String EXTERNAL_ID_ATTR = "external_id";
  private final Client oktaClient;

  @Override
  public void createUser(final OktaUser oktaUser, String... groups) throws ResourceException {
    UserBuilder.instance()
        .setEmail(oktaUser.getEmail())
        .setPassword(oktaUser.getPassword().toCharArray())
        .setFirstName(oktaUser.getFirstName())
        .setLastName(oktaUser.getLastName())
        .setGroups(groups)
        .putProfileProperty(EXTERNAL_ID_ATTR, oktaUser.getExternalId())
        .setActive(true)
        .buildAndCreate(oktaClient);
  }
}
