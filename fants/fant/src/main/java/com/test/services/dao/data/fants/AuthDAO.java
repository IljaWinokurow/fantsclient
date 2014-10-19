package com.test.services.dao.data.fants;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AuthDAO implements IAuthDAO {

	DataSource datasource;

	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
	}

	public String login(String username, String password) {
		String sql = "SELECT COUNT(*) FROM fants.userlogin where username='"
				+ username + "' AND password= '" + password + "'";
		java.sql.Connection conn = null;
		String token = null;
		try {
			conn = datasource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int i = rs.getInt(1);
				if (i > 0) {
					token = generateToken(username);
				}
			}
			rs.close();
			ps.close();
			return token;
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

	}

	protected String generateToken(String username) {
		SecureRandom random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);

		String sql = "UPDATE userlogin SET token='" + token
				+ "', lastlogin=now() where username = '" + username + "'";
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
		return token;

	}
}
