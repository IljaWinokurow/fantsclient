//**********************************************************
//
// Author: Ilja Winokurow
//
// Completion time: 15.07.2014
//
//*********************************************************
package com.test.services.fants.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.services.customers.rest.exceptions.Error;
import com.test.services.dao.data.fants.FantsDAO;
import com.test.services.dao.data.fants.IFantsDAO;
import com.test.services.entities.Fant;
import com.test.services.rest.response.ResponseCreator;

public class FantsServiceJSON implements IFantsService {

	// link to our dao object
	private IFantsDAO fantsDAO;

	// for customersDAO bean property injection
	public IFantsDAO getFantsDAO() {
		return fantsDAO;
	}

	public void setFantsDAO(IFantsDAO fantsDAO) {
		this.fantsDAO = fantsDAO;
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
	@Path("/{id}")
	public Response getFant(@PathParam("id") String id) {
		// Customer customer = customersDAO.getCustomer(id);
		if (id != null) {
			try {
				ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
						"/META-INF/spring/dao.xml");
				FantsDAO fantsDAO = (FantsDAO) context.getBean("fantsDAO");
				List<Fant> fants = fantsDAO.getFants();
				context.close();
				Random randomGenerator = new Random();
				int randomInt = randomGenerator.nextInt(fants.size());

				return ResponseCreator.success(getHeaderVersion(),
						fants.get(randomInt).getText().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(),
					getHeaderVersion());
		}
		return null;
	}

}