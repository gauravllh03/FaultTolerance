package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;
import javafx.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class Method3Test {
	
	@Autowired
	private Method3 method3Instance1,method3Instance2;
	
	/*
	 * 1.testing if method returns false when in downgraded mode
	 * 2.testing if it returns maximum 2 true in a second even when bombarded by multiple instances
	 */
	@Test
	public void test() {
		method3Instance1.flipToDowngradedMode();
		assertEquals(Method3Mode.DOWNGRADE,method3Instance1.getMode());
		assertEquals(false,method3Instance1.methodThree(new Pair<Integer,Long>(1,System.currentTimeMillis())));
		method3Instance1.flipToNormalMode();
		int truecounter=0;
		long startTime=System.currentTimeMillis();
		Method3.lastScheduledTime=System.currentTimeMillis()+10;
		while(System.currentTimeMillis()-startTime<1000){
			if(method3Instance1.methodThree(new Pair<Integer,Long>(1,System.currentTimeMillis())))
				truecounter++;
			if(method3Instance2.methodThree(new Pair<Integer,Long>(2,System.currentTimeMillis())))
				truecounter++;
		}
		assertEquals(2,truecounter);
	}

}
