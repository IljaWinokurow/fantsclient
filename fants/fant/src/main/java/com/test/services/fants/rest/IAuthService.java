package com.test.services.fants.rest;

import javax.ws.rs.core.Response;

public interface IAuthService {

	Response login(String username, String password);
}
