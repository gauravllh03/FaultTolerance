package com.faultToleranceproject.faulttolerance.main_application;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javafx.util.Pair;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

enum Method4Mode{
    NORMAL,DOWNGRADE;
};

@Component
@Scope(value =ConfigurableBeanFactory.SCOPE_SINGLETON) 
public class Method4 {
	Method4Mode method4Mode=Method4Mode.NORMAL;
	protected static long lastScheduledTime=System.currentTimeMillis();
	private static int numberOfTrue=0;
	protected static long invocationCounter=0;
	protected final static HashMap<Integer,Integer> Method4VisibilityMap=new HashMap<Integer,Integer>();
	
	/*
	 * function to implement throttling - 2 true/s
	 */
	private boolean throttlingMethod4()
	{
		if(System.currentTimeMillis()-lastScheduledTime<1000)
		{
			numberOfTrue++;
			if(numberOfTrue<=2)
				return true;
			else
				return false;
		}
		else
		{
			lastScheduledTime=System.currentTimeMillis();
			numberOfTrue=1;
			return  false;
		}
	}
	
	/*
	 * @param idPair: Pair of id generated and insertion time of that id in the request queue.
	 * Method to return false when method is in downgrade mode or two requests have been processed for that second
	 * Returns true otherwise.
	 */
	protected synchronized boolean methodFour(Pair<Integer,Long> idPair) {
		invocationCounter++;
		if (method4Mode ==Method4Mode.DOWNGRADE) {
			return false;
		} 
		else {
			if(throttlingMethod4())
			{
				System.out.println("Method 4 returned true for->"+idPair.getKey()+" at "+new SimpleDateFormat( "HH:mm:ss:S" ).format( new Date( System.currentTimeMillis() ) ));
				Method4VisibilityMap.put(idPair.getKey(),(int) ((System.currentTimeMillis()-idPair.getValue())));
				return true;
			}
			else
			{
				if(numberOfTrue>1)
					return false;
				System.out.println("Method 4 returned true for->"+idPair.getKey()+" at "+new SimpleDateFormat( "HH:mm:ss:S" ).format( new Date( System.currentTimeMillis() ) ));
				Method4VisibilityMap.put(idPair.getKey(),(int) ((System.currentTimeMillis()-idPair.getValue())));
				return true;
			}
		}
	}
	
	protected void flipToDowngradedMode()
	{
			method4Mode=Method4Mode.DOWNGRADE;
	}
	
	protected void flipToNormalMode()
	{
			method4Mode=Method4Mode.NORMAL;
	}
	
	protected Method4Mode getMode()
	{
		return method4Mode;
	}
}
