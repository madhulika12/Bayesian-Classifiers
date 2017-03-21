package com.example.naivebayseclassifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import weka.core.Instance;

public class PossibilityCalculator {
	private List<DataEntry> entries;
	private Map<Double, Integer> categoryCounter;
	private int instanceCount;

	public PossibilityCalculator(List<DataEntry> initalCase, int instanceCount) {
		this.entries = initalCase;
		this.instanceCount = instanceCount;
		this.categoryCounter = new HashMap<Double, Integer>();
	}

	private double getCategory(Instance instance) {
		int lastIndex = instance.numValues() - 1;
		return instance.value(lastIndex);
	}

	private void registerCategory(double category) {
		if (this.categoryCounter.containsKey(category)) {
			int count = this.categoryCounter.get(category);
			this.categoryCounter.put(category, count + 1);
		} else {
			this.categoryCounter.put(category, 1);
		}
	}

	public void registerInstance(Instance instance) {
		double category = this.getCategory(instance);

		for (DataEntry entry : entries) {
			entry.checkInstance(instance, category);
		}

		this.registerCategory(category);
	}

	private double calculatePossibility(double category, int occCount) {
		double possibility = 1.0;
		double possibilityByCat = 1.0;
		double catPossibility = (double) occCount / this.instanceCount;

		for (DataEntry entry : this.entries) {
			possibility *= entry.getPossibility(instanceCount);
			possibilityByCat *= entry.getPossibilityByCat(category);
		}

		// division by zero
		if (possibility == 0)
			return 0;

		return (possibilityByCat * catPossibility) / possibility;
	}

	private Map<Double, Double> getPossibilityByCat() {
		Map<Double, Double> result = new HashMap<Double, Double>();

		for (Entry<Double, Integer> entry : this.categoryCounter.entrySet()) {
			double category = entry.getKey();
			int occCount = entry.getValue();
			double possibility = this.calculatePossibility(category, occCount);

			result.put(category, possibility);
		}

		return result;
	}

	public double getCategory() {
		Map<Double, Double> categorizer = this.getPossibilityByCat();

		ValueComparator bvc = new ValueComparator(categorizer);
		TreeMap<Double, Double> sorted_map = new TreeMap<Double, Double>(bvc);
		sorted_map.putAll(categorizer);

		for (Entry<Double, Double> entry : sorted_map.entrySet()) {
			System.out.println("Category " + entry.getKey() + " score: "
					+ entry.getValue());
		}

		return sorted_map.firstKey();
	}
}
