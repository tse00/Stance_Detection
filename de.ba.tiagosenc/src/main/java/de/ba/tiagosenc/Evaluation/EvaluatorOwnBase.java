package de.ba.tiagosenc.Evaluation;

import java.util.HashMap;
import org.dkpro.tc.evaluation.measures.example.SubsetAccuracy;
import java.util.Map;

import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.evaluation.Id2Outcome;
import org.dkpro.tc.evaluation.confusion.matrix.CombinedSmallContingencyTable;
import org.dkpro.tc.evaluation.confusion.matrix.SmallContingencyTables;
import org.dkpro.tc.evaluation.measures.label.MacroFScore;
import org.dkpro.tc.evaluation.measures.label.MacroPrecision;
import org.dkpro.tc.evaluation.measures.label.MacroRecall;
import org.dkpro.tc.evaluation.measures.label.MicroFScore;
import org.dkpro.tc.evaluation.measures.label.MicroPrecision;
import org.dkpro.tc.evaluation.measures.label.MicroRecall;

public abstract class EvaluatorOwnBase {

	protected Id2Outcome id2Outcome;
	protected boolean softEvaluation;
	protected boolean individualLabelMeasures;
	
	SubsetAccuracy a = new 	SubsetAccuracy();


	public EvaluatorOwnBase(Id2Outcome id2Outcome, boolean softEvaluation,
			boolean individualLabelMeasures) {
		super();
		this.id2Outcome = id2Outcome;
		this.softEvaluation = softEvaluation;
		this.individualLabelMeasures = individualLabelMeasures;
	}
    public abstract Map<String, Double> calculateEvaluationMeasures() throws TextClassificationException;

    public abstract Map<String, Double> calculateMicroEvaluationMeasures() throws TextClassificationException;
	
	/**
	 * calculation of label based macro measures 
	 * 
	 * @param cTable
	 * @return
	 */
	protected Map<String, Double> calculateMacroMeasures(SmallContingencyTables cTable)
	{
		Map<String, Double> results = new HashMap<String, Double>();
		Map<String, Double> macroFScRes;
		Map<String, Double> iberEval;
		
		Map<String, Double> macroPrRes;
		Map<String, Double> macroReRes;

		if (individualLabelMeasures) {
			Map<Integer, String> number2class = new  HashMap<Integer, String>();
			for (String classValue : cTable.getClass2Number().keySet()) {
				Integer number = cTable.getClass2Number().get(classValue);
				number2class.put(number, classValue);
			}

			macroFScRes = MacroFScore.calculateExtraIndividualLabelMeasures(cTable, 
					softEvaluation, number2class);
			
			iberEval = MacroFScore.calculateExtraIndividualLabelMeasures(cTable, 
					softEvaluation, number2class);
			
			macroPrRes = MacroPrecision.calculateExtraIndividualLabelMeasures(cTable, 
					softEvaluation, number2class);
			macroReRes = MacroRecall.calculateExtraIndividualLabelMeasures(cTable, 
					softEvaluation, number2class);
		}
		else{
			macroFScRes = MacroFScore.calculate(cTable, softEvaluation);
			macroPrRes = MacroPrecision.calculate(cTable, softEvaluation);
			macroReRes = MacroRecall.calculate(cTable, softEvaluation);
		}
		results.putAll(macroFScRes);
//		results.putAll(macroPrRes);
//		results.putAll(macroReRes);
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
	
	/**
	 * calculation of label based micro measures 
	 * 
	 * @param cCTable
	 * @return
	 */
	protected Map<String, Double> calculateMicroMeasures(CombinedSmallContingencyTable cCTable)
	{
		Map<String, Double> results = new HashMap<String, Double>();
		
		Map<String, Double> microFScRes = MicroFScore.calculate(cCTable, softEvaluation);
		Map<String, Double> microPrRes = MicroPrecision.calculate(cCTable, softEvaluation);
		Map<String, Double> microReRes = MicroRecall.calculate(cCTable, softEvaluation);
		
		results.putAll(microFScRes);
		results.putAll(microPrRes);
		results.putAll(microReRes);		
		return results;
	}
}

