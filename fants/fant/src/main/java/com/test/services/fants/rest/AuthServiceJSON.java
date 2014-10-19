//**********************************************************
//
// Author: Ilja Winokurow
//
// Completion time: 15.07.2014
//
//*********************************************************
package com.test.services.fants.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.services.customers.rest.exceptions.ErrorRest;
import com.test.services.dao.data.fants.AuthDAO;
import com.test.services.dao.data.fants.IAuthDAO;
import com.test.services.rest.response.ResponseCreator;

public class AuthServiceJSON implements IAuthService {

	// link to our dao object
	private IAuthDAO authDAO;

	/**
	 * Getter for Auth DAO
	 * 
	 * @return authentification service dao
	 */
	public IAuthDAO getAuthDAO() {
		return authDAO;
	}

	public void setAuthDAO(IAuthDAO authDAO) {
		this.authDAO = authDAO;
	}

	// for retrieving request headers from context
	// an injectable interface that provides access to HTTP header information.
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return requestHeaders.getRequestHeader("version").get(0);
	}

	// get by id service
	@GET
	@Produces("text/plain")
	public Response login(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/dao.xml");
		AuthDAO authDAO = (AuthDAO) context.getBean("authDAO");
		String token = authDAO.login(username, password);
		context.close();
		if (token != null) {
			return ResponseCreator.success(getHeaderVersion(), token);
		} else {
			return ResponseCreator.error(401,
					ErrorRest.NOT_AUTHORIZED.getCode(),
					"Authentification wasn't successfull");
		}

	}
}