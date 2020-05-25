package com.faultToleranceproject.faulttolerance;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.faultToleranceproject.faulttolerance.main_application.MainApplication;


/*
 * main class to load application context and get all beans 
 * and test their functions
 * 
 */

@Configuration
@ComponentScan
public class FaultToleranceApplication {

	public static void main(String[] args) throws InterruptedException {
		
		try(AnnotationConfigApplicationContext ac=new
				AnnotationConfigApplicationContext(FaultToleranceApplication.class))
				{
					/*
					 * calling the main application function
					 */
					MainApplication mainApplication=ac.getBean(MainApplication.class);
					mainApplication.mainApplicationFunction();
				}
	}
}
