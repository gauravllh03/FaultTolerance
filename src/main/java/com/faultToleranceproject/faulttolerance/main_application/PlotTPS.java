package com.faultToleranceproject.faulttolerance.main_application;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PlotTPS extends JFrame {
	  private static final long serialVersionUID = 6294689542092367723L;  
	  protected static int methodNumber=1;
	  protected static 	Long previousValue=0L;
	  public PlotTPS(String title) {  
	    super(title);  
	    XYDataset dataset = createDataset();  
	    JFreeChart chart = ChartFactory.createScatterPlot(  
	        "TPS",   
	        "second number", "Number of invocations", dataset);  
	    XYPlot plot = (XYPlot)chart.getPlot();  
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	    XYItemRenderer renderer = plot.getRenderer();
	    renderer.setSeriesPaint(0, Color.red);
	    double size = 3.0;
	    double delta = size / 2.0;
	    Shape shape1 = new Ellipse2D.Double(-delta, -delta, size, size);
	    renderer.setSeriesShape(0, shape1);
	    
	    NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0.0, 6.0);
        range.setTickUnit(new NumberTickUnit(0.5));
	    
	    
	    ChartPanel panel = new ChartPanel(chart);  
	    setContentPane(panel);  
	  }  
	  
	  private XYDataset createDataset() {  
	    XYSeriesCollection dataset = new XYSeriesCollection();  
	    
	    /*
	     * Feeding tps of all methods and plotting
	     */
	    
	    if(methodNumber==1)
	    {
	    	XYSeries series1 = new XYSeries("Method1");
	    	TPSCalculator.TPSmethod1.forEach((k, v) -> {Long x=v-previousValue;series1.add(k,x);previousValue=v;} );
	    	dataset.addSeries(series1);
	    }
	    else if(methodNumber==2)
	    {
	    	XYSeries series2 = new XYSeries("Method2");
	    	TPSCalculator.TPSmethod2.forEach((k, v) -> {Long x=v-previousValue;series2.add(k,x);previousValue=v;});
	    	dataset.addSeries(series2);
	    }
	    else if(methodNumber==3)
	    {
	    	XYSeries series3 = new XYSeries("Method3");
	    	TPSCalculator.TPSmethod3.forEach((k, v) -> {Long x=v-previousValue;series3.add(k,x);previousValue=v;} );
	    	dataset.addSeries(series3);
	    }
	    else if(methodNumber==4)
	    {
	    	XYSeries series4 = new XYSeries("Method4");
	    	TPSCalculator.TPSmethod4.forEach((k, v) -> {Long x=v-previousValue;series4.add(k,x);previousValue=v;} );
	    	dataset.addSeries(series4);
	    }
	    else if(methodNumber==5)
	    {
	    	XYSeries series5 = new XYSeries("Method5");
	    	TPSCalculator.TPSmethod5.forEach((k, v) ->{Long x=v-previousValue;series5.add(k,x);previousValue=v;} );
	    	dataset.addSeries(series5);
	    }
	    else
	    {
	    	XYSeries series6 = new XYSeries("Method6");
	    	TPSCalculator.TPSmethod6.forEach((k, v) -> {Long x=v-previousValue;series6.add(k,x);previousValue=v;} );
	    	dataset.addSeries(series6);
	    }
	    return dataset;  
	  }  
}

