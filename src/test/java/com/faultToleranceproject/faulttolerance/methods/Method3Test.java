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
public class Method3Test {
	
	@Autowired
	Method3 method3Instance1;
	
	@Autowired
	Method3 method3Instance2;
	

	/*
	 * Test with multiple instances of Method3 class
	 * Because the class is Singleton, calling the method
	 * using both object continuously for 1 second but number of true
	 * is still 2
	 * Even with multiple calls from multiple objects, the method will only process 2
	 * requests in a second.
	 */
	@Test
	public void test() {
		
		//checking when method is in downgrade mode
		method3Instance1.flipToDowngradedMode();
		assertEquals(0,method3Instance1.getMode());
		assertEquals(false,method3Instance1.methodThree());
		
		
		/*
		 * calling method with multiple instances when method is in
		 * normal mode continuously for
		 * 1s and checking if number of true is 2
		 */
		method3Instance1.flipToNormalMode();
		int truecounter=0;
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<1000)
		{
			if(method3Instance1.methodThree())
				truecounter++;
			
			if(method3Instance2.methodThree())
				truecounter++;
		}
		assertEquals(2,truecounter);
	}

}
