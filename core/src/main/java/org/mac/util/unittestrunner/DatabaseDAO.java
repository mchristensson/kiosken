package org.mac.util.unittestrunner;

import org.springframework.jdbc.core.JdbcTemplate;

public interface DatabaseDAO {

	String handshakeDb();

	boolean isValid();

}
