package org.mac.util.unittestrunner.config;

import javax.sql.DataSource;

import org.mac.util.unittestrunner.DatabaseDAO;
import org.mac.util.unittestrunner.DatabaseDAOImpl;
import org.mac.util.unittestrunner.HttpDAO;
import org.mac.util.unittestrunner.HttpDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mysql.jdbc.Driver;

@Configuration
public class Beans {

	private static final Logger logger = LoggerFactory.getLogger(Beans.class);

	public static final String ENV_DB_LOGIN = "acme.db.login";

	public static final String ENV_DB_URL = "acme.db.url";

	private static final String ENV_DB_PASSWORD = "acme.db.password";

	private static final String ENV_DB_SCHEMA = "acme.db.schema";

	private static final String ENV_APP_URL = "acme.app.url";

	@Autowired
	private Environment env;

	/**
	 * Requires env parameters <code>-Dacme.db.schema</code>
	 * <code>-Dacme.db.url</code> <code>-Dacme.db.password</code>
	 * <code>-Dacme.db.login</code> <code>-Dacme.app.url</code>
	 * 
	 * @return
	 */
	@Bean(name = "datasource")
	public DataSource dataSource() {
		DriverManagerDataSource dbSource;
		try {
			dbSource = new DriverManagerDataSource();
			dbSource.setDriverClassName(Driver.class.getName());
			dbSource.setUrl("jdbc:mysql://" + trimUrl(getProperty(ENV_DB_URL)));
			dbSource.setUsername(getProperty(ENV_DB_LOGIN));
			dbSource.setPassword(getProperty(ENV_DB_PASSWORD));
			dbSource.setSchema(getProperty(ENV_DB_SCHEMA));

		} catch (IllegalArgumentException e) {
			logger.error("Unable to configure Datasource", e);
			dbSource = null;
		}

		return dbSource;
	}

	@Bean(name = "db")
	public DatabaseDAO databaseDAO() {
		return new DatabaseDAOImpl();
	}

	/**
	 * Requires env parameter <code>-Dacme.app.url</code>
	 * 
	 * @return
	 */
	@Bean(name = "httpauth")
	public HttpDAO httpDAO() {
		HttpDAO dao;
		try {
			dao = new HttpDAOImpl();
			dao.setUrl(getProperty(ENV_APP_URL));
		} catch (IllegalArgumentException e) {
			logger.error("Unable to configure Http endpoint", e);
			dao = null;
		}
		return dao;
	}

	private String getProperty(String key) throws IllegalArgumentException {
		if (null == key || "".equals(key)) {
			throw new IllegalArgumentException("Invalid env property key '" + key + "'");
		} else if (!env.containsProperty(key)) {
			throw new IllegalArgumentException("Missing env property -D" + key + "=...");
		}
		String r = env.getProperty(key);
		if (null == r || "".equals(r)) {
			throw new IllegalArgumentException("Empty value for env property -D" + key + "=...");
		} else {
			return r.trim();
		}
	}

	public static String trimUrl(String dbUrl) {
		int n = dbUrl.indexOf("://");
		dbUrl = n > -1 ? dbUrl.substring(n) : dbUrl;
		dbUrl = dbUrl.endsWith("/") ? dbUrl : dbUrl + "/";
		return dbUrl;
	}
}
