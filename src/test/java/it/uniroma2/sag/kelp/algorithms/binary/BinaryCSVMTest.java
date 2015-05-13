package it.uniroma2.sag.kelp.algorithms.binary;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.kernel.vector.LinearKernel;
import it.uniroma2.sag.kelp.learningalgorithm.classification.libsvm.BinaryCSvmClassification;
import it.uniroma2.sag.kelp.predictionfunction.classifier.ClassificationOutput;
import it.uniroma2.sag.kelp.predictionfunction.classifier.Classifier;
import it.uniroma2.sag.kelp.utils.evaluation.BinaryClassificationEvaluator;
import it.uniroma2.sag.kelp.utils.exception.NoSuchPerformanceMeasureException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryCSVMTest {
	// Accuracy: 0.9766667
	// F1 = 0.9769737
	private static Classifier f = null;
	private static SimpleDataset trainingSet;
	private static SimpleDataset testSet;
	private static ArrayList<Float> scores;

	private Label positiveClass = new StringLabel("+1");

	@BeforeClass
	public static void learnModel() {
		trainingSet = new SimpleDataset();
		testSet = new SimpleDataset();
		try {
			trainingSet.populate("src/test/resources/binaryCSvmTest/train.klp");
			// Read a dataset into a test variable
			testSet.populate("src/test/resources/binaryCSvmTest/test.klp");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

		// define the positive class
		StringLabel positiveClass = new StringLabel("+1");

		BinaryCSvmClassification learner = new BinaryCSvmClassification(
				new LinearKernel("0"), positiveClass, 1, 1);
		// learn and get the prediction function
		learner.learn(trainingSet);
		f = learner.getPredictionFunction();
	}

	@BeforeClass
	public static void loadClassificationScores() {
		try {
			scores = new ArrayList<Float>();
			String filepath = "src/test/resources/binaryCSvmTest/outScores.txt";
			BufferedReader in = null;
			String encoding = "UTF-8";
			if (filepath.endsWith(".gz")) {
				in = new BufferedReader(new InputStreamReader(
						new GZIPInputStream(new FileInputStream(filepath)),
						encoding));
			} else {
				in = new BufferedReader(new InputStreamReader(
						new FileInputStream(filepath), encoding));
			}

			String str = "";
			while ((str = in.readLine()) != null) {
				scores.add(Float.parseFloat(str));
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testAccuracy() {
		BinaryClassificationEvaluator ev = new BinaryClassificationEvaluator(
				positiveClass);
		for (Example e : testSet.getExamples()) {
			ClassificationOutput p = f.predict(testSet.getNextExample());
			ev.addCount(e, p);
		}

		try {
			float acc = ev.getPerformanceMeasure("getAccuracy");
			Assert.assertEquals(0.9766667f, acc, 0.000001);
		} catch (NoSuchPerformanceMeasureException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void testF1() {
		BinaryClassificationEvaluator ev = new BinaryClassificationEvaluator(
				positiveClass);
		for (Example e : testSet.getExamples()) {
			ClassificationOutput p = f.predict(e);
			ev.addCount(e, p);
		}

		try {
			float acc = ev.getPerformanceMeasure("getF1");
			Assert.assertEquals(0.9769737f, acc, 0.000001);
		} catch (NoSuchPerformanceMeasureException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void checkScores() {
		for (int i = 0; i < testSet.getExamples().size(); ++i) {
			Example e = testSet.getExample(i);
			ClassificationOutput p = f.predict(e);
			Float score = p.getScore(positiveClass);
			Assert.assertEquals(scores.get(i).floatValue(), score.floatValue(),
					0.000001f);
		}
	}
}
