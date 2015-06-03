package it.uniroma2.sag.kelp.evaluation;

import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.data.label.StringLabel;
import it.uniroma2.sag.kelp.utils.evaluation.BinaryClassificationEvaluator;
import it.uniroma2.sag.kelp.utils.evaluation.MulticlassClassificationEvaluator;
import it.uniroma2.sag.kelp.utils.exception.NoSuchPerformanceMeasureException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvaluatorTest {
	private static List<Label> labels =null;
	
	@BeforeClass
	public static void createObjects() {
		labels = new ArrayList<Label>();
		labels.add(new StringLabel("one"));
		labels.add(new StringLabel("two"));
		labels.add(new StringLabel("three"));
	}
	
	@Test
	public void multiclassEvaluatorMethodCallingTest() {
		MulticlassClassificationEvaluator clEv = new MulticlassClassificationEvaluator(labels);
		try {
			clEv.getPerformanceMeasure("Accuracy");
			clEv.getPerformanceMeasure("accuracy");

			clEv.getPerformanceMeasure("MeanF1");
			clEv.getPerformanceMeasure("meanF1");
			
			clEv.getPerformanceMeasure("OverallPrecision");
			clEv.getPerformanceMeasure("overallPrecision");
			
			clEv.getPerformanceMeasure("OverallRecall");
			clEv.getPerformanceMeasure("overallRecall");
		} catch (NoSuchPerformanceMeasureException e) {
			Assert.assertTrue(false);
		}
		Assert.assertTrue(true);
	}
	
	@Test
	public void binaryEvaluatorMethodCallingTest() {
		BinaryClassificationEvaluator ev = new BinaryClassificationEvaluator(new StringLabel("one"));
		try {
			ev.getPerformanceMeasure("Accuracy");
			ev.getPerformanceMeasure("accuracy");
			
			ev.getPerformanceMeasure("F1");
			ev.getPerformanceMeasure("f1");
			
			ev.getPerformanceMeasure("Precision");
			ev.getPerformanceMeasure("precision");
			
			ev.getPerformanceMeasure("Recall");
			ev.getPerformanceMeasure("recall");
		} catch (NoSuchPerformanceMeasureException e) {
			Assert.assertTrue(false);
		}
		Assert.assertTrue(true);
	}
}
