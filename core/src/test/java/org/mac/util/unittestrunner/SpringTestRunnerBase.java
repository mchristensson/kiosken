package org.mac.util.unittestrunner;

import org.junit.runner.RunWith;
import org.mac.util.unittestrunner.config.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=Beans.class)
public abstract class SpringTestRunnerBase {
	
	@Autowired
    protected ApplicationContext applicationContext;

}
