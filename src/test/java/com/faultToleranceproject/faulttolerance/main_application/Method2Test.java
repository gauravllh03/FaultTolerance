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
public class Method2Test {
	@Autowired
	private Method2 method2Instance1,method2Instance2;
	
	/*
	 * 1.testing if method returns false when in downgraded mode
	 * 2.testing if it returns maximum 2 true in a second even when bombarded by multiple instances
	 */
	@Test
	public void test() throws InterruptedException {
		method2Instance1.flipToDowngradedMode();
		assertEquals(Method2Mode.DOWNGRADE,method2Instance1.getMode());
		assertEquals(false,method2Instance1.methodTwo(new Pair<Integer,Long>(1,System.currentTimeMillis())));
		method2Instance1.flipToNormalMode();
		int truecounter=0;
		long startTime=System.currentTimeMillis();
		Method2.lastScheduledTime=System.currentTimeMillis()+10;
		while(System.currentTimeMillis()-startTime<1000){
			if(method2Instance1.methodTwo(new Pair<Integer,Long>(1,System.currentTimeMillis())))
				truecounter++;
			if(method2Instance2.methodTwo(new Pair<Integer,Long>(2,System.currentTimeMillis())))
				truecounter++;
		}
		assertEquals(2,truecounter);
	}

}
