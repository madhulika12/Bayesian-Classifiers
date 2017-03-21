package com.example.naivebayseclassifier;

import java.util.HashMap;
import java.util.Map;

import weka.core.Instance;

public class DataEntry {
	private double serachValue;
	private int fieldIndex;
	private int count;
	// occurences by category
	private Map<Double, Integer> occByCategory = new HashMap<Double, Integer>();

	public DataEntry() {
		this.fieldIndex = -1;
		this.serachValue = -1;
		this.count = 0;
	}

	public DataEntry(int fieldIndex, double serachValue) {
		this.fieldIndex = fieldIndex;
		this.serachValue = serachValue;
		this.count = 0;
	}

	private void addInstance(Instance instance, double category) {
		if (occByCategory.containsKey(category)) {
			int count = occByCategory.get(category);
			occByCategory.put(category, count + 1);
		} else {
			occByCategory.put(category, 1);
		}

		this.count++;
	}

	public void checkInstance(Instance instance, double category) {
		double value = instance.value(this.fieldIndex);

		if (value == this.serachValue) {
			this.addInstance(instance, category);
		}
	}

	public double getPossibilityByCat(double category) {
		int occByCat = 0;
		if (occByCategory.containsKey(category)) {
			occByCat = occByCategory.get(category);
		}

		return (double) occByCat / this.count;
	}

	public double getPossibility(int instanceCount) {
		return (double) this.count / instanceCount;
	}
}
