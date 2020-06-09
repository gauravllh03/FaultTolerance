package com.faultToleranceproject.faulttolerance.main_application;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PlotMaxVisibility extends JFrame{
	private static final long serialVersionUID = 6294689542092367723L;  
	public PlotMaxVisibility(String title) {  
	    super(title);   
	    XYDataset dataset = createDataset();  
	    JFreeChart chart = ChartFactory.createScatterPlot(  
	        "Maximum Visibility Time",   
	        "id", "max visibility", dataset);    
	    XYPlot plot = (XYPlot)chart.getPlot();  
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	    XYItemRenderer renderer = plot.getRenderer();
	    renderer.setSeriesPaint(0, Color.red);
	    double size = 3.0;
	    double delta = size / 2.0;
	    Shape shape1 = new Ellipse2D.Double(-delta, -delta, size, size);
	    renderer.setSeriesShape(0, shape1);
	    ChartPanel panel = new ChartPanel(chart);  
	    setContentPane(panel);  
	  }  
	  
	  private XYDataset createDataset() {  
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    XYSeries series = new XYSeries("All Methods");
	    int size=Method1.Method1VisibilityMap.size();
	   
	    for(int itr=0;itr<size-2;itr++)
	    {
	    	int maximumForGivenId=Math.max(Method6.Method6VisibilityMap.get(itr),Math.max(Method5.Method5VisibilityMap.get(itr),Math.max(Method4.Method4VisibilityMap.get(itr)/1000,Math.max(Method3.Method3VisibilityMap.get(itr),Math.max(Method1.Method1VisibilityMap.get(itr)/1000,Method2.Method2VisibilityMap.get(itr)/1000)))));
	    	series.add(itr,maximumForGivenId);
	    }
	    dataset.addSeries(series);
	    return dataset;  
	 }  
}
