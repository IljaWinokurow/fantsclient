package com.test.services.fants.rest;

import javax.ws.rs.core.Response;

public interface IUsersService {

	Response registerUser(String username, String password);
}
