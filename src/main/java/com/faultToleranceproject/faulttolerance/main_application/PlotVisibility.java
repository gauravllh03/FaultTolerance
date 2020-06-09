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



public class PlotVisibility extends JFrame {  
  private static final long serialVersionUID = 6294689542092367723L;  
  protected static int methodNumber=1;
  public PlotVisibility(String title) {  
    super(title);   
    XYDataset dataset = createDataset();  
  
    JFreeChart chart = ChartFactory.createScatterPlot(  
        "Visibility Time",   
        "id", "visibility", dataset);  
    
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
    
    /*
     * Feeding visibilty time of all ids in request queues of respective methods
     */
    if(methodNumber==1)
    {
    	XYSeries series1 = new XYSeries("Method1");
    	Method1.Method1VisibilityMap.forEach((k, v) ->{v=v/1000; series1.add(k,v);} );
    	dataset.addSeries(series1);
    }
    else if(methodNumber==2)
    {
    	XYSeries series2 = new XYSeries("Method2");
    	Method2.Method2VisibilityMap.forEach((k, v) ->{v=v/1000; series2.add(k,v);} );
    	dataset.addSeries(series2);
    }
    else if(methodNumber==3)
    {
    	XYSeries series3 = new XYSeries("Method3");
    	Method3.Method3VisibilityMap.forEach((k, v) ->{ series3.add(k,v); });
    	dataset.addSeries(series3);
    }
    else if(methodNumber==4)
    {
    	XYSeries series4 = new XYSeries("Method4");
    	Method4.Method4VisibilityMap.forEach((k, v) ->{v=v/1000; series4.add(k,v);} );
    	dataset.addSeries(series4);
    }
    else if(methodNumber==5)
    {
    	XYSeries series5 = new XYSeries("Method5");
    	Method5.Method5VisibilityMap.forEach((k, v) -> series5.add(k,v) );
    	dataset.addSeries(series5);
    }
    else
    {
    	XYSeries series6 = new XYSeries("Method6");
    	Method6.Method6VisibilityMap.forEach((k, v) -> series6.add(k,v) );
    	dataset.addSeries(series6);
    }
    return dataset;  
  }  
}
