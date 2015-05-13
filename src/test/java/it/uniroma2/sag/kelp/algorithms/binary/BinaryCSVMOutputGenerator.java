package it.uniroma2.sag.kelp.algorithms.binary;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.example.Example;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.kernel.vector.LinearKernel;
import it.uniroma2.sag.kelp.learningalgorithm.classification.libsvm.BinaryCSvmClassification;
import it.uniroma2.sag.kelp.predictionfunction.classifier.ClassificationOutput;
import it.uniroma2.sag.kelp.predictionfunction.classifier.Classifier;
import it.uniroma2.sag.kelp.utils.evaluation.BinaryClassificationEvaluator;

import java.io.PrintStream;

public class BinaryCSVMOutputGenerator {

	public static void main(String[] args) {
		try {
			// Read a dataset into a trainingSet variable
			SimpleDataset trainingSet = new SimpleDataset();
			trainingSet.populate("src/test/resources/binaryCSvmTest/train.klp");
			// Read a dataset into a test variable
			SimpleDataset testSet = new SimpleDataset();
			testSet.populate("src/test/resources/binaryCSvmTest/test.klp");

			// define the positive class
			StringLabel positiveClass = new StringLabel("+1");

			BinaryCSvmClassification learner = new BinaryCSvmClassification(new LinearKernel("0"), positiveClass, 1, 1);
			// learn and get the prediction function
			learner.learn(trainingSet);
			Classifier f = learner.getPredictionFunction();
			
			PrintStream ps = new PrintStream("src/test/resources/binaryCSvmTest/outScores.txt", "utf8");
			
			// classify examples and compute some statistics
			int correct = 0;
			BinaryClassificationEvaluator ev = new BinaryClassificationEvaluator(positiveClass);
			int predicted = 0;
			int tobe = 0;
			int cf1=0;
			for (Example e : testSet.getExamples()) {
				ClassificationOutput p = f.predict(testSet.getNextExample());
				tobe++;
				
				ps.println(p.getScore(positiveClass));
				if (p.getScore(positiveClass) >= 0)
					predicted++;
				if (p.getScore(positiveClass) >= 0
						&& e.isExampleOf(positiveClass)) {
					correct++;
					cf1++;
				} else if (p.getScore(positiveClass) < 0
						&& !e.isExampleOf(positiveClass))
					correct++;

				ev.addCount(e, p);
			}
			ps.flush();
			ps.close();
			System.out
					.println("Accuracy: "
							+ ((float) correct / (float) testSet
									.getNumberOfExamples()));

			float prec = (float)cf1/(float)predicted;
			float rec = (float)cf1/(float)tobe;
			float f1 = 2*prec*rec/(prec+rec);
			
			System.out.println("ClassificationEvaluator Accuracy: "
					+ ev.getPerformanceMeasure("getAccuracy"));
			System.out.println("InClass F1: "
					+ f1);
			System.out.println("ClassificationEvaluatorF1: "
					+ ev.getPerformanceMeasure("getF1"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
