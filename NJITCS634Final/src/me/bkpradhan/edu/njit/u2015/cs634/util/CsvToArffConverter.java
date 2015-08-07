package me.bkpradhan.edu.njit.u2015.cs634.util;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.InputStream;

public class CsvToArffConverter {

	public static void main(String args[]) throws Exception {

		// convert("/resources/iris-trainingdata.csv",
		// "temp/trainingdata.arff");
		// convert("/resourcses/iris-testingdata.csv", "testingdata.arff");
		// convert("/churn-testingset.csv", "churn-testingset.arff");

	}

	public static void convert(String csv, String arff) throws Exception {
		InputStream inputStream = CsvToArffConverter.class
				.getResourceAsStream(csv);// new
											// FileInputStream("config.properties");

		// load CSV
		CSVLoader loader = new CSVLoader();
		System.out.println("csv file path:" + csv);
		loader.setSource(new File(csv));
		Instances data = loader.getDataSet();

		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arff));
		saver.writeBatch();
	}

}
