package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;
import javafx.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class MainApplicationRetryTest {
	private Method1 method1;
	private FibonacciRetry fibonacciRetry;
	
	@Before
	public void setUp() throws Exception {
	      method1=new Method1();
	      fibonacciRetry=new FibonacciRetry();
	 }
	
	/*
	 * Test for Retry Strategy considering throttling: Continuously bombarding method1 
	 * for 3 seconds and checking 6 trues are returned (2 true/second) and 9 false returned
	 * while retrying in fibonacci backoff time gaps (200ms ,300ms,500ms) = (3 false/secon
	 */
	@Test
	public void retryStrategyTest() {
		int retryCounter=0,trueCounter=0,falseCounter=0;
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<3000){
			if(!method1.methodOne((new Pair<Integer,Long>(trueCounter,System.currentTimeMillis())))){
				falseCounter++;
				fibonacciRetry.fibonacciRetryStrategy(retryCounter,1);
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
