package com.example.naivebayseclassifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class MainClass {
	final static int YES = 0;
	final static int NO = 1;

	private static List<DataEntry> initTest(int result) {
		List<DataEntry> entries = new ArrayList<DataEntry>();

		switch (result) {
		case 0:// Expected YES - 0.00
			entries.add(new DataEntry(0, 1.00));
			entries.add(new DataEntry(1, 0.00));
			entries.add(new DataEntry(2, 1.00));
			entries.add(new DataEntry(3, 1.00));
			break;
		case 1:// Expected NO - 1.00
			entries.add(new DataEntry(0, 0.00));
			entries.add(new DataEntry(1, 0.00));
			entries.add(new DataEntry(2, 0.00));
			entries.add(new DataEntry(3, 0.00));
			break;

		}

		return entries;
	}

	public static void main(String[] args) throws IOException {

		ArffLoader loader = new ArffLoader();
		Instances structure;

		String str = "weather.nominal.arff";
		loader.setFile(new File(str));

		structure = loader.getStructure();
		structure.setClassIndex(structure.numAttributes() - 1);

		structure = loader.getDataSet();

		int instanceCount = structure.numInstances();
		List<DataEntry> entries = initTest(NO);
		PossibilityCalculator possCalc = new PossibilityCalculator(entries,
				instanceCount);

		for (int i = 0; i < instanceCount; i++) {
			Instance instance = structure.instance(i);
			possCalc.registerInstance(instance);
		}

		double category = possCalc.getCategory();
		String output = (int) category == YES ? "YES" : "NO";
		System.out.println("Result: " + output + "(" + category + ")");

	}
}
