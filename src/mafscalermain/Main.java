package mafscalermain;

import java.awt.*;

import javax.swing.*;

/**
 * Main entry point for the application
 * @author esanders
 *
 */
public class Main {

	static String uploaderTitle = new String("Upload CSV File");
	static String chartTitle = new String("Output Data");
    
    public static void main(final String[] args) {

    	JTextField cipherChoice = new JTextField();
    	JTextField romEditorChoice = new JTextField();
    	JPanel inputPanel = new JPanel();
    	FileUploader fileUploader = new FileUploader(uploaderTitle);
        fileUploader.setVisible(true);
        centerFrameInScreen(fileUploader);

      
    }

    public static void centerFrameInScreen(Container frame) {
    	
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHalfWidth = (int) (((dimension.getWidth() - frame.getWidth()) / 2));
        int screenHalfHeight = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(screenHalfWidth, screenHalfHeight);
        
    }
    
    //I realize this is a redudant method, but it will be edited in the future to reflect another function
    // /*
    public static void setGraphAndTable(Container frame) {
    	
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHalfWidth = (int) (((dimension.getWidth() - frame.getWidth()) / 2));
        int screenHalfHeight = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(screenHalfWidth, screenHalfHeight);
    }
    // */
    
}
