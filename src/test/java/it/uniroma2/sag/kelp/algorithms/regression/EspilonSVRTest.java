package it.uniroma2.sag.kelp.algorithms.regression;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.kernel.Kernel;
import it.uniroma2.sag.kelp.kernel.cache.FixIndexKernelCache;
import it.uniroma2.sag.kelp.kernel.vector.LinearKernel;
import it.uniroma2.sag.kelp.learningalgorithm.regression.libsvm.EpsilonSvmRegression;
import it.uniroma2.sag.kelp.predictionfunction.Prediction;
import it.uniroma2.sag.kelp.predictionfunction.PredictionFunction;

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

public class EspilonSVRTest {
	// Accuracy: 0.9766667
	// F1 = 0.9769737
	private static PredictionFunction p = null;
	private static SimpleDataset trainingSet;
	private static SimpleDataset testSet;
	private static ArrayList<Float> scores;

	private Label regressionLabel = new StringLabel("r");

	@BeforeClass
	public static void learnModel() {
		trainingSet = new SimpleDataset();
		testSet = new SimpleDataset();
		try {
			trainingSet
					.populate("src/test/resources/svmTest/regression/mg_scale.klp");
			// Read a dataset into a test variable
			testSet.populate("src/test/resources/svmTest/regression/mg_scale.klp");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

		// define the regression label
		Label label = new StringLabel("r");

		// define the kernel
		Kernel kernel = new LinearKernel("0");

		// add a cache
		kernel.setKernelCache(new FixIndexKernelCache(trainingSet
				.getNumberOfExamples()));

		// define the learning algorithm
		EpsilonSvmRegression learner = new EpsilonSvmRegression(kernel, label,
				1, 0.1f);

		// learn and get the prediction function
		learner.learn(trainingSet);
		p = learner.getPredictionFunction();
	}

	@BeforeClass
	public static void loadClassificationScores() {
		try {
			scores = new ArrayList<Float>();
			String filepath = "src/test/resources/svmTest/regression/outscore_libsvm_regressor.txt";
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
	public void testMSE() {
		double mse = 0f;
		for (int i = 0; i < testSet.getExamples().size(); ++i) {
			Example e = testSet.getExample(i);
			Prediction score = p.predict(e);
			mse += Math
					.pow((double) (score.getScore(regressionLabel).floatValue() - e
							.getRegressionValue(regressionLabel)), 2.0);
		}
		mse /= (float) testSet.getExamples().size();
		Assert.assertEquals(0.0212349f, mse, 0.0001);
	}

	@Test
	public void checkScores() {
		for (int i = 0; i < testSet.getExamples().size(); ++i) {
			Example e = testSet.getExample(i);
			Prediction score = p.predict(e);
			Assert.assertEquals(scores.get(i).floatValue(),
					score.getScore(regressionLabel), 0.001f);
		}
	}
}
