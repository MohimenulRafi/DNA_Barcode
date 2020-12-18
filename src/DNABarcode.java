//package dnabarcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;


public class DNABarcode {
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	int experimentType=Integer.parseInt(args[0]);
    	String trainFile, testFile;
    	//trainFile="E:\\DNA Barcode Project\\New Approach\\Dataset CSV\\Algae.csv";
    	//testFile="E:\\\\DNA Barcode Project\\\\New Approach\\\\Dataset CSV\\\\Inga_Test.csv";
    	
    	boolean sameFile=true;
    	boolean fullLength=false;
    	
    	if(experimentType==1 || experimentType==2) {
    		sameFile=false;
    	}
    	
    	/*if(sameFile) {
    		testFile=trainFile;
    	}*/
    	
    	ArrayList<Integer> testCaseNumber=new ArrayList<Integer>();
    	
    	//Setting lengths and files
    	int[] l_emp_sim= {70, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600};
    	int[] l_fun_alg= {100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850};
    	int[] l_inga= {1050, 1100, 1150, 1200, 1250, 1300, 1350, 1400, 1450, 1500, 1550, 1600, 1650, 1700, 1750, 1800};
    	//int[] l_fun_perc= {93, 186, 279, 372, 465, 558, 651, 744, 837, 930};
    	//int[] l_alg_perc= {112, 225, 338, 451, 564, 676, 789, 902, 1015, 1128};
    	//int[] l_inga_perc= {183, 367, 551, 735, 919, 1102, 1286, 1470, 1654, 1838};
    	int[] l;
    	
    	if(experimentType==1 && args.length==3) {
    		trainFile=args[1];
        	testFile=args[2];
    		l=new int[l_emp_sim.length];
    		l=l_emp_sim.clone();
    	}
    	else if(experimentType==1 && args.length==4 && args[3].equals("perc")) {
    		trainFile=args[1];
        	testFile=args[2];
    		l=getLengths(trainFile);
    	}
    	else if(experimentType==2 && args.length==2) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_emp_sim.length];
    		l=l_emp_sim.clone();
    	}
    	else if(experimentType==2 && args.length==3 && args[2].equals("perc")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=getLengths(trainFile);
    	}
    	else if(experimentType==3 && args.length==2) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_emp_sim.length];
    		l=l_emp_sim.clone();
    	}
    	else if(experimentType==3 && args.length==3 && args[2].equals("perc")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=getLengths(trainFile);
    	}
    	else if(experimentType==3 && args.length==3 && args[2].equals("fun")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_fun_alg.length];
    		l=l_fun_alg.clone();
    	}
    	else if(experimentType==3 && args.length==3 && args[2].equals("alg")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_fun_alg.length];
    		l=l_fun_alg.clone();
    	}
    	else if(experimentType==3 && args.length==3 && args[2].equals("inga")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_inga.length];
    		l=l_inga.clone();
    	}
    	else if(experimentType==4 && args.length==2) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_emp_sim.length];
    		l=l_emp_sim.clone();
    	}
    	else if(experimentType==4 && args.length==3 && args[2].equals("fun")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_fun_alg.length];
    		l=l_fun_alg.clone();
    	}
    	else if(experimentType==4 && args.length==3 && args[2].equals("alg")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_fun_alg.length];
    		l=l_fun_alg.clone();
    	}
    	else if(experimentType==4 && args.length==3 && args[2].equals("inga")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=new int[l_inga.length];
    		l=l_inga.clone();
    	}
    	else if(experimentType==4 && args.length==3 && args[2].equals("perc")) {
    		trainFile=args[1];
    		testFile=args[1];
    		l=getLengths(trainFile);
    	}
    	else {
    		l=new int[1];
    		trainFile="";
    		testFile="";
    		System.out.println("Check the command or modify the code.");
    	}
    	
    	System.out.println("######## Lengths ########");
    	for(int i=0;i<l.length;i++) {
    		System.out.print(l[i]+" ");
    	}
    	System.out.println();
    	System.out.println("######## Accuracies ########");
    	
    	//Experiments
    	if(experimentType==1) {
    		for(int i=0;i<l.length;i++) {
	    		int barcodeLength=l[i];
		    	
		    	learnSpecies learn=new learnSpecies(trainFile, barcodeLength, sameFile, fullLength, testCaseNumber);
		        learn.train();
		        
		        testSpecies test=new testSpecies(testFile, barcodeLength, sameFile, fullLength);
		        double a=test.test(learn.sp, learn.totalSample, testCaseNumber);
		        System.out.printf("%.2f ", a);
	    	}
    	}
    	
    	else if(experimentType==2) {
    		double totalAccuracy=0.0;
        	for(int i=0;i<l.length;i++) {
        		totalAccuracy=0.0;
    	    	for(int j=0;j<100;j++) {
    	    		//trainFile="E:\\DNA Barcode Project\\New Approach\\Dataset CSV (Simulated)\\N50000\\GNe50000r"+j+"_train_SingleLined.csv";
    	        	//testFile="E:\\DNA Barcode Project\\New Approach\\Dataset CSV (Simulated)\\N50000\\GNe50000r"+j+"_test_SingleLined.csv";
    	    		trainFile=new String();
    	    		testFile=new String();
    	    		trainFile=args[1]+j+"_train_SingleLined.csv";
    	    		testFile=args[1]+j+"_test_SingleLined.csv";
    	        	
    		    	//int barcodeLength=70;
    	        	int barcodeLength=l[i];
    		    	
    		        learnSpecies learn=new learnSpecies(trainFile, barcodeLength, sameFile, fullLength, testCaseNumber);
    		        learn.train();
    		        
    		        testSpecies test=new testSpecies(testFile, barcodeLength, sameFile, fullLength);
    		        totalAccuracy+=test.test(learn.sp, learn.totalSample, testCaseNumber);
    	    	}
    	    	System.out.printf("%.2f ", (totalAccuracy/100.0));
    	    	//File f=new File("E:\\DNA Barcode Project\\New Approach\\Accuracy_DifferentLengths_Simulated.txt");
    	        //FileWriter fileWriter=new FileWriter(f,true);
    	        //fileWriter.append(totalAccuracy/100.0+",");
    	        //fileWriter.close();
        	}
    	}
    	else if(experimentType==3) {
    		ArrayList<Integer> caseNumber=new ArrayList<Integer>();
    		ArrayList<Integer> bestTestCaseNumber=new ArrayList<Integer>();
    		
        	
        	ArrayList<Integer> bestTests=new ArrayList<Integer>();
        	double best=0.0;
        	fullLength=true;
        	int barcodeLength=600;
        	for(int i=0;i<10;i++) {
        		HashMap<String, ArrayList<Integer>> speciesEntryNumbers=new HashMap<>();
        		String speciesName, sample;
        		ArrayList<Integer> entries=new ArrayList<Integer>();
        		ArrayList<Integer> tests=new ArrayList<Integer>();
        		Scanner speciesNumberReader=new Scanner(new File(trainFile));
        		sample=speciesNumberReader.next();
        		int lineNumber=0;
        		
            	while(speciesNumberReader.hasNext()) {
            		sample=speciesNumberReader.next();
            		lineNumber++;
            		String[] arrOfAttr=sample.split(",");
                    speciesName=arrOfAttr[arrOfAttr.length-1];
                    
                    if(speciesEntryNumbers.containsKey(speciesName)){
                    	entries=speciesEntryNumbers.get(speciesName);
                    	entries.add(lineNumber);
                    	speciesEntryNumbers.put(speciesName, entries);
                    }
                    else{
                    	entries=new ArrayList<Integer>();
                    	entries.add(lineNumber);
                        speciesEntryNumbers.put(speciesName, entries);
                    }
            		
            	}
            	speciesNumberReader.close();
            	
            	Iterator<String> keyIterator=speciesEntryNumbers.keySet().iterator();
            	while(keyIterator.hasNext()){
                    String key=keyIterator.next();
                    ArrayList<Integer> entryList=new ArrayList<Integer>();
                    entryList=speciesEntryNumbers.get(key);
                    
                    int tNo=0;
                    tNo=(int)(entryList.size()*(0.2));
                    if(entryList.size()==4 || entryList.size()==3) {
                    	tNo=1;
                    }
                    
                    Random rand = new Random();
                    for(int j=0;j<tNo;j++) {
                    	int rInt = rand.nextInt(entryList.size()-1);
                    	tests.add(entryList.get(rInt));
                    }
                }
            	
        		learnSpecies learn=new learnSpecies(trainFile, barcodeLength, sameFile, fullLength, tests); //previously bestTestCaseNumber in place of tests
		        learn.train();
		        
		        testSpecies test=new testSpecies(testFile, barcodeLength, sameFile, fullLength);
		        double a=test.test(learn.sp, learn.totalSample, tests); //previously bestTestCaseNumber in place of tests
		        
		        if(a>=best) {
		        	best=a;
		        	bestTests.clear();
		        	bestTests.addAll(tests);
		        }
        	}
        	
        	
        	fullLength=false;
        	for(int i=0;i<l.length;i++) {
        		barcodeLength=l[i];
		    	
		    	learnSpecies learn=new learnSpecies(trainFile, barcodeLength, sameFile, fullLength, bestTests);
		        learn.train();
		        
		        testSpecies test=new testSpecies(testFile, barcodeLength, sameFile, fullLength);
		        double a=test.test(learn.sp, learn.totalSample, bestTests);
		        System.out.printf("%.2f ", a);
	    	}
    	}
    	else if(experimentType==4) {
    		//Random 80-20 split
    		ArrayList<Integer> caseNumber=new ArrayList<Integer>();
    		ArrayList<Integer> bestTestCaseNumber=new ArrayList<Integer>();
    		
    		Scanner scReadLine=new Scanner(new File(trainFile));
        	int numberOfLines=0;
        	while(scReadLine.hasNext()) {
        		numberOfLines+=1;
        		scReadLine.next();
        	}
        	scReadLine.close();
        	
        	int numberOfCase=numberOfLines; //Unnecessary declaration
        	for(int i=1;i<numberOfCase;i++) {
        		caseNumber.add(i);
        	}
        	Collections.shuffle(caseNumber);
        	
        	
        	double bestAccuracy=0.0;
        	int numberOfElements=(int)(caseNumber.size()*0.2);
        	
        	int startPoint=0;
        	
        	testCaseNumber.clear();
    		for(int k=startPoint;k<startPoint+numberOfElements;k++) {
    			testCaseNumber.add(caseNumber.get(k));
    		}
    		
    		
    		int barcodeLength=850;
    		for(int m=0;m<l.length;m++) {
        		barcodeLength=l[m];
	    		learnSpecies learn=new learnSpecies(trainFile, barcodeLength, sameFile, fullLength, testCaseNumber);
		        learn.train();
		        
		        testSpecies test=new testSpecies(testFile, barcodeLength, sameFile, fullLength);
		        double a=test.test(learn.sp, learn.totalSample, testCaseNumber);
	    		
	    		System.out.printf("%.2f ", a);
    		}
    		
	        
    	}
    	
    }
    
    //Returns the lengths of various percentages
    public static int[] getLengths(String file) throws FileNotFoundException {
    	double[] percentages= {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
    	int[] lengths=new int[percentages.length];
    	String line;
    	int sequenceLength;
    	
    	Scanner scRead=new Scanner(new File(file));
    	line=scRead.next();
    	
    	String[] arrOfAttr=line.split(",");
    	sequenceLength=arrOfAttr.length-1;
    	
    	for(int i=0;i<percentages.length;i++) {
    		lengths[i]=(int)(sequenceLength*percentages[i]);
    	}
    	
    	scRead.close();
    	return lengths;
    }
    
}
