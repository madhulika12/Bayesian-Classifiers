package com.example.naivebayseclassifier;

import java.util.Comparator;
import java.util.Map;


public class ValueComparator implements Comparator<Double> {

    Map<Double, Double> base;
    public ValueComparator(Map<Double, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(Double a, Double b) {
    	//Reverse sort
    	return Double.compare(base.get(b), base.get(a));
    }
}
