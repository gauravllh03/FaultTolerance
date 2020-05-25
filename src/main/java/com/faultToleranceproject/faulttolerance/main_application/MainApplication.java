package com.faultToleranceproject.faulttolerance.main_application;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faultToleranceproject.faulttolerance.methods.Method1;
import com.faultToleranceproject.faulttolerance.methods.Method2;
import com.faultToleranceproject.faulttolerance.methods.Method3;
import com.faultToleranceproject.faulttolerance.methods.Method4;
import com.faultToleranceproject.faulttolerance.methods.Method5;
import com.faultToleranceproject.faulttolerance.methods.Method6;
import com.faultToleranceproject.faulttolerance.swing_application.SwingApplication;

@Component
public class MainApplication {
	
	@Autowired
	SwingApplication swingApplication;
	
	
	@Autowired
	Method1 method1;
	@Autowired
	Method2 method2;
	@Autowired
	Method3 method3;
	@Autowired
	Method4 method4;
	@Autowired
	Method5 method5;
	@Autowired
	Method6 method6;
	
	
	/*
	 * request queues for all methods to cache requests being generated continuously
	 */
	private Queue<Integer>requestQueueMethod1=new LinkedList<>();
	private Queue<Integer>requestQueueMethod2=new LinkedList<>();
	private Queue<Integer>requestQueueMethod3=new LinkedList<>();
	private Queue<Integer>requestQueueMethod4=new LinkedList<>();
	private Queue<Integer>requestQueueMethod5=new LinkedList<>();
	private Queue<Integer>requestQueueMethod6=new LinkedList<>();
	
	
	/*
	 * time intervals to be tried between successive retry attempts
	 */
	
	private int fibonacciTimeSeriesForRetrying[]=new int[10001];
	
	
	private int generatedId=0;
	
	/*
	 * variables controlling starting and stopping of servers.
	 * stopServers=0 => servers will be stopped
	 * startServer=1 => servers will be started
	 */
	public int stopServers=1;
	public int startServers=0;
	
	
	
	/*
	 * counter for number of retries made by a specific method
	 */
	private int retryCounterMethod1=0;
	private int retryCounterMethod2=0;
	private int retryCounterMethod3=0;
	private int retryCounterMethod4=0;
	private int retryCounterMethod5=0;
	private int retryCounterMethod6=0;
	
	
	
	
	/*
	 * Function to display swing application, prepare
	 * fibonacci time interval series and then invoke 
	 * all methods when start servers button is clicked
	 * and use retry strategy when any method goes down
	 */
	
	public void mainApplicationFunction() throws InterruptedException
	{
		//starting the swing application
		swingApplication.swingapp();
		
		System.out.println("Welcome to Fault Tolerance Project");
		
		// waiting for start servers button to be clicked 
		while(startServers!=1)
		{
			System.out.print("");
		}
		
		
		prepareFibonacciTimeIntervals();
		
		/*
		 * calling function that starts all servers and id generation
		 */
		methodInvocationAndRetryStrategy();
		
	}
	
	
	/*
	 * Function to prepare the fibonacci time interval series
	 */
	public void prepareFibonacciTimeIntervals()
	{
		fibonacciTimeSeriesForRetrying[0]=200;
		fibonacciTimeSeriesForRetrying[1]=300;
		for(int i=2;i<10000;i++)
		{
			fibonacciTimeSeriesForRetrying[i]=fibonacciTimeSeriesForRetrying[i-1]+fibonacciTimeSeriesForRetrying[i-2];
		}
	}
	
	
	
	public int getStopServersValue()
	{
		return stopServers;
	}
	
	public int getStartServersValue()
	{
		return startServers;
	}
	
	
	
	/*
	 * Function for retry strategy.
	 * Retry attempts are made for with a successive interval
	 * between consecutive retries in accordance to fibonacci backoff
	 */
	
	public void fibonacciRetryStrategy(int retryCounter,int methodNumber)
	{
			System.out.println("Retry attempt "+(retryCounter+1)+" for method"+methodNumber);
			try {
				Thread.sleep(fibonacciTimeSeriesForRetrying[retryCounter]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}
	
	
	
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
	 * Function for generating an id per second and invoking all methods
	 * simultaneously for generated id until a true is returned for that id.
	 * Uses retry strategy for methods which have more than 1 requests pending
	 * in their request queues.
	 */
	
	
	private void methodInvocationAndRetryStrategy() throws InterruptedException
	{
			
		
		
			/*
			 * Thread for generating id per second and inserting in request
			 * queues of all methods
			 */
			Thread idGenerator = new Thread( new Runnable() {
                @Override
                public void run() {
                	while(stopServers!=0)
                	{
                		long startTime=System.currentTimeMillis();
            			//System.out.println(generatedId+" -> "+new SimpleDateFormat( "HH:mm:ss" ).format( new Date( System
            				//				.currentTimeMillis() ) ));
            			requestQueueMethod1.add(generatedId);
            			requestQueueMethod2.add(generatedId);
            			requestQueueMethod3.add(generatedId);
            			requestQueueMethod4.add(generatedId);
            			requestQueueMethod5.add(generatedId);
            			requestQueueMethod6.add(generatedId);
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
			 * Separate threads for each method where they check if a request is
			 * available in request queue. If yes ,they process 2 requests max in
			 * a second and then use retry strategy before attempting to return true
			 * for pending requests in next second. 
			 * 
			 */
			
			Thread method1Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					if(!requestQueueMethod1.isEmpty())
    					{
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    						if(method1.methodOne(requestQueueMethod1.peek()))
    						{
    							retryCounterMethod1=0;
    							requestQueueMethod1.remove();
    						}
    						else
    						{
    								fibonacciRetryStrategy(retryCounterMethod1,1);
    								retryCounterMethod1++;
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		
    		
    		
    		
    		Thread method2Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					
    					if(!requestQueueMethod2.isEmpty())
    					{
    						
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    						
    						if(method2.methodTwo(requestQueueMethod2.peek()))
    						{
    							retryCounterMethod2=0;
    							requestQueueMethod2.remove();
    							
    						}
    						else
    						{
    							fibonacciRetryStrategy(retryCounterMethod2,2);
    							retryCounterMethod2++;
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		
    		
    		Thread method3Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					
    					if(!requestQueueMethod3.isEmpty())
    					{
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    						if(method3.methodThree(requestQueueMethod3.peek()))
    						{
    							retryCounterMethod3=0;
    							requestQueueMethod3.remove();
    							
    						}
    						else
    						{
    								fibonacciRetryStrategy(retryCounterMethod3,3);
    								retryCounterMethod3++;
    								
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		
    		Thread method4Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					
    					if(!requestQueueMethod4.isEmpty())
    					{
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    					
    						if(method4.methodFour(requestQueueMethod4.peek()))
    						{
    							retryCounterMethod4=0;
    							requestQueueMethod4.remove();
    							
    						}
    						else
    						{
    								fibonacciRetryStrategy(retryCounterMethod4,4);
    								retryCounterMethod4++;
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		
    		
    		Thread method5Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					
    					if(!requestQueueMethod5.isEmpty())
    					{
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    					
    						if(method5.methodFive(requestQueueMethod5.peek()))
    						{
    							retryCounterMethod5=0;
    							requestQueueMethod5.remove();
    							
    						}
    						else
    						{
    								fibonacciRetryStrategy(retryCounterMethod5,5);
    								retryCounterMethod5++;
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		
    		
    		Thread method6Thread=new Thread(new Runnable(){
    			@Override
    			public void run()
    			{
    				while(stopServers!=0)
    				{
    					
    					if(!requestQueueMethod6.isEmpty())
    					{
    						/*
    						 * if a true is returned resetting retryCounter to 0
    						 * and popping id which has been successfully processed.
    						 */
    						
    						if(method6.methodSix(requestQueueMethod6.peek()))
    						{
    							retryCounterMethod6=0;
    							requestQueueMethod6.remove();
    							
    						}
    						else
    						{
    								fibonacciRetryStrategy(retryCounterMethod6,6);
    								retryCounterMethod6++;
    						}
    					}
    					else
    					{
    						waitTillNextIdGenerated();
    					}
    				}
    			}
    		});
    		
    		
    		
    		idGenerator.start();
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
    		
    		/*
    		 * After the stop servers button is clicked displaying
    		 * pending requests for all methods.
    		 */
    		
    		System.out.println("Pending requests for method1 "+requestQueueMethod1);
    		System.out.println("Pending requests for method2 "+requestQueueMethod2);
    		System.out.println("Pending requests for method3 "+requestQueueMethod3);
    		System.out.println("Pending requests for method4 "+requestQueueMethod4);
    		System.out.println("Pending requests for method5 "+requestQueueMethod5);
    		System.out.println("Pending requests for method6 "+requestQueueMethod6);
    		
	}
}
