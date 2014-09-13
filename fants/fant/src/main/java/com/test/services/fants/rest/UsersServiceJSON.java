//**********************************************************
//
// Author: Ilja Winokurow
//
// Completion time: 15.07.2014
//
//*********************************************************
package com.test.services.fants.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.test.services.customers.rest.exceptions.Error;
import com.test.services.dao.data.fants.IUsersDAO;
import com.test.services.rest.response.ResponseCreator;

public class UsersServiceJSON implements IUsersService {

	// link to our dao object
	private IUsersDAO usersDAO;

	// for customersDAO bean property injection
	public IUsersDAO getUsersDAO() {
		return usersDAO;
	}

	public void setUsersDAO(IUsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	// for retrieving request headers from context
	// an injectable interface that provides access to HTTP header information.
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return requestHeaders.getRequestHeader("version").get(0);
	}

	// // get by id service
	// @GET
	// @Produces("text/plain")
	// @Path("/{id}")
	// public Response getFant(@PathParam("id") String id) {
	// // Customer customer = customersDAO.getCustomer(id);
	// if (id != null) {
	// try {
	// ConfigurableApplicationContext context = new
	// ClassPathXmlApplicationContext(
	// "/META-INF/spring/dao.xml");
	// FantsDAO fantsDAO = (FantsDAO) context.getBean("fantsDAO");
	// List<Fant> fants = fantsDAO.getFants();
	// context.close();
	// Random randomGenerator = new Random();
	// int randomInt = randomGenerator.nextInt(fants.size());
	//
	// return ResponseCreator.success(getHeaderVersion(),
	// fants.get(randomInt).getText().getBytes("UTF-8"));
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	// return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
	// getHeaderVersion());
	// }
	// return null;
	// }

	// create row representing customer and returns created customer as
	// object->JSON structure
	@POST
	@javax.ws.rs.Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		System.out.println("POST");
		if (usersDAO.insertNewUser(username, password)) {
			return ResponseCreator.success(getHeaderVersion(), "created");
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), "-"
					+ username + ":" + password);
		}
	}
}