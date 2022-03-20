package com.tudux.threading.Lecture1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tudux.threading.Lecture1.callables.CODSDAOImpl;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	int numberOfEvents = 1000;
    	int numberOfThreads = 10;
    	
    	long multiStart2 = System.nanoTime();        
    	singleThreadCollectionHandler(numberOfEvents);
        long multiEnd2 = System.nanoTime();  
        System.out.println("Elapsed Time in nano seconds for Single Thread Approach:"
        		+ " "+ (multiEnd2-multiStart2));    	
    	
    	long multiStart1 = System.nanoTime();
    	multiThreadCollectionHandler(numberOfEvents,numberOfThreads);
        long multiEnd1 = System.nanoTime();      
        System.out.println("Elapsed Time in nano seconds for Executor Service approach:"
        		+ " "+ (multiEnd1-multiStart1));
        
    	
    }
    
    
    public static void multiThreadCollectionHandler(int numberOfEvents,int threadsN) {
    	
    	System.out.println("Starting Multi Thread Processing..");
    	LinkedList<String> testColl = new LinkedList<String>();
    	LinkedList<Future<LinkedList<String>>> futures = new LinkedList<Future<LinkedList<String>>>();
    	for (int i = 1; i < numberOfEvents ; i++) {
    		testColl.add(i + "-SomeEvent");
    	}
    	
    	Iterable<List<String>> subSets =  Iterables.partition(testColl, threadsN);    	    	
    	ExecutorService executor = Executors.newFixedThreadPool(threadsN);
    	
    	//Prepare futures to get results
    	for (List<String> x : subSets) {
    		Future<LinkedList<String>> future = executor.submit(new CODSDAOImpl(new LinkedList<String>(x)));
    		futures.add(future);
    		
    	}
    	
    	//Retrieve result from futures
        for(Future<LinkedList<String>> fut : futures){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
            	for (String x : fut.get()) {
            		System.out.println(x);
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown(); 
    	
    }
    
    
    public static void singleThreadCollectionHandler(int numberOfEvents) throws InterruptedException {
    	
    	System.out.println("Starting Single Thread Processing..");
    	LinkedList<String> testColl = new LinkedList<String>();
    	LinkedList<String> testColl2 = new LinkedList<String>();
    	for (int i = 1; i < numberOfEvents ; i++) {
    		testColl.add(i + "-SomeEvent");
    	}
    	    	
    	for (String x : testColl) {
        	TimeUnit.MILLISECONDS.sleep(800);
        	System.out.println("Processing");
    		testColl2.add(x.concat("Modified String"));    		    		
    	}
    	
        for(String x : testColl2){
        	System.out.println(x);
        }    	
    }    
    
}
