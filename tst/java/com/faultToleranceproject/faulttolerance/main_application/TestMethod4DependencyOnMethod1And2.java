package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class TestMethod4DependencyOnMethod1And2 {

	@Autowired
	private MainApplication mainApplication;
	@Autowired
	private ServerController serverController;
	
	/*
	 * Invoking all methods and ig generator thread for 5 seconds and stopping servers
	 * after that and then asserting that visibility time for all id processed by method4
	 * is greater than or equal to that for method1 and method2
	 */
	@Test
	public void test() throws InterruptedException {
		Thread stopServerThread=new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				serverController.stopServers=0;
			}
		});
		stopServerThread.start();
		mainApplication.methodInvocationAndRetryStrategy(4,new int[]{1,2});
		for(int itr=0;itr<Method1.Method1VisibilityMap.size();itr++)
		{	
			assertTrue(Method4.Method4VisibilityMap.get(itr)>=Method1.Method1VisibilityMap.get(itr));
			assertTrue(Method4.Method4VisibilityMap.get(itr)>=Method2.Method2VisibilityMap.get(itr));
			
		}
	}

}
