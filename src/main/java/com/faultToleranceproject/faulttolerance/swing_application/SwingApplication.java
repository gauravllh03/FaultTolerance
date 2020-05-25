package com.faultToleranceproject.faulttolerance.swing_application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faultToleranceproject.faulttolerance.main_application.MainApplication;
import com.faultToleranceproject.faulttolerance.methods.Method1;
import com.faultToleranceproject.faulttolerance.methods.Method2;
import com.faultToleranceproject.faulttolerance.methods.Method3;
import com.faultToleranceproject.faulttolerance.methods.Method4;
import com.faultToleranceproject.faulttolerance.methods.Method5;
import com.faultToleranceproject.faulttolerance.methods.Method6;


@Component
public class SwingApplication{

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
	
	@Autowired
	MainApplication mainApplication;

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
	 * A method to start all servers in the main application
	 */
	
	public void startServers()
	{
		mainApplication.startServers=1;
		System.out.println("Starting all servers !!!!");
	}
	
	
	
	/*
	 * A method to stop all servers in the main application
	 */
	public void stopServers()
	{
		mainApplication.stopServers=0;
		System.out.println("Stopping all servers !!!!");
	}
	
	
	
	
	/*
	 * A method to render the swing GUI :
	 * 1) to take as input which method to downgrade and for what amount of time
	 * 2) Button to control when to start the servers and when to stop them
	 * 3) to take as input method number for which we need to visualize the metrics
	 */
	private void SwingForModeControl()
	{
		
		JFrame mainFrame = new JFrame("Fault Tolerance Project");
		mainFrame.setLayout(new GridLayout(10,2));
        mainFrame.setSize(900,800);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
	    
	    
	    
	    /*
	     * JLabel to display downgrade methods controller heading
	     */
	    JLabel downgradeControllerText = new JLabel("<html><span style='color: teal;'>Method Downgrading Controller</span></html>",SwingConstants.CENTER);
	    downgradeControllerText.setFont (downgradeControllerText.getFont().deriveFont(28.0f));
	    downgradeControllerText.setBorder(new EmptyBorder(50,0,55,0));
	    
	   
	    
	    /*
	     * Panel that contains a dropdown to select
	     * method number that we want to downgrade
	     */
	    
	    JPanel methodDowngradePanel = new JPanel();
	    methodDowngradePanel.setBorder(new EmptyBorder(10, 10, 10,50));
	    
	    JLabel methodNumberLabel = new JLabel("<html><font size='5' color='black'>Select Method to downgrade: </font></html>");
	    methodNumberLabel.setVisible(true);
	   

	    String[] methodChoices = {"1","2","3","4","5","6"};
	    JComboBox<String> methodDropDown = new JComboBox<String>(methodChoices);
	    methodDropDown.setRenderer(new FontCellRenderer());
	    methodDropDown.setVisible(true);
	    
	    methodDowngradePanel.add(methodNumberLabel);
	    methodDowngradePanel.add(methodDropDown);
	    
	    
	    
	    /*
	     * Panel that has a TextField to take as input
	     * time to downgrade selected method
	     */

	    JPanel timePanel = new JPanel();
	    JLabel downgradeTimeLabel = new JLabel("<html><font size='5' color='black'>Time to downgrade: </font></html>");
	    downgradeTimeLabel.setVisible(true);
	    
	    JTextField downgradeTimeTextField= new JTextField(10);
	    
	    timePanel.add(downgradeTimeLabel);
	    timePanel.add(downgradeTimeTextField);
	    
	    
	    /*
	     * Panel that contains a submit button to finalize method number
	     * and time for downgrading that method and onClick downgrades given method
	     * for selected time.
	     */
	    
	    JPanel submitButtonPanel = new JPanel();
	    JButton submitButton = new JButton("OK");
	    submitButton.setBackground(new Color(0x2dce98));
	    submitButton.setUI(new StyledButtononUI());
	    submitButton.setPreferredSize(new Dimension(80,30));
	    submitButton.setMargin(new Insets(150,0,0,0));
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
                        	long downgradeTime=Long.parseLong(downgradeTimeTextField.getText())*1000;
                    		String downgradeMethod=String.valueOf(methodDropDown.getSelectedItem());
                    		downgrade(downgradeMethod,downgradeTime);
                        }
                    } );
            		consumer.start();
            		
            	}
            }
	    });
	    
	    
	    submitButtonPanel.add(submitButton);
	    
	    
	    
	    /*
	     * JLabel for displaying server controller heading
	     */
	    
	    
	    JLabel serverControllerText = new JLabel("<html><span style='color: teal;'>Program Controller</span></html>",SwingConstants.CENTER);
	    serverControllerText.setFont (serverControllerText.getFont().deriveFont(28.0f));
	    serverControllerText.setBorder(new EmptyBorder(45, 20,55, 20));
	    
	    
	    /*
	     * Panel with two  buttons-
	     * A button to start all servers
	     * A button to stop all servers
	     */
	    
	    JPanel serverControlButtonPanel = new JPanel();
	    serverControlButtonPanel.setBorder(new EmptyBorder(10, 10, 10,50));
	    
	    JButton startButton = new JButton("START SERVERS");
	    startButton.setBackground(new Color(0x2dce98));
	    startButton.setUI(new StyledButtononUI());
	    startButton.setPreferredSize(new Dimension(150,40));
	    startButton.setMargin(new Insets(0,0,0,150));
	    startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	{
            		startServers();
            	}
            }
	    });
	    
	    
	    
	    JButton stopButton = new JButton("STOP SERVERS");
	    stopButton.setBackground(new Color(0x2dce98));
	    stopButton.setUI(new StyledButtononUI());
	    stopButton.setPreferredSize(new Dimension(150,40));
	    stopButton.setMargin(new Insets(0,0,0,150));
	    stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	{
            		stopServers();
            	}
            }
	    });
	    
	  
	    
	    serverControlButtonPanel.add(startButton);
	    serverControlButtonPanel.add(stopButton);
	    
	    
	    
	    
	    /*
	     * JLabel for displaying metric visualization controller heading
	     */
	    
	    JLabel metricVisualizationText = new JLabel("<html><span style='color: teal;'>Metric Visualization Controller </span></html>",SwingConstants.CENTER);
	    metricVisualizationText.setFont (metricVisualizationText.getFont().deriveFont(28.0f));
	    metricVisualizationText.setBorder(new EmptyBorder(45, 20,55, 20));
	    
	    
	    
	    
	    /*
	     * Panel with a dropdown to select method whose metrics need to  be visualized.
	     */
	    JPanel metricPanelForMethodSelection = new JPanel();
	    metricPanelForMethodSelection.setBorder(new EmptyBorder(10, 10, 10,50));
	    JLabel methodNumberJLabel = new JLabel("<html><font size='5' color='black'>Select Method to visualize: </font></html>");
	    methodNumberJLabel.setVisible(true);
	    
	    JComboBox<String> methodSelectorForVisualizing = new JComboBox<String>(methodChoices);
	    methodSelectorForVisualizing.setRenderer(new FontCellRenderer());
	    methodSelectorForVisualizing.setVisible(true);
	    
	    metricPanelForMethodSelection.add(methodNumberJLabel);
	    metricPanelForMethodSelection.add(methodSelectorForVisualizing);
	    
	    
	    
	    /*
	     * Panel with a dropdown to select metric to be visualized
	     */
	    JPanel metricPanelForMetricSelection = new JPanel();
	    metricPanelForMetricSelection.setBorder(new EmptyBorder(10, 10, 10,50));
	    
	    JLabel metricToVisualizeJLabel = new JLabel("<html><font size='5' color='black'>Metric to visualize: </font></html>");
	    methodNumberJLabel.setVisible(true);
	    
	    
	    String[] metricChoices = {"interval for id generation and successful processing","false returned per second"};
	    JComboBox<String> metricSelectorForVisualizing = new JComboBox<String>(metricChoices);
	    metricSelectorForVisualizing.setVisible(true);
	    metricSelectorForVisualizing.setRenderer(new FontCellRenderer());
	    
	    
	    metricPanelForMetricSelection.add(metricToVisualizeJLabel);
	    metricPanelForMetricSelection.add(metricSelectorForVisualizing);
	    
	    
	    /*
	     * Panel with button onClicking which selected method's
	     * selected metric is displayed.
	     * To be implemented later
	     */
	    JPanel metricDisplayButtonPanel = new JPanel();
	    JButton displayMetricButton = new JButton("OK");
	    displayMetricButton.setBackground(new Color(0x2dce98));
	    displayMetricButton.setUI(new StyledButtononUI());
	    displayMetricButton.setPreferredSize(new Dimension(80,30));
	    displayMetricButton.setMargin(new Insets(150,0,0,0));
	    displayMetricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	{
            		String methodToVisualize=String.valueOf(methodSelectorForVisualizing.getSelectedItem());
            		String metricToVisualize=String.valueOf(metricSelectorForVisualizing.getSelectedItem());
            		System.out.println(methodToVisualize + "  "+ metricToVisualize);
            	}
            }
	    });
	    
	    metricDisplayButtonPanel.add(displayMetricButton);
	    
	    
	    
	    /*
	     * Adding all panels to the parent mainFrame and making them visible
	     */
	    mainFrame.add(downgradeControllerText);
	    mainFrame.add(methodDowngradePanel);
	    mainFrame.add(timePanel);
	    mainFrame.add(submitButtonPanel);
	    mainFrame.add(serverControllerText);
	    mainFrame.add(serverControlButtonPanel);
	    mainFrame.add(metricVisualizationText);
	    mainFrame.add(metricPanelForMethodSelection);
	    mainFrame.add(metricPanelForMetricSelection);
	    mainFrame.add(metricDisplayButtonPanel);
	    mainFrame.setVisible(true);
	}
	
	
	
	
	public void swingapp() throws InterruptedException
	{
		SwingForModeControl();
	}
}

// class to increase font size in the dropdown menu in swing application
class FontCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
    public java.awt.Component getListCellRendererComponent(
        JList<?> list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {
        JLabel label = (JLabel)super.getListCellRendererComponent(
            list,value,index,isSelected,cellHasFocus);
        Font font = new Font((String)value, Font.BOLD, 14);
        label.setFont(font);
        return label;
    }
}

