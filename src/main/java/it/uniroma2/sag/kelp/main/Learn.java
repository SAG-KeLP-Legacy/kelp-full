package it.uniroma2.sag.kelp.main;

import java.io.File;
import java.util.List;

import it.uniroma2.sag.kelp.data.dataset.SimpleDataset;
import it.uniroma2.sag.kelp.data.label.Label;
import it.uniroma2.sag.kelp.learningalgorithm.LearningAlgorithm;
import it.uniroma2.sag.kelp.predictionfunction.PredictionFunction;
import it.uniroma2.sag.kelp.utils.JacksonSerializerWrapper;

public class Learn {
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("USAGE: trainDataset learningAlgorithmJsonConfiguration modelPath");
			System.exit(1);
		}

		// Initialize some parameters
		String trainPath = args[0];
		String jsonPath = args[1];
		String modelPath = args[2];

		// Load a dataset
		SimpleDataset trainSet = new SimpleDataset();
		trainSet.populate(trainPath);

		// Instantiate a learning algorithm through a Json file
		JacksonSerializerWrapper serializer = new JacksonSerializerWrapper();
		LearningAlgorithm learner = serializer.readValue(new File(jsonPath), LearningAlgorithm.class);
		
		// Set classes if not specified in Json file
		if (learner.getLabels() == null || learner.getLabels().size() == 0) {
			List<Label> classes = trainSet.getClassificationLabels();
			if (classes.size() == 2) {
				learner.setLabels(classes.subList(0, 1));
			} else {
				learner.setLabels(classes);
			}
		}

		// Learn
		learner.learn(trainSet);

		// Save the model, a.k.a. PredictionFunction
		PredictionFunction predictionFunction = learner.getPredictionFunction();
		serializer.writeValueOnFile(predictionFunction, modelPath);
	}
}
