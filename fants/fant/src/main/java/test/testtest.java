package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.services.dao.data.fants.IUsersDAO;

public class testtest {
	private static final String targetURL = "http://192.168.2.103/service/customer/1";

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/META-INF/spring/dao.xml");
		IUsersDAO dao = applicationContext.getBean("usersDAO", IUsersDAO.class);
		System.out.println(dao.insertNewUser("ert", "ert"));

	}
}
