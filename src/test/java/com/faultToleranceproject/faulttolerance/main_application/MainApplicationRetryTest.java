package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;
import com.faultToleranceproject.faulttolerance.methods.Method1;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class MainApplicationRetryTest {

	@Autowired
	Method1 method1;
	
	@Autowired
	MainApplication mainApplication;
	
	
	
	/*
	 * Test for Retry Strategy considering throttling
	 * Continuously bombarding method1 for 3 seconds and
	 * checking 6 trues are returned (2 true/second) and
	 * 9 false returned while retrying in fibonacci backoff 
	 * time gaps (200ms ,300ms,500ms) = (3 false/second)
	 * Finally, number of false returned=9
	 * number of true returned=6
	 */
	
	@Test
	public void retryStrategyTest() {
		
		int retryCounter=0,trueCounter=0,falseCounter=0;
		mainApplication.prepareFibonacciTimeIntervals();
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<3000){
			if(!method1.methodOne(trueCounter)){
				falseCounter++;
				mainApplication.fibonacciRetryStrategy(retryCounter,1);
				retryCounter++;
			}
			else{
				trueCounter++;
				retryCounter=0;
			}
		}
		assertEquals(9,falseCounter);
		assertEquals(6,trueCounter);
	}

}
