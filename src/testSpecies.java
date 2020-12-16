//package dnabarcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class testSpecies {
    public String speciesClass;
    public double maxProb=-1000000.0,prob=0.0; //without log, 0.0 and 1.0
    public int bLen;
    public double correct=0.0;
    public double totalSamples=0.0;
    public double accuracy=0.0;
    public double latestProb=0.0;
    public double logValue=0.0;
    public String file;
    public int length;
    public boolean same;
    public boolean full;
    
    testSpecies(String filename, int barcodeLength, boolean sameFile, boolean fullLength){
    	this.file=filename;
    	this.length=barcodeLength;
    	this.same=sameFile;
    	this.full=fullLength;
    }
    
    public double test(HashMap<String, double[][]> spData, int totalSample, ArrayList<Integer> randTestCaseNo) throws IOException{
        Scanner sc=new Scanner(new File(file));
        String testCaseName;
        
        ArrayList<Integer> testCases=new ArrayList<Integer>();
        testCases.addAll(randTestCaseNo);
        int lineNumber=0;
        
        testCaseName=sc.next(); //Skip the first line with attribute names
        lineNumber+=1;
        while(sc.hasNext()) {
	        testCaseName=sc.next();
	        lineNumber+=1;
	        if(same && !testCases.contains(lineNumber)) {
        		continue;
        	}
	        totalSamples++;
	        //StringTokenizer st=new StringTokenizer(testCaseName,"|");
	        //testCaseName=st.nextToken();
	        //testCaseName=st.nextToken();
	        String[] arrOfAttr=testCaseName.split(",");
	        testCaseName=arrOfAttr[arrOfAttr.length-1];
	        
	        //String testSequence;
	        //testSequence=sc.next();
	        //bLen=testSequence.length();
	        //System.out.println(testSequence);
	        bLen=arrOfAttr.length-1;
	        if(!full) {
	        	bLen=length;
	        }
	        
	        Iterator<String> keyIterator=spData.keySet().iterator();
	        while(keyIterator.hasNext()){
	            String spKey=keyIterator.next();
	            double storedMat[][]=new double[5][bLen];
	            storedMat=spData.get(spKey);
	            
	            for(int i=0;i<bLen;i++){ //Previously- testSequence.length()
	                if(arrOfAttr[i].equals("A")){
	                	logValue=Math.log(storedMat[0][i]);
	                	prob=prob+logValue;
	                    //prob=prob*storedMat[0][i];
	                }
	                else if(arrOfAttr[i].equals("T")){
	                	logValue=Math.log(storedMat[1][i]);
	                	prob=prob+logValue;
	                    //prob=prob*storedMat[1][i];
	                }
	                else if(arrOfAttr[i].equals("G")){
	                	logValue=Math.log(storedMat[2][i]);
	                	prob=prob+logValue;
	                    //prob=prob*storedMat[2][i];
	                }
	                else if(arrOfAttr[i].equals("C")){
	                	logValue=Math.log(storedMat[3][i]);
	                	prob=prob+logValue;
	                    //prob=prob*storedMat[3][i];
	                }
	                else if(arrOfAttr[i].equals("N"))
	                {
	                	prob=prob+0.0;
	                	//prob=prob*1.0;
	                }
	                else{
	                	//logValue=Math.log(storedMat[4][i]);
	                	//prob=prob+logValue;
	                	prob=prob+0.0;
	                    //prob=prob*storedMat[4][i];
	                }
	            }
	            prob+=Math.log(storedMat[4][0]/totalSample);
	            
	            if(prob>=maxProb){
	                speciesClass=spKey;
	                maxProb=prob;
	            }
	            latestProb=maxProb;
	            prob=0.0;
	            
	            /*System.out.println(spKey);
	            for(int i=0;i<4;i++){
	                for(int j=0;j<31;j++){
	                    System.out.print(probMat[i][j]+" ");
	                }
	                System.out.println();
	            }*/
	        }
	        //System.out.println(testCaseName+" "+speciesClass);
	        //System.out.println("Identified as: "+speciesClass+" "+"Original: "+testCaseName+" "+"Probability: "+latestProb);
	        if(speciesClass.equals(testCaseName)) {
	        	correct++;
	        	//System.out.println("Correct");
	        }
	        else {
	        	//System.out.println("Wrong");
	        }
	        maxProb=-1000000.0;
	        prob=0.0;
        }
        accuracy=(correct/totalSamples)*100.0;
        //System.out.println("Accuracy: "+accuracy+"%");
        //System.out.print(accuracy+",");
        
        //File f=new File("E:\\DNA Barcode Project\\New Approach\\Accuracy_DifferentLengths_Simulated.txt");
        //FileWriter fileWriter=new FileWriter(f,true);
        //fileWriter.append(accuracy+",");
        //fileWriter.close();
        
        sc.close();
        return accuracy;
    }
}
