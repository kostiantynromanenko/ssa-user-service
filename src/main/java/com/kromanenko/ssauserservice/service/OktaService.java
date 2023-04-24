package com.kromanenko.ssauserservice.service;

import com.kromanenko.ssauserservice.model.OktaUser;
import com.okta.sdk.resource.ResourceException;

public interface OktaService {

  void createUser(OktaUser oktaUser, String... groups) throws ResourceException;
}
