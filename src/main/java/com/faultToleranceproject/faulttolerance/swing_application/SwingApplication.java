package com.faultToleranceproject.faulttolerance.swing_application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faultToleranceproject.faulttolerance.methods.Method1;
import com.faultToleranceproject.faulttolerance.methods.Method2;
import com.faultToleranceproject.faulttolerance.methods.Method3;
import com.faultToleranceproject.faulttolerance.methods.Method4;
import com.faultToleranceproject.faulttolerance.methods.Method5;
import com.faultToleranceproject.faulttolerance.methods.Method6;


@Component
public class SwingApplication {

	@Autowired
	Method1 method1;
	@Autowired
	Method2 method2;
	@Autowired
	Method3 method3;
	@Autowired
	Method4 method4;
	@Autowired
	Method5 method5;
	@Autowired
	Method6 method6;
	

	/* 
	 * to put specified method in
	 * downgrade mode for time specified.  
	 */
	private void sleepMethodForGivenTime(long time )
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/*
	 * method to downgrade given method for given time
	 * by setting it's respective mode variable to 0
	 * 
	 */
	public void downgrade(String methodNumber, long downgradeTime)
	{
		if(methodNumber=="1")
    	{
    		method1.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method1.flipToNormalMode();
    	}
    	else if(methodNumber=="2")
    	{
    		method2.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method2.flipToNormalMode();
    	}
    	else if(methodNumber=="3")
    	{
    		method3.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method3.flipToNormalMode();
    	}
    	else if(methodNumber=="4")
    	{
    		method4.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method4.flipToNormalMode();
    	}
    	else if(methodNumber=="5")
    	{
    		method5.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method5.flipToNormalMode();
    	}
    	else
    	{
    		method6.flipToDowngradedMode();
    		sleepMethodForGivenTime(downgradeTime);
    		method6.flipToNormalMode();
    	}
	}
	
	
	
	/*
	 * A method to render the swing GUI to take as input
	 * which method to downgrade and for what
	 * amount of time
	 * 
	 */
	private void SwingForModeControl()
	{
		JFrame mainFrame = new JFrame("Fault Tolerance Project");
		mainFrame.setLayout(new GridLayout(3,2));
        mainFrame.setSize(500,200);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    
	    JPanel methodPanel = new JPanel();
	    JLabel methodNumberLabel = new JLabel("Select Method to downgrade: ");
	    methodNumberLabel.setFont(new Font("Courier", Font.BOLD, 16));
	    methodNumberLabel.setForeground(Color.BLACK);
	    methodNumberLabel.setVisible(true);
	   

	    String[] methodChoices = {"1","2","3","4","5","6"};
	    JComboBox<String> methodDropDown = new JComboBox<String>(methodChoices);
	    methodDropDown.setVisible(true);
	    
	    methodPanel.add(methodNumberLabel);
	    methodPanel.add(methodDropDown);
	    

	    JPanel timePanel = new JPanel();
	    JLabel downgradeTimeLabel = new JLabel("Time to downgrade: ");
	    downgradeTimeLabel.setFont(new Font("Courier", Font.BOLD,16));
    	downgradeTimeLabel.setForeground(Color.BLACK);
	    downgradeTimeLabel.setVisible(true);
	    
	    JTextField timeTextField= new JTextField(10);
	    
	    timePanel.add(downgradeTimeLabel);
	    timePanel.add(timeTextField);
	    
	    
	    JPanel buttonPanel = new JPanel();
	    JButton submitButton = new JButton("OK");
	    submitButton.setBackground(new Color(0x2dce98));
	    submitButton.setUI(new StyledButtononUI());
	    submitButton.setPreferredSize(new Dimension(80,30));
	    submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	{
            		/*
            		 * Downgrading in separate thread so that Swing's 
            		 * GUI doesn't freeze + multiple methods can be
            		 * downgraded together
            		 * 
            		 */
            		Thread consumer = new Thread( new Runnable() {
                        @Override
                        public void run() {
                        	long downgradeTime=Long.parseLong(timeTextField.getText())*1000;
                    		String downgradeMethod=String.valueOf(methodDropDown.getSelectedItem());
                    		downgrade(downgradeMethod,downgradeTime);
                        }
                    } );
            		consumer.start();
            		
            	}
            }
	    });
	    
	    
	    buttonPanel.add(submitButton);
	    
	    mainFrame.add(methodPanel);
	    mainFrame.add(timePanel);
	    mainFrame.add(buttonPanel);
	    
	    mainFrame.setVisible(true);
	}
	
	
	
	/* 
	 * a method to test working of task 1
	 */
	public void swingapp() throws InterruptedException
	{
		SwingForModeControl();
		while(true)
		{
			method1.methodOne();
			/*
			method2.methodTwo();
			method3.methodThree();
			method4.methodFour();
			method5.methodFive();
			method6.methodSix();
			System.out.println();*/
		}
	}
}
