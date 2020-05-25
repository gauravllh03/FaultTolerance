package com.faultToleranceproject.faulttolerance.methods;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)

public class Method1Test {

	@Autowired
	Method1 method1Instance1;
	
	@Autowired
	Method1 method1Instance2;
	
	
	/*
	 * Test with multiple instances of Method1 class
	 * Because the class is Singleton, calling the method
	 * using both object continuously for 1 second but number of true
	 * is still 2
	 * Even with multiple calls from multiple objects, the method will only process 2
	 * requests in a second.
	 */
	
	@Test
	public void test() throws InterruptedException {
		
		
		//checking when method is in downgrade mode
		method1Instance1.flipToDowngradedMode();
		assertEquals(0,method1Instance1.getMode());
		assertEquals(false,method1Instance1.methodOne(1));
		
		
		/*
		 * calling method with multiple instances when method is in
		 * normal mode continuously for
		 * 1s and checking if number of true is 2
		 */
		method1Instance1.flipToNormalMode();
		int truecounter=0;
		
		long startTime=System.currentTimeMillis();
		Method1.lastScheduledTime=System.currentTimeMillis()+10;
		while(System.currentTimeMillis()-startTime<1000)
		{
			if(method1Instance1.methodOne(1))
				truecounter++;
			if(method1Instance2.methodOne(2))
				truecounter++;
		}
		
		assertEquals(2,truecounter);
	}

}
