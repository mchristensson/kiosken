package org.mac.util.unittestrunner;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mac.util.unittestrunner.config.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=Beans.class)
public class SpringTestRunner {
	
	@Autowired
    private ApplicationContext applicationContext;
	
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
}
