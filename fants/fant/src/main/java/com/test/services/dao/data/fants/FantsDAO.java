package com.test.services.dao.data.fants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.test.services.entities.Fant;

public class FantsDAO implements IFantsDAO {

	DataSource datasource;

	public void setDataSource(DataSource dataSource) {
		this.datasource = dataSource;
	}

	public List<Fant> getFants() {
		String sql = "SELECT * FROM FANT";
		List<Fant> fants = new ArrayList<Fant>();
		java.sql.Connection conn = null;

		try {
			conn = datasource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("rwrw");
				Fant fant = new Fant();
				fant.setId((String) rs.getString("id"));
				fant.setText((String) rs.getString("text"));
				fants.add(fant);
			}
			rs.close();
			ps.close();
			return fants;
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
}
