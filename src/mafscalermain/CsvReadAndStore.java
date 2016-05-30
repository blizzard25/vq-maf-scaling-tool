package mafscalermain;

import java.io.*;

import java.util.*;

import org.apache.commons.csv.*;

/**
 * This class reads the CSV file, parses the CSV file using
 * Apache Commons CSV, generates data bins using the parsed data, and
 * manipulates the data for final output
 * @author esanders
 *
 */
public class CsvReadAndStore {
    
    private static final String RPM = "ENGINE RPM (rpm)";
    private static final String BFS = "Base Fuel Schedule";
    private static final String MAFV = "MAS A/F -B1 (V)";
    private static final String FC1 = "A/F CORR-B1 (%)"; 
    private static final String FC2 = "A/F CORR-B2 (%)";	
    private static final String ACCELPOS = "ACCEL PED POS 1 (V-Accel)";
    private static final String TARGETAFR = "TARGET AFR";
    private static final String ACTUALAFRBANK1 = "AFR WB-B1";
    private static final String ACTUALAFRBANK2 = "AFR WB-B2";
    
    
    public static int counter08, counter16, counter23, counter31, counter39,
    	counter47, counter55, counter63, counter70, counter78, counter86, 
    	counter94, counter102, counter109, counter117, counter125, counter133,
    	counter141, counter148, counter156, counter164, counter172, counter180,
    	counter188, counter195, counter203, counter211, counter219, counter227, 
    	counter234, counter242, counter250, counterOpenLoop, afrCount, totalCount;
    
    public static double mult08, mult16, mult23, mult31, mult39,
	mult47, mult55, mult63, mult70, mult78, mult86, 
	mult94, mult102, mult109, mult117, mult125, mult133,
	mult141, mult148, mult156, mult164, mult172, mult180,
	mult188, mult195, mult203, mult211, mult219, mult227, mult234,
	mult242, mult250, multOpenLoop;
    
    public static double[] multArr = {mult08, mult16, mult23, mult31, mult39,
    	mult47, mult55, mult63, mult70, mult78, mult86, 
    	mult94, mult102, mult109, mult117, mult125, mult133,
    	mult141, mult148, mult156, mult164, mult172, mult180,
    	mult188, mult195, mult203, mult211, mult219, mult227, mult234, 
    	mult242, mult250, multOpenLoop};
    
    public static float[][] finalData = new float[totalCount][2];
    
    public CsvReadAndStore() {
    	getFinalData();
    	getMultArr();
    }
    
    public static float[][] getFinalData() {
    	return finalData;
    }
    
    public static double[] getMultArr() {
    	return multArr;
    }
    
    //calculates the average fuel correction value for a given MAF voltage bin
    public static float findAvg(float[][] mafBinVolts) {
    	float sum = 0;
    	for(int i = 0; i < mafBinVolts.length; i++) {
    		sum = sum + mafBinVolts[i][1];
    	}
    	double mafBinAvg = ((double)sum / mafBinVolts.length) / 100;
    	return (float)mafBinAvg;
    }
    
    //finds the percentage difference between the target AFR and the actual AFR
    public static float findAFRDiff(float[][] afrBin) {
    	float sum = 0;
    	float[] percentDiff = new float[afrBin.length];
    	for(int i = 0; i < percentDiff.length; i++) {
    		percentDiff[i] = (((afrBin[i][1] - afrBin[i][0]) / 
    				((afrBin[i][1] + afrBin[i][0]) / 2)) * 100);
    		sum = sum + percentDiff[i];
    	}
    	double percentAvg = ((double)sum / percentDiff.length) / 100;
    	return (float)percentAvg;
    }
    
    //parses an uploaded CSV file and manipulates the data
    public static void parseCsvFile(String fileName) {
     
    	CSVParser csvFileParser = null;
        FileReader fileReader = null;
        
        try {
        	
        	CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader();
            	fileReader = new FileReader(fileName);
        	csvFileParser = new CSVParser(fileReader, csvFileFormat);
        	List<CSVRecord> csvRecords = csvFileParser.getRecords();
        	
            float[][] csvData = new float[csvRecords.size()][5];
          
            for (int i = 1; i < csvRecords.size(); i++) {
            
            	
            	CSVRecord record = csvRecords.get(i);

            	//creates an object to hold a single data set
            	VariableRetriever v = new VariableRetriever(
            			Float.parseFloat(record.get(RPM)),
            			Float.parseFloat(record.get(BFS)),
            			Float.parseFloat(record.get(MAFV)),
            			Float.parseFloat(record.get(FC1)),
            			Float.parseFloat(record.get(FC2)),
            			Float.parseFloat(record.get(ACCELPOS)),
            			Float.parseFloat(record.get(TARGETAFR)),
            			Float.parseFloat(record.get(ACTUALAFRBANK1)),
            			Float.parseFloat(record.get(ACTUALAFRBANK2))
            			);
            	
            	csvData[i-1][0] = v.getMafVolts();
            	csvData[i-1][1] = v.getFC1();
            	csvData[i-1][2] = v.getAccelPos();
            	csvData[i-1][3] = v.getTargetAFR();
            	csvData[i-1][4] = v.getActualAFRB1();
            		
            	
            		if (v.getMafVolts() < 0.09 && v.getMafVolts() > 0.0
            				&& v.getAccelPos() > 1.00) {  
            			counter08++;
            		
        		} else if (v.getMafVolts() < .17 && v.getMafVolts() > .08
        				&& v.getAccelPos() > 1.00) {
            			counter16++;
            		
        		} else if (v.getMafVolts() < .24 && v.getMafVolts() > .16
        				&& v.getAccelPos() > 1.00) { 
        			counter23++;
        			
        		} else if (v.getMafVolts() < .32 && v.getMafVolts() > .23
        				&& v.getAccelPos() > 1.00) {   
        			counter31++;
        			
        		} else if (v.getMafVolts() < .40 && v.getMafVolts() > .31
        				&& v.getAccelPos() > 1.00) {
        			counter39++;
        			
        		} else if (v.getMafVolts() < .48 && v.getMafVolts() > .39
        				&& v.getAccelPos() > 1.00) { 
        			counter47++;
        			
        		} else if (v.getMafVolts() < .56 && v.getMafVolts() > .47
        				&& v.getAccelPos() > 1.00) {   
        			counter55++;
        			
        		} else if (v.getMafVolts() < .64 && v.getMafVolts() > .55
        				&& v.getAccelPos() > 1.00) {
        			counter63++;
        			
        		} else if (v.getMafVolts() < .71 && v.getMafVolts() > .63
        				&& v.getAccelPos() > 1.00) { 
        			counter70++;
        			
        		} else if (v.getMafVolts() < .79 && v.getMafVolts() > .70
        				&& v.getAccelPos() > 1.00) {   
        			counter78++;
        			
        		} else if (v.getMafVolts() < .87 && v.getMafVolts() > .78
        				&& v.getAccelPos() > 1.00) {
        			counter86++;
        			
        		} else if (v.getMafVolts() < .95 && v.getMafVolts() > .86
        				&& v.getAccelPos() > 1.00) { 
        			counter94++;
        			
        		} else if (v.getMafVolts() < 1.03 && v.getMafVolts() > .94
        				&& v.getAccelPos() > 1.00) {   
        			counter102++;
        			
        		} else if (v.getMafVolts() < 1.10 && v.getMafVolts() > 1.02
        				&& v.getAccelPos() > 1.00) {
        			counter109++;
        			
        		} else if (v.getMafVolts() < 1.18 && v.getMafVolts() > 1.09
        				&& v.getAccelPos() > 1.00) { 
        			counter117++;
        			
        		} else if (v.getMafVolts() < 1.26 && v.getMafVolts() > 1.17
        				&& v.getAccelPos() > 1.00) {   
        			counter125++;
        			
        		} else if (v.getMafVolts() < 1.34 && v.getMafVolts() > 1.25
        				&& v.getAccelPos() > 1.00) {
        			counter133++;
        			
        		} else if (v.getMafVolts() < 1.42 && v.getMafVolts() > 1.33
        				&& v.getAccelPos() > 1.00) { 
        			counter141++;
        			
        		} else if (v.getMafVolts() < 1.49 && v.getMafVolts() > 1.40
        				&& v.getAccelPos() > 1.00) {  
            			counter148++;
            		
        		} else if (v.getMafVolts() < 1.57 && v.getMafVolts() > 1.48
        				&& v.getAccelPos() > 1.00) {
        			counter156++;
        			
        		} else if (v.getMafVolts() < 1.65 &&v.getMafVolts() > 1.56
        				&& v.getAccelPos() > 1.00) { 
        			counter164++;
        			
        		} else if (v.getMafVolts() < 1.73 && v.getMafVolts() > 1.64
        				&& v.getAccelPos() > 1.00) {   
        			counter172++;
        			
        		} else if (v.getMafVolts() < 1.81 && v.getMafVolts() > 1.72
        				&& v.getAccelPos() > 1.00) {
        			counter180++;
        			
        		} else if (v.getMafVolts() < 1.89 && v.getMafVolts() > 1.80
        				&& v.getAccelPos() > 1.00) { 
        			counter188++;
        			
        		} else if (v.getMafVolts() < 1.96 && v.getMafVolts() > 1.88
        				&& v.getAccelPos() > 1.00) { 
        			counter195++;
        			
        		} else if (v.getMafVolts() < 2.04 && v.getMafVolts() > 1.95
        				&& v.getAccelPos() > 1.00) {   
        			counter203++;
        			
        		} else if (v.getMafVolts() < 2.12 && v.getMafVolts() > 2.03
        				&& v.getAccelPos() > 1.00) {
        			counter211++;
        			
        		} else if (v.getMafVolts() < 2.20 && v.getMafVolts() > 2.11
        				&& v.getAccelPos() > 1.00) { 
        			counter219++;
        			
        		} else if (v.getMafVolts() < 2.28 && v.getMafVolts() > 2.19
        				&& v.getAccelPos() > 1.00) {   
        			counter227++;
        			
        		} else if (v.getMafVolts() < 2.35 && v.getMafVolts() > 2.27
        				&& v.getAccelPos() > 1.00) {   
        			counter234++;
        			
        		} else if (v.getMafVolts() < 2.43 && v.getMafVolts() > 2.34
        				&& v.getAccelPos() > 1.00) {
        			counter242++;
        			
        		} else if (v.getMafVolts() < 2.51 && v.getMafVolts() > 2.42
        				&& v.getAccelPos() > 1.00) {
        			counter250++;
        			
        		} else if (v.getMafVolts() > 2.50 && v.getAccelPos() > 1.00) {
        			counterOpenLoop++;
        			
        		}
                
		}
            
            //sum of the maf bin counters
            totalCount = counter08 + counter16 + counter23 + counter31 + counter39 +
                	counter47 + counter55 + counter63 + counter70 + counter78 + counter86 + 
                	counter94 + counter102 + counter109 + counter117 + counter125 + counter133 +
                	counter141 + counter148 + counter156 + counter164 + counter172 + counter180 +
                	counter188 + counter195 + counter203 + counter211 + counter219 + counter227 + 
                	counter234 + counter242 + counter250 + counterOpenLoop;
            
            //System.out.println("1.48 counter: " + counter148);
            //System.out.println("1.56 counter: " + counter156);
            //System.out.println("1.64 counter: " + counter164);
            //System.out.println("Total counter: " + totalCount);

		
            float[][] tempFinalData = new float[totalCount][2];
            int refTempFinal = 0;
            float[][] dataBin08 = new float[counter08][2];
            int ref08 = 0;
            float[][] dataBin16 = new float[counter16][2];
            int ref16 = 0;
            float[][] dataBin23 = new float[counter23][2];
            int ref23 = 0;
            float[][] dataBin31 = new float[counter31][2];
            int ref31 = 0;
            float[][] dataBin39 = new float[counter39][2];
            int ref39 = 0;
            float[][] dataBin47 = new float[counter47][2];
            int ref47 = 0;
            float[][] dataBin55 = new float[counter55][2];
            int ref55 = 0;
            float[][] dataBin63 = new float[counter63][2];
            int ref63 = 0;
            float[][] dataBin70 = new float[counter70][2];
            int ref70 = 0;
            float[][] dataBin78 = new float[counter78][2];
            int ref78 = 0;
            float[][] dataBin86 = new float[counter86][2];
            int ref86 = 0;
            float[][] dataBin94 = new float[counter94][2];
            int ref94 = 0;
            float[][] dataBin102 = new float[counter102][2];
            int ref102 = 0;
            float[][] dataBin109 = new float[counter109][2];
            int ref109 = 0;
            float[][] dataBin117 = new float[counter117][2];
            int ref117 = 0;
            float[][] dataBin125 = new float[counter125][2];
            int ref125 = 0;
            float[][] dataBin133 = new float[counter133][2];
            int ref133 = 0;
            float[][] dataBin141 = new float[counter141][2];
            int ref141 = 0;
            float[][] dataBin148 = new float[counter148][2];
            int ref148 = 0;
            float[][] dataBin156 = new float[counter156][2];
            int ref156 = 0;
            float[][] dataBin164 = new float[counter164][2];
            int ref164 = 0;
            float[][] dataBin172 = new float[counter172][2];
            int ref172 = 0;
            float[][] dataBin180 = new float[counter180][2];
            int ref180 = 0;
            float[][] dataBin188 = new float[counter188][2];
            int ref188 = 0;
            float[][] dataBin195 = new float[counter195][2];
            int ref195 = 0;
            float[][] dataBin203 = new float[counter203][2];
            int ref203 = 0;
            float[][] dataBin211 = new float[counter211][2];
            int ref211 = 0;
            float[][] dataBin219 = new float[counter219][2];
            int ref219 = 0;
            float[][] dataBin227 = new float[counter227][2];
            int ref227 = 0;
            float[][] dataBin234 = new float[counter234][2];
            int ref234 = 0;
            float[][] dataBin242 = new float[counter242][2];
            int ref242 = 0;
            float[][] dataBin250 = new float[counter250][2];
            int ref250 = 0;
            float[][] dataBinOL = new float[counterOpenLoop][2];
            int refOL = 0;
            
            for(int i = 0; i < csvRecords.size(); i++) {
            	if (csvData[i][0] < .09 && csvData[i][0] > 0.0
            			&& csvData[i][2] > 1.00) {  
            		dataBin08[ref08][0] = csvData[i][0];
            		dataBin08[ref08][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref08++;
        		} else if (csvData[i][0] < .17 && csvData[i][0] > .08
        				&& csvData[i][2] > 1.00) {
            		dataBin16[ref16][0] = csvData[i][0];
            		dataBin16[ref16][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref16++;
        		} else if (csvData[i][0] < .24 && csvData[i][0] > .16
        				&& csvData[i][2] > 1.00) { 
            		dataBin23[ref23][0] = csvData[i][0];
            		dataBin23[ref23][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref23++;
        		} else if (csvData[i][0] < .32 && csvData[i][0] > .23
        				&& csvData[i][2] > 1.00) {   
            		dataBin31[ref31][0] = csvData[i][0];
            		dataBin31[ref31][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref31++;
        		} else if (csvData[i][0] < .40 && csvData[i][0] > .31
        				&& csvData[i][2] > 1.00) {
            		dataBin39[ref39][0] = csvData[i][0];
            		dataBin39[ref39][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref39++;
        		} else if (csvData[i][0] < .48 && csvData[i][0] > .39
        				&& csvData[i][2] > 1.00) { 
            		dataBin47[ref47][0] = csvData[i][0];
            		dataBin47[ref47][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref47++;
        		} else if (csvData[i][0] < .56 && csvData[i][0] > .47
        				&& csvData[i][2] > 1.00) {   
            		dataBin55[ref55][0] = csvData[i][0];
            		dataBin55[ref55][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref55++;
        		} else if (csvData[i][0] < .64 && csvData[i][0] > .55
        				&& csvData[i][2] > 1.00) {
            		dataBin63[ref63][0] = csvData[i][0];
            		dataBin63[ref63][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref63++;
        		} else if (csvData[i][0] < .71 && csvData[i][0] > .63
        				&& csvData[i][2] > 1.00) { 
            		dataBin70[ref70][0] = csvData[i][0];
            		dataBin70[ref70][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref70++;
        		} else if (csvData[i][0] < .79 && csvData[i][0] > .70
        				&& csvData[i][2] > 1.00) {   
            		dataBin78[ref78][0] = csvData[i][0];
            		dataBin78[ref78][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref78++;
        		} else if (csvData[i][0] < .87 && csvData[i][0] > .78
        				&& csvData[i][2] > 1.00) {
            		dataBin86[ref86][0] = csvData[i][0];
            		dataBin86[ref86][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref86++;
        		} else if (csvData[i][0] < .95 && csvData[i][0] > .86
        				&& csvData[i][2] > 1.00) { 
            		dataBin94[ref94][0] = csvData[i][0];
            		dataBin94[ref94][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref94++;
        		} else if (csvData[i][0] < 1.03 && csvData[i][0] > .94
        				&& csvData[i][2] > 1.00) {   
            		dataBin102[ref102][0] = csvData[i][0];
            		dataBin102[ref102][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref102++;
        		} else if (csvData[i][0] < 1.10 && csvData[i][0] > 1.02
        				&& csvData[i][2] > 1.00) {
            		dataBin109[ref109][0] = csvData[i][0];
            		dataBin109[ref109][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref109++;
        		} else if (csvData[i][0] < 1.18 && csvData[i][0] > 1.09
        				&& csvData[i][2] > 1.00) { 
            		dataBin117[ref117][0] = csvData[i][0];
            		dataBin117[ref117][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref117++;
        		} else if (csvData[i][0] < 1.26 && csvData[i][0] > 1.17
        				&& csvData[i][2] > 1.00) {   
            		dataBin125[ref125][0] = csvData[i][0];
            		dataBin125[ref125][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref125++;
        		} else if (csvData[i][0] < 1.34 && csvData[i][0] > 1.25
        				&& csvData[i][2] > 1.00) {
            		dataBin133[ref133][0] = csvData[i][0];
            		dataBin133[ref133][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref133++;
        		} else if (csvData[i][0] < 1.42 && csvData[i][0] > 1.33
        				&& csvData[i][2] > 1.00) { 
            		dataBin141[ref141][0] = csvData[i][0];
            		dataBin141[ref141][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref141++;
        		} else if (csvData[i][0] < 1.49 && csvData[i][0] > 1.41
        				&& csvData[i][2] > 1.00) {   
            		dataBin148[ref148][0] = csvData[i][0];
            		dataBin148[ref148][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref148++;
        		} else if (csvData[i][0] < 1.57 && csvData[i][0] > 1.48
        				&& csvData[i][2] > 1.00) {
            		dataBin156[ref156][0] = csvData[i][0];
            		dataBin156[ref156][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref156++;
        		} else if (csvData[i][0] < 1.65 && csvData[i][0] > 1.56
        				&& csvData[i][2] > 1.00) { 
            		dataBin164[ref164][0] = csvData[i][0];
            		dataBin164[ref164][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref164++;
        		} else if (csvData[i][0] < 1.73 && csvData[i][0] > 1.64
        				&& csvData[i][2] > 1.00) {   
            		dataBin172[ref172][0] = csvData[i][0];
            		dataBin172[ref172][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref172++;
        		} else if (csvData[i][0] < 1.81 && csvData[i][0] > 1.72
        				&& csvData[i][2] > 1.00) {
            		dataBin180[ref180][0] = csvData[i][0];
            		dataBin180[ref180][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref180++;
        		} else if (csvData[i][0] < 1.89 && csvData[i][0] > 1.80
        				&& csvData[i][2] > 1.00) { 
            		dataBin188[ref188][0] = csvData[i][0];
            		dataBin188[ref188][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref188++;
        		} else if (csvData[i][0] < 1.96 && csvData[i][0] > 1.88
        				&& csvData[i][2] > 1.00) { 
            		dataBin195[ref195][0] = csvData[i][0];
            		dataBin195[ref195][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref195++;
        		} else if (csvData[i][0] < 2.04 && csvData[i][0] > 1.95
        				&& csvData[i][2] > 1.00) {   
            		dataBin203[ref203][0] = csvData[i][0];
            		dataBin203[ref203][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref203++;
        		} else if (csvData[i][0] < 2.12 && csvData[i][0] > 2.03
        				&& csvData[i][2] > 1.00) {
            		dataBin211[ref211][0] = csvData[i][0];
            		dataBin211[ref211][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref211++;
        		} else if (csvData[i][0] < 2.20 && csvData[i][0] > 2.11
        				&& csvData[i][2] > 1.00) { 
            		dataBin219[ref219][0] = csvData[i][0];
            		dataBin219[ref219][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref219++;
        		} else if (csvData[i][0] < 2.28 && csvData[i][0] > 2.19 
        				&& csvData[i][2] > 1.00) {   
            		dataBin227[ref227][0] = csvData[i][0];
            		dataBin227[ref227][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref227++;
        		} else if (csvData[i][0] < 2.35 && csvData[i][0] > 2.27
        				&& csvData[i][2] > 1.00) {   
            		dataBin234[ref234][0] = csvData[i][0];
            		dataBin234[ref234][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref234++;
        		} else if (csvData[i][0] < 2.43 && csvData[i][0] > 2.34
        				&& csvData[i][2] > 1.00) {   
            		dataBin242[ref242][0] = csvData[i][0];
            		dataBin242[ref242][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref242++;
        		} else if (csvData[i][0] < 2.51 && csvData[i][0] > 2.42
        				&& csvData[i][2] > 1.00) {   
            		dataBin250[ref250][0] = csvData[i][0];
            		dataBin250[ref250][1] = csvData[i][1];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
            		ref250++;
        		} else if (csvData[i][0] > 2.50 && csvData[i][2] > 1.00) {
        			dataBinOL[refOL][0] = csvData[i][2];
        			dataBinOL[refOL][1] = csvData[i][3];
            		tempFinalData[refTempFinal][0] = csvData[i][0];
            		tempFinalData[refTempFinal][1] = csvData[i][1];
            		refTempFinal++;
        			refOL++;
        		} 
            }
            
            mult08 = findAvg(dataBin08);
            mult16 = findAvg(dataBin16);
            mult23 = findAvg(dataBin23);
            mult31 = findAvg(dataBin31);
            mult39 = findAvg(dataBin39);
            mult47 = findAvg(dataBin47);
            mult55 = findAvg(dataBin55);
            mult63 = findAvg(dataBin63);
            mult70 = findAvg(dataBin70);
            mult78 = findAvg(dataBin78);
            mult86 = findAvg(dataBin86);
            mult94 = findAvg(dataBin94);
            mult102 = findAvg(dataBin102);
            mult117 = findAvg(dataBin117);
            mult125 = findAvg(dataBin125);
            mult133 = findAvg(dataBin133);
            mult141 = findAvg(dataBin141);
            mult148 = findAvg(dataBin148);
            mult156 = findAvg(dataBin156);
            mult164 = findAvg(dataBin164);
            mult172 = findAvg(dataBin172);
            mult180 = findAvg(dataBin180);
            mult188 = findAvg(dataBin188);
            mult195 = findAvg(dataBin195);
            mult203 = findAvg(dataBin203);
            mult211 = findAvg(dataBin211);
            mult219 = findAvg(dataBin219);
            mult227 = findAvg(dataBin227);
            mult234 = findAvg(dataBin234);
            mult242 = findAvg(dataBin242);
            mult250 = findAvg(dataBin250);
            multOpenLoop = findAFRDiff(dataBinOL);
            
            multArr[0] = mult08;
            multArr[1] = mult16;
            multArr[2] = mult23;
            multArr[3] = mult31;
            multArr[4] = mult39;
            multArr[5] = mult47;
            multArr[6] = mult55;
            multArr[7] = mult63;
            multArr[8] = mult70;
            multArr[9] = mult78;
            multArr[10] = mult86;
            multArr[11] = mult94;
            multArr[12] = mult102;
            multArr[13] = mult109;
            multArr[14] = mult117;
            multArr[15] = mult125;
            multArr[16] = mult133;
            multArr[17] = mult141;
            multArr[18] = mult148;
            multArr[19] = mult156;
            multArr[20] = mult164;
            multArr[21] = mult172;
            multArr[22] = mult180;
            multArr[23] = mult188;
            multArr[24] = mult195;
            multArr[25] = mult203;
            multArr[26] = mult211;
            multArr[27] = mult219;
            multArr[28] = mult227;
            multArr[29] = mult234;
            multArr[30] = mult242;
            multArr[31] = mult250;
            multArr[32] = multOpenLoop;
          
            finalData = tempFinalData;

             
            //System.out.println("1.33 average: " + avg133);
            //System.out.println("1.33 counter: " + counter133);
            //System.out.println("1.33 multiplier: " + mult133);
            //System.out.println("1.48 multiplier: " + mult148);
            //System.out.println("1.56 multiplier: " + mult156);
            //System.out.println("1.64 multiplier: " + mult164);
            //System.out.println("1.72 multiplier: " + mult172);
            	
        }
        
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
	}
	
}

