package mafscalermain;

import javax.swing.*;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

/**
 * Currently this class is just a placeholder. But eventually 
 * this will contain the generated output graph and table in the same pane.
 * I chose JFreeChart but I honestly don't have a preference
 * if you're assisting in the GUI
 * @author esanders
 *
 */
@SuppressWarnings("serial")
public class ChartDisplay extends JFrame {
	
	public ChartDisplay(final String title, float[][] data, double[] mArr) {		
		
		super(title);


		Object rowStuff[][] = generateRowData(mArr);
		Object[] columnNames = generateColumnNames();
		//Object[] firstCol = new Object[columnNames.length / 5];
		//for(int i = 0; i < columnNames.length / 5; i++) {
			//firstCol[i] = columnNames[i];
		//}
		JTable table = new JTable(rowStuff, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(350,30);
		JPanel chartPanel = generateOutputPanel(data);
		JFrame frame = new JFrame();
		frame.add(chartPanel,BorderLayout.EAST);
		frame.add(scrollPane,BorderLayout.WEST);
		frame.setSize(640,480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.isForegroundSet();
		
	}

	//Just creating some random data values to put in the temporary graph
    public static XYDataset generateGraphData(float[][] data) {
    	
    	XYSeriesCollection dataset = new XYSeriesCollection();
    	XYSeries seriesAllData = new XYSeries("MAF V vs Corrections");
    	for(int i = 0; i < data.length; i++) {
    		seriesAllData.add(data[i][0],data[i][1]);
    	}
    	
    	dataset.addSeries(seriesAllData);
    	return dataset;
    }
    
    public static JPanel generateOutputPanel(float[][] data) {
    	String graphTitle = "MAF/Corrections";
    	String xAxisTitle = "MAF Voltage (V)";
    	String yAxisTitle = "A/F Corrections (%)";
    	
    	XYDataset dataset = generateGraphData(data);
    	JFreeChart chart = ChartFactory.createScatterPlot(graphTitle,
                xAxisTitle, yAxisTitle, dataset);

    	XYPlot plot = chart.getXYPlot();
    	ValueAxis domainAxis = plot.getDomainAxis();
    	ValueAxis rangeAxis = plot.getRangeAxis();
    	domainAxis.setRange(0.00,5.00);
    	rangeAxis.setRange(75.0,125.0);
    	plot.setOutlinePaint(Color.BLUE);
    	plot.setOutlineStroke(new BasicStroke(2.0f));
    	plot.setBackgroundPaint(Color.DARK_GRAY);
    	plot.setRangeGridlinesVisible(true);
    	plot.setRangeGridlinePaint(Color.BLACK);   	 
    	plot.setDomainGridlinesVisible(true);
    	plot.setDomainGridlinePaint(Color.BLACK);
    	
    	return new ChartPanel(chart);
    }
    
    public static Object[] generateColumnNames() {
    	Object colName[] = {"MAF Voltage", "Multiplier"};
		return colName;
    }
    public static Object[][] generateRowData(double[] mArr) {
    	
    	Object[][] rowData = new Object[mArr.length][2];
		Object columnNames[] = {
	    		".08 (V)",
	    		".16 (V)",
	    		".23 (V)",
	    		".31 (V)",
	    		".39 (V)",
	    		".47 (V)",
	    		".55 (V)",
	    		".63 (V)",
	    		".70 (V)",
	    		".78 (V)",
	    		".86 (V)",
	    		".94 (V)",
	    		"1.02 (V)",
	    		"1.09 (V)",
	    		"1.17 (V)",
	    		"1.25 (V)",
	    		"1.33 (V)",
	    		"1.41 (V)",
	    		"1.48 (V)",
	    		"1.56 (V)",
	    		"1.64 (V)",
	    		"1.72 (V)",
	    		"1.80 (V)",
	    		"1.88 (V)",
	    		"1.95 (V)",
	    		"2.03 (V)",
	    		"2.11 (V)",
	    		"2.19 (V)",
	    		"2.27 (V)",
	    		"2.34 (V)",
	    		"2.42 (V)",
	    		"2.50 (V)",
	    		"Open Loop (> 2.50 (V))"
	    		};
    	for(int i = 0; i < mArr.length; i++) {
    		rowData[i][1] = (float)mArr[i];
    		rowData[i][0] = columnNames[i];
    	}
    	return rowData;
    }
    
    
}
