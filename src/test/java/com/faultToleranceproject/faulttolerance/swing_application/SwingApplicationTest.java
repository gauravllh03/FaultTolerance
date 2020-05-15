package com.faultToleranceproject.faulttolerance.swing_application;

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
import com.faultToleranceproject.faulttolerance.methods.Method1;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FaultToleranceApplication.class)
public class SwingApplicationTest {
	
	@Autowired
	private SwingApplication swingApplication;
	@Autowired
	private Method1 method1;
	
	
	/*
	 * Mocking the JButton class and on clicking it calling the 
	 * downgrade method to downgrade Method 1 for 10s and
	 * checking value of mode of Method1 for next 10s if it is equal to
	 * 0 or not.
	 * After 10s reconfirming that the method is in normal mode again,i.e,
	 * mode of Method1 is 1.
	 */
	
	@Test
	public void Test() throws InterruptedException {
	
		JButton downgradeButton=mock(JButton.class);
		
		
		doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("You clicked me");
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
        
        
    	 while(method1.getMode()!=0);
    	 
    	 /*
    	  * checking if method1 is downgraded for next 10s after button is clicked
    	  */
    	 
		 final long startTime=System.currentTimeMillis();
		 while(System.currentTimeMillis()-startTime<10000)
		 {
			 assertEquals(0,method1.getMode());
		 }
		 Thread.sleep(1000);
		 
		 /*
		  * Checking if Method1 is in normal mode after 10s 
		  */
		 assertEquals(1,method1.getMode());
	}

}
