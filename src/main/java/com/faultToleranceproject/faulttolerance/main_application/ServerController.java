package com.faultToleranceproject.faulttolerance.main_application;

import org.springframework.stereotype.Component;

@Component
public class ServerController {
	protected int stopServers=1;
	protected int startServers=0;
	
	/*
	 * Function to wait till start servers button is clicked
	 */
	public void waitTillServersStart()
	{
		while(startServers!=1)
		{
			System.out.print("");
		}
	}
	
	protected int getStopServersValue()
	{
		return stopServers;
	}
	
	protected int getStartServersValue()
	{
		return startServers;
	}

}
