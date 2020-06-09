package com.faultToleranceproject.faulttolerance.main_application;

import org.springframework.stereotype.Component;

@Component
public class FibonacciRetry {
	private final static int fibonacciTimeSeriesForRetrying[]=new int[10001];
	
	FibonacciRetry()
	{
		fibonacciTimeSeriesForRetrying[0]=200;
		fibonacciTimeSeriesForRetrying[1]=300;
		for(int i=2;i<10000;i++)
		{
			fibonacciTimeSeriesForRetrying[i]=fibonacciTimeSeriesForRetrying[i-1]+fibonacciTimeSeriesForRetrying[i-2];
		}
	}
	
	/*
	 * @param retryCounter:Helps to determine interval to wait for before next attempt
	 * @param methodNumber:determines for which method a retry attempt is to be made.
	 */
	protected void fibonacciRetryStrategy(int retryCounter,int methodNumber)
	{
			System.out.println("Retry attempt "+(retryCounter+1)+" for method"+methodNumber);
			try {
				Thread.sleep(fibonacciTimeSeriesForRetrying[retryCounter]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
	}
}
