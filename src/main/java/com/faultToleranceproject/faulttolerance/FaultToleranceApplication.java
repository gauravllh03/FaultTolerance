package com.faultToleranceproject.faulttolerance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.faultToleranceproject.faulttolerance.main_application.ServerController;
import com.faultToleranceproject.faulttolerance.main_application.SwingApplication;

@Configuration
@ComponentScan
public class FaultToleranceApplication {
	public static void main(String[] args) throws InterruptedException {
		try(AnnotationConfigApplicationContext ac=new
				AnnotationConfigApplicationContext(FaultToleranceApplication.class)){
					SwingApplication swingApplication=ac.getBean(SwingApplication.class);
					swingApplication.swingapp();
					ServerController serverController=ac.getBean(ServerController.class);
					serverController.waitTillServersStart();
				}
	}
}
