package com.faultToleranceproject.faulttolerance.main_application;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class TPSCalculator {
	protected final static HashMap<Integer,Long>TPSmethod1=new HashMap<>();
	protected final static HashMap<Integer,Long>TPSmethod2=new HashMap<>();
	protected final static HashMap<Integer,Long>TPSmethod3=new HashMap<>();
	protected final static HashMap<Integer,Long>TPSmethod4=new HashMap<>();
	protected final static HashMap<Integer,Long>TPSmethod5=new HashMap<>();
	protected final static HashMap<Integer,Long>TPSmethod6=new HashMap<>();
	
	/*
	 * stores number of transactions for each second for each method and then feeds it to the
	 * plotting framework
	 */
	protected void StoreTPS(int id)
	{
		/*
		 * called every second to store transactions count in that second and is fed to the 
		 * plotting framework later
		 */
		TPSmethod1.put(id,Method1.invocationCounter);
		TPSmethod2.put(id,Method2.invocationCounter);
		TPSmethod3.put(id,Method3.invocationCounter);
		TPSmethod4.put(id,Method4.invocationCounter);
		TPSmethod5.put(id,Method5.invocationCounter);
		TPSmethod6.put(id,Method6.invocationCounter);
	}
}
