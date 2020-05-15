package com.faultToleranceproject.faulttolerance.methods;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/*
 * Singleton Class for Method5
 */
@Component
@Scope(value =ConfigurableBeanFactory.SCOPE_SINGLETON) 
public class Method5 {
	
	
	private static int method5Mode = 1;
	private static long lastScheduledTime=System.currentTimeMillis()+350;
	private static int numberOfTrue=0;
	

	
	/*
	 * 
	 * method that returns false when it is in downgraded mode,i.e,
	 * when method5mode=0 and when in normal mode returns true for
	 * only 2 requests per second
	 * @Param numberOfTrue stores no. of true per second
	 * @Param lastScheduledTime stores time of beginning of each second
	 * 
	 */
	public synchronized boolean methodFive() {

		if (method5Mode == 0) {
			System.out.println("Method 5 is down at "+new SimpleDateFormat( "HH:mm:ss" ).format( new Date( System
                    .currentTimeMillis() ) ));
			return false;
		}
		
		else {
			
			if(System.currentTimeMillis()-lastScheduledTime <1000)
			{
				if(numberOfTrue<2)
				{
					System.out.println("Method 5 returned true ->"+
										new SimpleDateFormat( "HH:mm:ss:S" ).format( new Date( System
										.currentTimeMillis() ) ));
					numberOfTrue++;
					return true;
				}
				else
				{
					return false;
				}
			}
			
			else
			{
				/*
				 * beginning of new second
				 */
				
				
				lastScheduledTime=System.currentTimeMillis(); 
				numberOfTrue=1;
				System.out.println("Method 5 returned true ->"+ 
									new SimpleDateFormat( "HH:mm:ss:S" ).format( new Date( System
									.currentTimeMillis() ) ));
				
				return true;
			}
		}
	}
	
	
	/*
	 * Method to set mode of method5 to 0 -> downgrade mode
	 */
	public void flipToDowngradedMode()
	{
			method5Mode=0;
	}
	
	
	/*
	 * Method to set mode of method5 to 1 -> normal mode
	 */
	public void flipToNormalMode()
	{
			method5Mode=1;
	}
	
	
	/*
	 * Method to return mode of method5
	 */
	public int getMode()
	{
		return method5Mode;
	}
}
