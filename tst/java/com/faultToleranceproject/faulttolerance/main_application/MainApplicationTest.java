package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;
import javafx.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class MainApplicationTest {

	@Mock
	private Method1 method1;
		
	@InjectMocks
	private FibonacciRetry fibonacciRetry;
	
	/*
	 * Test for Retry Strategy: We bombard method for 1second and check that 3 retry 
	 * attempts are made each second in fibonacci backoff time intervals (200,300,500).
	 */
	
	@Test
	public void MainApplicationRetryStrategyTest()
	{
		Mockito.when(method1.methodOne(new Pair<Integer,Long>(Mockito.anyInt(),System.currentTimeMillis()))).thenReturn(true).thenReturn(true).thenReturn(false);
		int falseCounter=0;
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<1000){
			if(!method1.methodOne(new Pair<Integer,Long>(1,System.currentTimeMillis()))){
				fibonacciRetry.fibonacciRetryStrategy(falseCounter,1);
				falseCounter++;
			}	
		}
		assertEquals(3,falseCounter);
	}
}
