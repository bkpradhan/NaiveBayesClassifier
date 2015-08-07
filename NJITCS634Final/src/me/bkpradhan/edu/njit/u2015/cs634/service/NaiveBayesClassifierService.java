package me.bkpradhan.edu.njit.u2015.cs634.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.bkpradhan.edu.njit.u2015.cs634.domain.EvaluationResult;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionResult;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionStatistics;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionSummary;
import me.bkpradhan.edu.njit.u2015.cs634.util.CsvToArffConverter;
import me.bkpradhan.edu.njit.u2015.cs634.util.Helper;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;

public class NaiveBayesClassifierService {

	EvaluationResult evalResult = null;
	NaiveBayes nb = new NaiveBayes();
	private boolean isTrainingFileDirty = true;

	public static void main(String args[]) throws Exception {
		new NaiveBayesClassifierService().predict("test");
	}

	public NaiveBayesClassifierService() {

	}

	public boolean isTrainingFileDirty() {
		return isTrainingFileDirty;
	}

	public void setTrainingFileDirty(boolean isTrainingFileDirty) {
		this.isTrainingFileDirty = isTrainingFileDirty;
	}

	protected EvaluationResult trainAndEvaluate(int foldCount, boolean force)
			throws Exception {
		if (evalResult != null) {
			if (!force) {
				System.out
						.println("Will not train/evaluate, as training model is already available!");
				return evalResult;
			} else {
				System.out
						.println("Will force train and evaluate, eventhough a training model exists!");
			}
		} else {
			System.out
					.println("Will train and evaluate for the first time, as no training model is available!");
		}

		System.out.println("Fold Count: " + foldCount + ", Retrained ? [ "
				+ force + " ]");

		Path path = Paths.get(Helper.TRAINING_SET);
		if (!Files.isRegularFile(path) || Files.size(path) < 100L) {
			throw new Exception(
					"No trainigdata set found or worthless trainingset file; size < 100 bytes! Please upload training data set for (demo) evaluation or contact System administrator.");
		}

		EvaluationResult result = new EvaluationResult();
		try {
			long trainStart = new Date().getTime();
			BufferedReader trainArff = new BufferedReader(new FileReader(
					Helper.TRAINING_SET));
			Instances train = new Instances(trainArff);
			train.setClassIndex(train.numAttributes() - 1);
			nb.buildClassifier(train);
			Evaluation eval = new Evaluation(train);
			eval.crossValidateModel(nb, train, foldCount, new Random(1));
			result.setCid(new Date().getTime());
			result.setName("-- NJIT CS634 Final Project - Naive Bayes Classification [ @bkpradhan ]--");
			result.setTrain(train);
			result.setFoldCount(foldCount);
			result.setEval(eval);
			// System.out.println(eval.getMetricsToDisplay());
			String confusedMatrix = eval
					.toMatrixString("\n---- Confused Matrix ----");
			String summary = eval.toSummaryString("\n" + foldCount
					+ " Fold Evaluation Summary\n", true);
			String classStat = eval
					.toClassDetailsString("\n---- Class details --");
			// for ( int i = 0 ; i < classCount ; i++){
			// classStat += eval.fMeasure(i) + " " + eval.precision(i) + " "
			// + eval.recall(i);
			// }
			int trimmed = (int) ((0.001 * (result.getCid() - trainStart)) * 100);
			String totalTime = "\nTotal Execution time for training and Evaluation: "
					+ Double.toString(trimmed / 100.0) + "s.\n";
			result.setStatistics(totalTime + summary + classStat
					+ confusedMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		evalResult = result;
		return result;
	}

	protected PredictionSummary predict(String testingFile) throws Exception {
		if (evalResult == null) {
			throw new Exception(
					"No trained model is available for prediction! Please upload and/or train and evaluate before analyzing test data.");
		}
		Instances test = null;
		try {
			// Reading the Testing Set
			BufferedReader breader = new BufferedReader(new FileReader(
					testingFile));
			test = new Instances(breader);

			// Use the same classifier index as the training table
			test.setClassIndex(evalResult.getTrain().classIndex());

			breader.close();
		} catch (Exception e) {
			throw new Exception(
					"You didn't upload a 'test data set' to analyze or your test data is not valid! Please upload before analyzing test data."
							+ e.getMessage());
		}
		ArrayList<PredictionResult> outputList = new ArrayList<PredictionResult>();
		long predictStart = new Date().getTime();
		int totalMatchedOk = 0;
		for (int i = 0; i < test.numInstances(); i++) {
			Instance record = test.instance(i);
			double pred = nb.classifyInstance(record);
			PredictionResult result = new PredictionResult();
			result.setCid(i + 1);
			result.setRecord(record.toString());
			double[] dist = nb.distributionForInstance(record);
			result.setProbDistribution(dist);
			// System.out.print(record.toString(test.classIndex()));
			result.setPrediction(test.classAttribute().value((int) pred));
			if (pred == record.classValue()) {
				result.setMatch("Yes");
				totalMatchedOk++;
			} else {
				result.setMatch("No");
			}

			outputList.add(result);
		}
		long predictFinish = new Date().getTime();
		double totalTimeTaken = 0.001 * (predictFinish - predictStart);
		PredictionStatistics pStat = new PredictionStatistics();
		pStat.setName(" --Prediction Summary---");
		pStat.setTotalInstances(test.numInstances());
		pStat.setTotalMatchedOk(totalMatchedOk);
		pStat.setTotalTimeTaken(totalTimeTaken);
		// System.out.println(outputList);
		PredictionSummary pSummary = new PredictionSummary();
		pSummary.setResults(outputList);
		pSummary.setStat(pStat);

		return pSummary;
	}

	public static void printEvaluationSummary(final Evaluation eval)
			throws Exception {
		System.out.println(eval.toSummaryString("Results\n========\n", true));
		System.out.println(eval.toClassDetailsString("---- Class details --"));
		System.out.println(eval.toMatrixString("---- Confused Matrix ----"));
	}

	public EvaluationResult train(int foldCount, boolean force)
			throws Exception {
		EvaluationResult result = trainAndEvaluate(foldCount, force);
		return result;
	}

	public PredictionSummary predictNow(String testfile) throws Exception {

		return predict(testfile);
	}

}
