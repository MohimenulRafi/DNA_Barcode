//package dnabarcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


public class learnSpecies {
    public double basePosProb[][];
    public HashMap<String, double[][]> sp=new HashMap<>();
    public int barcodeLength;
    public int totalSample=0;
    public String file;
    public int length;
    public boolean same;
    public boolean full;
    
    public ArrayList<Integer> randomNumber=new ArrayList<Integer>();
    public ArrayList<Integer> testCases=new ArrayList<Integer>();
    
    learnSpecies(String filename, int barcodeLength, boolean sameFile, boolean fullLength, ArrayList<Integer> randTestCaseNo){
    	this.file=filename;
    	this.length=barcodeLength;
    	this.same=sameFile;
    	this.full=fullLength;
    	this.testCases.addAll(randTestCaseNo);
    }
    
    
    public void train() throws FileNotFoundException{
        
        barcodeLength=0;
        
        Scanner scRead=new Scanner(new File(file));
        String speciesName, sample;
        int lineNumber=0;
        sample=scRead.next(); //Skipping the first line with attributes name
        //System.out.println(sample.split(",").length-1);
        lineNumber+=1;
        while(scRead.hasNext()){
        	sample=scRead.next();
        	lineNumber+=1;
        	if(same && testCases.contains(lineNumber)) {
        		continue;
        	}
        	String[] arrOfAttr=sample.split(",");
        	speciesName=arrOfAttr[arrOfAttr.length-1];
        	
        	barcodeLength=arrOfAttr.length-1;
        	
        	if(!full) {
        		barcodeLength=length;
        	}
            
            if(sp.containsKey(speciesName)){
                basePosProb=sp.get(speciesName);
                
                for(int i=0;i<barcodeLength;i++){ //Previously- barcode.length()
                    if(arrOfAttr[i].equals("A")){ //Previously- barcode.charAt(i)=='A'
                        basePosProb[0][i]+=1.0;
                    }
                    else if(arrOfAttr[i].equals("T")){
                        basePosProb[1][i]+=1.0;
                    }
                    else if(arrOfAttr[i].equals("G")){
                        basePosProb[2][i]+=1.0;
                    }
                    else if(arrOfAttr[i].equals("C")){
                        basePosProb[3][i]+=1.0;
                    }
                    else if(arrOfAttr[i].equals("N"))
                    {
                    	basePosProb[4][i]+=1.0;
                    }
                    else{
                        basePosProb[4][i]+=1.0;
                    }
                }
                sp.put(speciesName, basePosProb);
            }
            else{
                basePosProb=new double[5][barcodeLength];
                for(int i=0;i<barcodeLength;i++){
                    if(arrOfAttr[i].equals("A")){
                        basePosProb[0][i]=1.0;
                    }
                    else if(arrOfAttr[i].equals("T")){
                        basePosProb[1][i]=1.0;
                    }
                    else if(arrOfAttr[i].equals("G")){
                        basePosProb[2][i]=1.0;
                    }
                    else if(arrOfAttr[i].equals("C")){
                        basePosProb[3][i]=1.0;
                    }
                    else if(arrOfAttr[i].equals("N"))
                    {
                    	basePosProb[4][i]=1.0;
                    }
                    else{
                        basePosProb[4][i]=1.0;
                    }
                }
                sp.put(speciesName, basePosProb);
            }
            //basePosProb=new double[5][barcodeLength];
        }
        
        Iterator<String> keyIterator=sp.keySet().iterator();
        while(keyIterator.hasNext()){
            String spKey=keyIterator.next();
            double probMat[][]=new double[5][barcodeLength];
            probMat=sp.get(spKey);
            for(int i=0;i<barcodeLength;i++){
                double totalCountAtPos=0.0;
                totalCountAtPos=probMat[0][i]+probMat[1][i]+probMat[2][i]+probMat[3][i]+probMat[4][i];
                
                probMat[4][i]=probMat[4][i]+probMat[0][i]+probMat[1][i]+probMat[2][i]+probMat[3][i]; //For storing number of samples of a particular species
                probMat[0][i]=probMat[0][i]/totalCountAtPos;
                probMat[1][i]=probMat[1][i]/totalCountAtPos;
                probMat[2][i]=probMat[2][i]/totalCountAtPos;
                probMat[3][i]=probMat[3][i]/totalCountAtPos;
                //probMat[4][i]=probMat[4][i]/totalCountAtPos;
                if(probMat[0][i]==0.0){
                    probMat[0][i]=0.000000097;
                }
                if(probMat[1][i]==0.0){
                    probMat[1][i]=0.000000097;
                }
                if(probMat[2][i]==0.0){
                    probMat[2][i]=0.000000097;
                }
                if(probMat[3][i]==0.0){
                    probMat[3][i]=0.000000097;
                }
                if(probMat[4][i]==0.0){
                    probMat[4][i]=0.000000097;
                }
            }
            totalSample+=probMat[4][0];
            sp.put(spKey, probMat);
        }
        
        
        scRead.close();
    }
}
