package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.faultToleranceproject.faulttolerance.methods.Method1;


@RunWith(MockitoJUnitRunner.class)
public class MainApplicationTest {

	@Mock
	Method1 method1;
	
	@InjectMocks
	MainApplication mainApplication;

	
	/*
	 * Test for Retry Strategy 
	 * We bombard method for 1second and check that
	 * 3 retry attempts are made each second in fibonacci
	 * backoff time intervals (200,300,500).
	 * Finally, number of false returned per second=3
	 */
	
	@Test
	public void MainApplicationRetryStrategyTest()
	{
		Mockito.when(method1.methodOne(Mockito.anyInt())).thenReturn(true).thenReturn(true).thenReturn(false);
		int falseCounter=0;
		mainApplication.prepareFibonacciTimeIntervals();
		long startTime=System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<1000)
		{
			if(!method1.methodOne(1))
			{
				mainApplication.fibonacciRetryStrategy(falseCounter,1);
				falseCounter++;
			}	
		}
		assertEquals(3,falseCounter);
	}
}
