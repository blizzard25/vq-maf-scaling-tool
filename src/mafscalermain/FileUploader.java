package mafscalermain;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class FileUploader extends JFrame {
    
    public FileUploader(String title) {
    	
    	super(title);
	   	setSize(350, 100);
	   	setDefaultCloseOperation(EXIT_ON_CLOSE);
	   	setResizable(false);
	   	Container c = getContentPane();
	   	c.setLayout(new FlowLayout());    
	   	JButton openButton = new JButton("Open File");
	   	JButton quitButton = new JButton("Close App");
	   
	   final JLabel appStatusOut = new JLabel(
    		"File pathname will display here on selection");

	   //Display File Chooser Panel when user clicks Open button
	   openButton.addActionListener(new ActionListener() {
		   
		   public void actionPerformed(ActionEvent ae) {
			   
			   JFileChooser fileChooser = new JFileChooser();
			   fileChooser.setMultiSelectionEnabled(false);
			   
			   int option = fileChooser.showOpenDialog(FileUploader.this);
			   			   
			   if (option == JFileChooser.APPROVE_OPTION) {
				   
				   File file = fileChooser.getSelectedFile();
				   String path = new String(file.getPath());				   
				   appStatusOut.setText(path);
				   CsvReadAndStore.parseCsvFile(path);
				   float[][] test = CsvReadAndStore.getFinalData();
				   double[] multArray = CsvReadAndStore.getMultArr();
					
					ChartDisplay.generateGraphData(test);
					ChartDisplay.generateRowData(multArray);
					ChartDisplay.generateOutputPanel(test);
					
					Main.setGraphAndTable(new ChartDisplay("Title",test,multArray));
					//System.out.println(multArray.toString());
				   
			   } else {
				   
          appStatusOut.setText("Upload Canceled");
          
        }
			   
      }
 
    });

	   //Quit application when user clicks button
	   quitButton.addActionListener(new ActionListener() {
		   
		   public void actionPerformed(ActionEvent ae) {
			   
			   System.exit(ABORT);
			   
		   }
		   
	   });
	   
	   c.add(openButton);
	   c.add(quitButton);
	   c.add(appStatusOut);
	   
   }
}