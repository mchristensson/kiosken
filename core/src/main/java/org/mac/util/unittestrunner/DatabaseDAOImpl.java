package org.mac.util.unittestrunner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDAOImpl implements DatabaseDAO {

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public boolean isValid() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate != null;
	}
	
	@Override
	public String handshakeDb() {
		StringBuilder result = new StringBuilder();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query("select now", new Object[]{}, row -> {
			result.append(row.getString(0));
		});
		return result.toString();
	}
}
