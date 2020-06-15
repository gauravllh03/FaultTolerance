package com.faultToleranceproject.faulttolerance.main_application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.swing.JButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.faultToleranceproject.faulttolerance.FaultToleranceApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class SwingApplicationTest {
	
	@Autowired
	private SwingApplication swingApplication;
	@Autowired
	private Method1 method1;
	@Autowired
	private ServerController serverController;

	/*
	 * Mocking the JButton class and on clicking it calling the downgrade method to 
	 * downgrade Method 1 for 10s and checking if method is in downgraded mode for next 10s
	 * After 10s reconfirming that the method is in normal mode again
	 */
	@Test
	public void MethodDowngradeControllerTest() throws InterruptedException {
	
		JButton downgradeButton=mock(JButton.class);
		doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("You clicked downgrade method button");
                Thread thread = new Thread( new Runnable() {
        			@Override
                    public void run() {
        				swingApplication.downgrade("1",10000);
                    }
            	});
            	thread.start();
                return null;
            }
        }).when(downgradeButton).doClick();
		downgradeButton.doClick();
    	 while(method1.getMode()!=Method1Mode.DOWNGRADE);
		 final long startTime=System.currentTimeMillis();
		 while(System.currentTimeMillis()-startTime<10000){
			 assertEquals(Method1Mode.DOWNGRADE,method1.getMode());
		 }
		 Thread.sleep(1000);
		 assertEquals(Method1Mode.NORMAL,method1.getMode());
	}
	
	/*
	 *Mocking the JButton class and on clicking it calling the stopServers() method 
	 *and checking that all servers have stopped 
	 */	
	@Test
	public void StopServersTest()
	{
		JButton stopServersButton = mock(JButton.class);
		doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("You clicked stop servers button");
                swingApplication.stopServers();
                return null;
            }
        }).when(stopServersButton).doClick();
		stopServersButton.doClick();
		assertEquals(0,serverController.getStopServersValue());
	}
	
	/*
	 *Mocking the JButton class and on clicking it calling the startServers() method 
	 *and checking if all servers have started working 
	 */
	@Test
	public void StartServersTest()
	{
		JButton startServersButton = mock(JButton.class);
		doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("You clicked start servers button");
                swingApplication.startServers();
                return null;
            }
        }).when(startServersButton).doClick();
		startServersButton.doClick();
		assertEquals(1,serverController.getStartServersValue());
	}
	
}
