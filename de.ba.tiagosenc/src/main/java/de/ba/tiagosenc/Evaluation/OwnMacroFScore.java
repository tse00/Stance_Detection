package de.ba.tiagosenc.Evaluation;

import java.util.HashMap;
import java.util.Map;

import org.dkpro.tc.evaluation.confusion.matrix.SmallContingencyTables;
import org.dkpro.tc.evaluation.measures.label.MacroFScore;

public class OwnMacroFScore
{

	public static Map<String, Double> calculate(SmallContingencyTables smallContTables, 
			boolean softEvaluation) 
	{
		int numberOfTables = smallContTables.getSize();
		double summedFScore = 0.0;
		Map<String, Double> results = new HashMap<String, Double>();
		
		Double result = 0.0;
		for (int i = 0; i < numberOfTables; i++){
			double tp = smallContTables.getTruePositives(i);
			double fp = smallContTables.getFalsePositives(i);
			double fn = smallContTables.getFalseNegatives(i);
			
			double localSum = 0.0;
			double precision = 0.0;
			double recall = 0.0;
			if ((localSum = tp + fp) != 0.0) {
				precision = tp / localSum;
			}
			if ((localSum = tp + fn) != 0.0) {
				recall = tp / localSum;
			}
			if ((localSum = precision + recall) != 0.0) {
				summedFScore += (2 * precision * recall) / localSum;
			}
			else if (! softEvaluation) {
				result = Double.NaN;
				break;
			}
		}	
		
		if (result == 0.0){
			result = (Double) summedFScore / numberOfTables;
		}
		results.put(MacroFScore.class.getSimpleName(), result);
		return results;
	}

	
	public static Map<String, Double> calculateExtraIndividualLabelMeasures(SmallContingencyTables smallContTables,
			boolean softEvaluation, Map<Integer, String> number2class) 
	{
		int numberOfTables = smallContTables.getSize();
		Double[] fScore = new Double[numberOfTables];
		double summedFScore = 0.0;
		Map<String, Double> results = new HashMap<String, Double>();
		
		boolean computableCombined = true;
		for (int i = 0; i < numberOfTables; i++){
			double tp = smallContTables.getTruePositives(i);
			double fp = smallContTables.getFalsePositives(i);
			double fn = smallContTables.getFalseNegatives(i);
						
			double localSum = 0.0;
			double precision = 0.0;
			double recall = 0.0;
			String key = MacroFScore.class.getSimpleName() + "_" + number2class.get(i);
			if ((localSum = tp + fp) != 0.0) {
				precision = tp / localSum;
			}
			if ((localSum = tp + fn) != 0.0) {
				recall = tp / localSum;
			}
			if ((localSum = precision + recall) != 0.0) {
				fScore[i] = (Double) (2 * precision * recall) / localSum;
				summedFScore += fScore[i];
				results.put(key, fScore[i]);
			}
			else if (! softEvaluation) {
				results.put(key, Double.NaN);
				computableCombined = false;
			}
		}	
		Double combinedFScore = Double.NaN;
		if (computableCombined){
			combinedFScore = (Double) summedFScore / numberOfTables;
		} 
		results.put(MacroFScore.class.getSimpleName(), combinedFScore);
		return results;
	}	
	
	public static Map<String, Double> calculateIberEvalMetric(SmallContingencyTables smallContTables,
			boolean softEvaluation, Map<Integer, String> number2class) 
	{
		int numberOfTables = smallContTables.getSize();
		Double[] fScore = new Double[numberOfTables];
		double summedFScore = 0.0;
		Map<String, Double> results = new HashMap<String, Double>();
		
		boolean computableCombined = true;
		for (int i = 0; i < numberOfTables; i++){
			double tp = smallContTables.getTruePositives(i);
			double fp = smallContTables.getFalsePositives(i);
			double fn = smallContTables.getFalseNegatives(i);
						
			double localSum = 0.0;
			double precision = 0.0;
			double recall = 0.0;
			String key = MacroFScore.class.getSimpleName() + "_" + number2class.get(i);
			if ((localSum = tp + fp) != 0.0) {
				precision = tp / localSum;
			}
			if ((localSum = tp + fn) != 0.0) {
				recall = tp / localSum;
			}
			if ((localSum = precision + recall) != 0.0) {
				fScore[i] = (Double) (2 * precision * recall) / localSum;
				summedFScore += fScore[i];
				results.put(key, fScore[i]);
			}
			
			///////   HERE ////////////////////////
			if ((localSum = precision + recall) != 0.0) {
				fScore[i] = (Double) (2 * precision * recall) / localSum;
				summedFScore += (fScore[2]+fScore[3])/2;
				results.put(key, fScore[i]);
			}
			
			else if (! softEvaluation) {
				results.put(key, Double.NaN);
				computableCombined = false;
			}
		}	
		Double combinedFScore = Double.NaN;
		if (computableCombined){
			combinedFScore = (Double) summedFScore / numberOfTables;
		} 
		results.put(MacroFScore.class.getSimpleName(), combinedFScore);
		return results;
	}
}
