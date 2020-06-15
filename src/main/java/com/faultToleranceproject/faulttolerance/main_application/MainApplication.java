package com.faultToleranceproject.faulttolerance.main_application;

import java.util.LinkedList;
import java.util.Queue;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainApplication {
	private final Method1 method1;
	private final Method2 method2;
	private final Method3 method3;
	private final Method4 method4;
	private final Method5 method5;
	private final Method6 method6;
	private final ServerController serverController;
	private final FibonacciRetry fibonacciRetry;
	private final TPSCalculator tpsCalculator;
	
	private final Queue<Pair<Integer,Long>>requestQueueMethod1=new LinkedList<Pair<Integer,Long>>();
	private final Queue<Pair<Integer,Long>>requestQueueMethod2=new LinkedList<Pair<Integer,Long>>();
	private final Queue<Pair<Integer,Long>>requestQueueMethod3=new LinkedList<Pair<Integer,Long>>();
	private final Queue<Pair<Integer,Long>>requestQueueMethod4=new LinkedList<Pair<Integer,Long>>();
	private final Queue<Pair<Integer,Long>>requestQueueMethod5=new LinkedList<Pair<Integer,Long>>();
	private final Queue<Pair<Integer,Long>>requestQueueMethod6= new LinkedList<Pair<Integer,Long>>();
	private int generatedId=0;
	private int retryCounterMethod1=0;
	private int retryCounterMethod2=0;
	private int retryCounterMethod3=0;
	private int retryCounterMethod4=0;
	private int retryCounterMethod5=0;
	private int retryCounterMethod6=0;

	/*
	 * function to sleep for 1 second till next id is generated
	 */
	
	private void waitTillNextIdGenerated()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
	
	/*
	 * Function to invoke all 6 methods with an ID generated per second and 
	 * use retry strategy in case of false returned either because of throttling
	 *  or because method is in downgraded mode.
	 */
	public void methodInvocationAndRetryStrategy(int dependentServer ,int serverDependencies[]) throws InterruptedException
	{
			/*
			 * Thread for generating id per second and inserting in request
			 * queues of all methods
			 */
			Thread idGenerator = new Thread( new Runnable() {
                @Override
                public void run() {
                	while(serverController.stopServers!=0)
                	{
                		long startTime=System.currentTimeMillis();
                		if(generatedId>=1)
                		{
                			tpsCalculator.StoreTPS(generatedId);			
                		}

            			requestQueueMethod1.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			requestQueueMethod2.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			requestQueueMethod3.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			requestQueueMethod4.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			requestQueueMethod5.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			requestQueueMethod6.add(new Pair<Integer,Long>(generatedId,System.currentTimeMillis()));
            			generatedId++;
            			try {
							Thread.sleep(1000-(System.currentTimeMillis()-startTime));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                	}
                }
            } );
			/*
			 * Separate thread for each method to process requests in request queue when available
			 * and use retry strategy when false is returned
			 */
			Thread method1Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod1.isEmpty()){
    						if(dependentServer==1)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod1.peek().getKey());
    						}
    						if(method1.methodOne(requestQueueMethod1.peek())){
    							retryCounterMethod1=0;
    							requestQueueMethod1.remove();
    						}
    						else{
    								fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod1,1);
    								retryCounterMethod1++;
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		Thread method2Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod2.isEmpty()){
    						if(dependentServer==2)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod2.peek().getKey());
    						}
    						if(method2.methodTwo(requestQueueMethod2.peek())){
    							retryCounterMethod2=0;
    							requestQueueMethod2.remove();
    						}
    						else{
    							fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod2,2);
    							retryCounterMethod2++;
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		Thread method3Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod3.isEmpty()){
    						if(dependentServer==3)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod3.peek().getKey());
    						}
    						if(method3.methodThree(requestQueueMethod3.peek())){
    							retryCounterMethod3=0;
    							requestQueueMethod3.remove();
    						}
    						else{
    							fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod3,3);
    								retryCounterMethod3++;	
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		Thread method4Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod4.isEmpty()){
							if(dependentServer==4)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod4.peek().getKey());
    						}
    						if(method4.methodFour(requestQueueMethod4.peek())){
    							retryCounterMethod4=0;
    							requestQueueMethod4.remove();
    						}
    						else{
    							fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod4,4);
    								retryCounterMethod4++;
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		Thread method5Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod5.isEmpty()){
    						if(dependentServer==5)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod5.peek().getKey());
    						}
    						if(method5.methodFive(requestQueueMethod5.peek())){
    							retryCounterMethod5=0;
    							requestQueueMethod5.remove();
    						}
    						else{
    							fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod5,5);
    								retryCounterMethod5++;
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		Thread method6Thread=new Thread(new Runnable(){
    			@Override
    			public void run(){
    				while(serverController.stopServers!=0){
    					if(!requestQueueMethod6.isEmpty()){
    						if(dependentServer==6)
    						{
    							GenericTrackerForDependency.waitForDependency(serverDependencies,requestQueueMethod6.peek().getKey());
    						}
    						if(method6.methodSix(requestQueueMethod6.peek())){
    							retryCounterMethod6=0;
    							requestQueueMethod6.remove();
    						}
    						else{
    							fibonacciRetry.fibonacciRetryStrategy(retryCounterMethod6,6);
    								retryCounterMethod6++;
    						}
    					}
    					else{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		idGenerator.start();
    		Thread.sleep(100);
    		method1Thread.start();
    		method2Thread.start();
    		method3Thread.start();
    		method4Thread.start();
    		method5Thread.start();
    		method6Thread.start();
    		
    		idGenerator.join();
    		method1Thread.join();
    		method2Thread.join();
    		method3Thread.join();
    		method4Thread.join();
    		method5Thread.join();
    		method6Thread.join();
	}
}
