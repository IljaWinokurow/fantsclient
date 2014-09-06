package com.test.services.dao.data.fants;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UsersDAO implements IUsersDAO {

	DataSource datasource;

	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
	}

	public boolean insertNewUser(String username, String password) {
		String sql = "INSERT INTO userlogin (`username`, `password`) VALUES ('"
				+ username + "', '" + password + "')";
		boolean result = false;
		java.sql.Connection conn = null;

		try {
			conn = datasource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate(sql);
			result = true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
}
