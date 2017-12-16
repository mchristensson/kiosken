package org.mac.util.unittestrunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mac.util.unittestrunner.config.Beans;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class BasicTestExample extends SpringTestRunnerBase {

	
	@Test
    public void testAppContext() {
        assertNotNull(applicationContext);
    }
	
	@Test
    public void testAppProfileAvailable() {
        Environment env = applicationContext.getEnvironment();
        assertNotNull(env);
        String[] profiles = env.getActiveProfiles();
        assertNotNull(profiles);
        assertEquals(1, profiles.length);
        //Set run argument -Dspring.profiles.active=utv453
        assertEquals("Ensure to set argument -Dspring.profiles.active=utv453", "utv453", profiles[0]);
    }
	
	@Test
    public void testDBAvailable() {
		DatabaseDAO bean = (DatabaseDAO) applicationContext.getBean("db");
		assertNotNull(bean);
		assertTrue(bean instanceof DatabaseDAO);
		assertTrue(bean.isValid());
		
	}
	
	@Test(expected=CannotGetJdbcConnectionException.class)
    public void testDBHandshake() {
		DatabaseDAO bean = (DatabaseDAO) applicationContext.getBean("db");
		bean.handshakeDb();
		
	}
	
	@Test
    public void testHttpUrlAvailable() {
		HttpDAO httpDAO = (HttpDAO) applicationContext.getBean("httpauth");
		String url = httpDAO.getUrl();
		assertEquals("Invalid url", "http://ssdfsdf:5656", url);
	}
	
	@Test
	public void trimUrlTest() {
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("http://asdf.sdfsdf.sdfsd:45456/dsgf"));
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("jdbc:mysql://asdf.sdfsdf.sdfsd:45456/dsgf"));
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("http://asdf.sdfsdf.sdfsd:45456/"));
		assertEquals("asdf.sdfsdf.sdfsd", Beans.trimUrl("http://asdf.sdfsdf.sdfsd/dsgf"));
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("http://asdf.sdfsdf.sdfsd:45456"));
		assertEquals("asdf.sdfsdf.sdfsd", Beans.trimUrl("http://asdf.sdfsdf.sdfsd"));
		
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("asdf.sdfsdf.sdfsd:45456/dsgf"));
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("asdf.sdfsdf.sdfsd:45456/"));
		assertEquals("asdf.sdfsdf.sdfsd", Beans.trimUrl("asdf.sdfsdf.sdfsd/dsgf"));
		assertEquals("asdf.sdfsdf.sdfsd:45456", Beans.trimUrl("asdf.sdfsdf.sdfsd:45456"));
		assertEquals("asdf.sdfsdf.sdfsd", Beans.trimUrl("asdf.sdfsdf.sdfsd"));
		
	}
}
