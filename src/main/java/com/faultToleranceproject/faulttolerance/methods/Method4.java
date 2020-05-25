package com.faultToleranceproject.faulttolerance.methods;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/*
 * Singleton Class for Method4
 */
@Component
@Scope(value =ConfigurableBeanFactory.SCOPE_SINGLETON) 
public class Method4 {
	
	private static int method4Mode = 1;
	public static long lastScheduledTime=System.currentTimeMillis();
	private static int numberOfTrue=0;
	
	
	
	/*
	 * 
	 * method that returns false when it is in downgraded mode,i.e,
	 * when method4mode=0 and when in normal mode returns true for
	 * only 2 requests per second
	 * @Param numberOfTrue stores no. of true per second
	 * @Param lastScheduledTime stores time of beginning of each second
	 * 
	 */
	
	public synchronized boolean methodFour(int id) {
		if (method4Mode == 0) {
			// downgraded mode
			return false;
		} 
		
		else {
			
			if(System.currentTimeMillis()-lastScheduledTime <1000)
			{
				if(numberOfTrue<2)
				{
					System.out.println("Method 4 returned true for->"+id+" at "+
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
				System.out.println("Method 4 returned true for->"+id+" at "+ 
									new SimpleDateFormat( "HH:mm:ss:S" ).format( new Date( System
									.currentTimeMillis() ) ));
				return true;
			}
		}
	}
	
	
	/*
	 * Method to set mode of method4 to 0 -> downgrade mode
	 */
	public void flipToDowngradedMode()
	{
			method4Mode=0;
	}
	
	/*
	 * Method to set mode of method4 to 1 -> normal mode
	 */
	public void flipToNormalMode()
	{
			method4Mode=1;
	}
	
	/*
	 * Method to return mode of method4
	 */
	public int getMode()
	{
		return method4Mode;
	}
}
