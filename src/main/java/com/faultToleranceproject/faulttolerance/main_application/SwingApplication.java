package com.faultToleranceproject.faulttolerance.main_application;

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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwingApplication{	
	private final Method1 method1;
	private final Method2 method2;
	private final Method3 method3;
	private final Method4 method4;
	private final Method5 method5;
	private final Method6 method6;
	private final ServerController serverController;
	private final MainApplication mainApplication;
	
	private void sleepMethodForGivenTime(long time )
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	/*
	 * @param methodNumber:method to downgrade
	 * @param downgradeTime:Time for which method is to be downgraded
	 */
	protected void downgrade(String methodNumber, long downgradeTime)
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
	protected void startServers()
	{
		serverController.startServers=1;
		System.out.println("Starting all servers !!!!");
	}
	
	protected void stopServers()
	{
		serverController.stopServers=0;
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
		mainFrame.setLayout(new GridLayout(14,2));
        mainFrame.setSize(900,900);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
	    
	    JLabel serverDependencyControllerText = new JLabel("<html><span style='color: teal;'>Server Dependency Controller</span></html>",SwingConstants.CENTER);
	    serverDependencyControllerText.setFont (serverDependencyControllerText.getFont().deriveFont(28.0f));
	    serverDependencyControllerText.setBorder(new EmptyBorder(50,0,55,0));
	    
	    JPanel dependentServerPanel = new JPanel();
	    dependentServerPanel.setBorder(new EmptyBorder(10, 10, 10,50));
	    JLabel dependentMethodLabel = new JLabel("<html><font size='5' color='black'>Dependent method:</font></html>");
	    dependentMethodLabel.setVisible(true);
	    JTextField dependentMethodTextField= new JTextField(10);
	    dependentServerPanel.add(dependentMethodLabel);
	    dependentServerPanel.add(dependentMethodTextField);
	    
	    JPanel serverDependenciesPanel = new JPanel();
	    serverDependenciesPanel.setBorder(new EmptyBorder(10, 10, 10,50));
	    JLabel MethodDependenciesLabel = new JLabel("<html><font size='5' color='black'>Dependencies:</font></html>");
	    MethodDependenciesLabel.setVisible(true);
	    JTextField MethodDependenciesTextField= new JTextField(10);
	    serverDependenciesPanel.add(MethodDependenciesLabel);
	    serverDependenciesPanel.add(MethodDependenciesTextField);
	    
	    
	    JPanel submitDependencyButtonPanel = new JPanel();
	    JButton submitDependencyButton= new JButton("OK");
	    submitDependencyButton.setBackground(new Color(0x2dce98));
	    submitDependencyButton.setUI(new StyledButtononUI());
	    submitDependencyButton.setPreferredSize(new Dimension(80,30));
	    submitDependencyButton.setMargin(new Insets(150,0,0,0));
	    submitDependencyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	{
            		Thread startServerWithDependencyThread= new Thread( new Runnable() {
                        @Override
                        public void run() {
                        	int dependentMethod;
                    		if(dependentMethodTextField.getText().equals(""))
                    			dependentMethod=0;
                    		else
                    			dependentMethod=Integer.parseInt(dependentMethodTextField.getText());
                    		if(MethodDependenciesTextField.getText().equals(""))
                    		{
                    			serverController.waitTillServersStart();
                    			try {
        							mainApplication.methodInvocationAndRetryStrategy(dependentMethod,new int[]{0});
        						} catch (InterruptedException e1) {
        							e1.printStackTrace();
        						}
                    		}
                    		else
                    		{
        	            		int dependencies[]=new int[6];
        	            		int dependencyCounter=0;
        	            		String methodDependencies=MethodDependenciesTextField.getText();
        	            		for(int i=0;i<methodDependencies.length();i++)
        	            		{
        	            			if(methodDependencies.charAt(i)!=',')
        	            			{
        	            				dependencies[dependencyCounter]=methodDependencies.charAt(i)-'0';
        	            				dependencyCounter++;
        	            			}
        	            		}
        	            		serverController.waitTillServersStart();
        	            		try {
        							mainApplication.methodInvocationAndRetryStrategy(dependentMethod,dependencies);
        						} catch (InterruptedException e1) {
        							e1.printStackTrace();
        						}
                    		}
                        }
                    } );
            		startServerWithDependencyThread.start();
            	}
            }
	    });
	    submitDependencyButtonPanel.add(submitDependencyButton);
	    
	    
	    
	    JLabel downgradeControllerText = new JLabel("<html><span style='color: teal;'>Method Downgrading Controller</span></html>",SwingConstants.CENTER);
	    downgradeControllerText.setFont (downgradeControllerText.getFont().deriveFont(28.0f));
	    downgradeControllerText.setBorder(new EmptyBorder(50,0,55,0));
	    
	    /*
	     * Panel that contains a dropdown to select method number that we want to downgrade
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
	     * Panel that has a TextField to take as input time to downgrade selected method
	     */
	    JPanel timePanel = new JPanel();
	    JLabel downgradeTimeLabel = new JLabel("<html><font size='5' color='black'>Time to downgrade: </font></html>");
	    downgradeTimeLabel.setVisible(true);
	    JTextField downgradeTimeTextField= new JTextField(10);
	    timePanel.add(downgradeTimeLabel);
	    timePanel.add(downgradeTimeTextField);
	    /*
	     * Panel that contains a submit button to finalize method number and time for
	     * downgrading that method and onClick downgrades given method for selected time.
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
            		 * Downgrading in separate thread so that Swing's GUI doesn't freeze + multiple methods
            		 *  can be downgraded together
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
	      
	    JLabel serverControllerText = new JLabel("<html><span style='color: teal;'>Program Controller</span></html>",SwingConstants.CENTER);
	    serverControllerText.setFont (serverControllerText.getFont().deriveFont(28.0f));
	    serverControllerText.setBorder(new EmptyBorder(45, 20,55, 20));
	    
	    /*
	     * Panel with two  buttons- A button to start all servers and a button to stop all servers
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
	    String[] metricChoices = {"visibility time in queue","tps of method","max visibilty of id"};
	    JComboBox<String> metricSelectorForVisualizing = new JComboBox<String>(metricChoices);
	    metricSelectorForVisualizing.setVisible(true);
	    metricSelectorForVisualizing.setRenderer(new FontCellRenderer());
	    metricPanelForMetricSelection.add(metricToVisualizeJLabel);
	    metricPanelForMetricSelection.add(metricSelectorForVisualizing);
	   
	    /*
	     * Panel with button onClicking which selected method's selected metric is displayed.
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
            		int methodToVisualize=Integer.parseInt(String.valueOf(methodSelectorForVisualizing.getSelectedItem()));
            		String metricToVisualize=String.valueOf(metricSelectorForVisualizing.getSelectedItem());
            		System.out.println(methodToVisualize + "  "+ metricToVisualize);
            		if(metricToVisualize.equals("visibility time in queue"))
            		{
            			PlotVisibility.methodNumber=methodToVisualize;
            			SwingUtilities.invokeLater(() -> {  
            	  		      PlotVisibility plotVisibility = new PlotVisibility("Visibility in Queue");  
            	  		      plotVisibility.setSize(1000, 800);  
            	  		      plotVisibility.setLocationRelativeTo(null);    
            	  		  	  plotVisibility.setVisible(true);  
            	  		 }); 
            		}
            		else if(metricToVisualize.equals("max visibilty of id"))
            		{
            			SwingUtilities.invokeLater(() -> {  
            	  		      PlotMaxVisibility plotMaxVisibilty = new PlotMaxVisibility("Max Visibilty for an ID");  
            	  		      plotMaxVisibilty.setSize(1000, 800);  
            	  		      plotMaxVisibilty.setLocationRelativeTo(null);    
            	  		      plotMaxVisibilty.setVisible(true);  
            	  		 }); 
            		}
            		else
            		{
            			PlotTPS.methodNumber=methodToVisualize;
            			PlotTPS.previousValue=0L;
            			SwingUtilities.invokeLater(() -> {  
            	  		      PlotTPS plotTPS = new PlotTPS("TPS");  
            	  		      plotTPS.setSize(1000, 800);  
            	  		      plotTPS.setLocationRelativeTo(null);    
            	  		      plotTPS.setVisible(true);  
            	  		 }); 
            		}
            	}
            }
	    });
	    
	    metricDisplayButtonPanel.add(displayMetricButton);
	    
	    /*
	     * Adding all panels to the parent mainFrame and making them visible
	     */
	    mainFrame.add(serverDependencyControllerText);
	    mainFrame.add(dependentServerPanel);
	    mainFrame.add(serverDependenciesPanel);
	    mainFrame.add(submitDependencyButtonPanel);
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

/*
 *  class to increase font size in the dropdown menu in swing application
 */
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

