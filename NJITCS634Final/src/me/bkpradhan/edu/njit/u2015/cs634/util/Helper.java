package me.bkpradhan.edu.njit.u2015.cs634.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.bkpradhan.edu.njit.u2015.cs634.controller.TaskManagerController;


public class Helper {
	public static String TRAINING_DATA="trainingdata";
	public static String TESTING_DATA="testingdata";

	public static String TRAINING_SET=Helper.getBasePath()+"/"+TRAINING_DATA+".arff";
	public static String TESTING_SET=Helper.getBasePath()+"/"+TESTING_DATA+".arff";

	public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
	    Set<Set<T>> sets = new HashSet<Set<T>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<T>());
	    	return sets;
	    }
	    List<T> list = new ArrayList<T>(originalSet);
	    T head = list.get(0);
	    Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
	    for (Set<T> set : powerSet(rest)) {
	    	Set<T> newSet = new HashSet<T>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }	
	    //sets.remove(head);
	    return sets;
	}

	
	public static <T> Set<T> getSubset(Set<T> superSet, Set<T> subSet) {
		Set<T> newSet = new HashSet<T>();
		for(T val : superSet){
			if(!subSet.contains(val)){
				newSet.add(val);
			}
		}
		return newSet; 
	}

		public static int[] convert(Object[] objectArray){
		  int[] intArray = new int[objectArray.length];

		  for(int i=0; i<objectArray.length; i++){
		   intArray[i] = (int) objectArray[i];
		  }

		  return intArray;
		 }
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static String getBasePath(){
		return Helper.class.getProtectionDomain().getCodeSource().getLocation().getFile().split("WEB-INF")[0]+"WEB-INF";
	}
}
