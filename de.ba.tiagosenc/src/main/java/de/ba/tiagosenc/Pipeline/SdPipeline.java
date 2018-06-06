package de.ba.tiagosenc.Pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.lab.Lab;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;
import org.dkpro.tc.api.features.TcFeatureFactory;
import org.dkpro.tc.api.features.TcFeatureSet;
import org.dkpro.tc.core.Constants;
import org.dkpro.tc.features.ngram.LuceneNGram;
import org.dkpro.tc.ml.ExperimentCrossValidation;
import org.dkpro.tc.ml.ExperimentTrainTest;
import org.dkpro.tc.ml.report.BatchCrossValidationReport;
import org.dkpro.tc.ml.weka.WekaClassificationAdapter;

import de.ba.tiagosenc.Evaluation.BatchOwnCVReport;
import de.ba.tiagosenc.Evaluation.BatchOwnTTReport;
import de.ba.tiagosenc.Features.BlankOntology;
import de.ba.tiagosenc.Features.InOne;
import de.ba.tiagosenc.Features.Lists2;
import de.ba.tiagosenc.Features.OnlyAdjectiv;
import de.ba.tiagosenc.Features.OnlyParty;
import de.ba.tiagosenc.Features.OnlyPolitician;
import de.ba.tiagosenc.Features.PartyPoliticInOne;
import de.ba.tiagosenc.Reader.ReaderSD;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import weka.classifiers.functions.SMO;

import org.dkpro.tc.evaluation.confusion.matrix.SmallContingencyTables;
import org.dkpro.tc.evaluation.evaluator.single.SingleEvaluator;


@SuppressWarnings("unused")
public class SdPipeline 
	implements Constants
{
	
	public static final String FILEPATH_TRAIN = "src/main/resources/Data/training_tweets_es.txt";
	public static final String FILEPATH_TEST = "src/main/resources/Data/test_tweets_es.txt";
	public static final String FILEPATH_GOLD_LABELS = "src/main/resources/Data/training_truth_es.txt";
	
	
	public static void main(String[] args)
			throws Exception
	{

		//path to your DKpro Directory
		System.setProperty("DKPRO_HOME", "C:/Users/TSE/DKPRO_HOME");

		ParameterSpace pSpace = getParameterSpace();
		
		SdPipeline experiment = new SdPipeline();
		experiment.runCrossValidation(pSpace);
//        experiment.runTrainTest(pSpace);
		
	}

	private static ParameterSpace getParameterSpace()
		throws ResourceInitializationException
	{
		
		Map<String, Object> dimReaders = new HashMap<String, Object>();
		
		
		CollectionReaderDescription readerTrain = CollectionReaderFactory.createReaderDescription(
		
			ReaderSD.class, ReaderSD.PARAM_TWEET_FILE, FILEPATH_TRAIN,
			ReaderSD.PARAM_GOLD_FILE, FILEPATH_GOLD_LABELS);
		
		CollectionReaderDescription readerTest = CollectionReaderFactory.createReaderDescription(
				
			ReaderSD.class, 
			ReaderSD.PARAM_TWEET_FILE, FILEPATH_TEST,
			ReaderSD.PARAM_GOLD_FILE, FILEPATH_GOLD_LABELS);
		
		dimReaders.put(DIM_READER_TRAIN, readerTrain);
	    dimReaders.put(DIM_READER_TEST, readerTest);
	   
		
	    @SuppressWarnings("unchecked")
		Dimension<List<String>> dimClassificationArgs = Dimension.create(DIM_CLASSIFICATION_ARGS,
                Arrays.asList(new String[] { SMO.class.getName() }));
		
		
		Dimension<TcFeatureSet> dimFeatureSets = Dimension.create(
				DIM_FEATURE_SET,
                new TcFeatureSet(
//                        TcFeatureFactory.create(PartyPoliticInOne.class),
//                        TcFeatureFactory.create(Baseline.class),    		
                        TcFeatureFactory.create(Lists2.class),
//                        TcFeatureFactory.create(InOne.class),
//                        TcFeatureFactory.create(OnlyParty.class),
                        TcFeatureFactory.create(OnlyPolitician.class),
                        TcFeatureFactory.create(OnlyAdjectiv.class),
//                		  TcFeatureFactory.create(BlankOntology.class)
                              TcFeatureFactory.create(LuceneNGram.class,
                        		LuceneNGram.PARAM_NGRAM_LOWER_CASE, true,
                        		LuceneNGram.PARAM_NGRAM_MIN_N, 1,
                        		LuceneNGram.PARAM_NGRAM_MAX_N, 3,
                        		LuceneNGram.PARAM_NGRAM_USE_TOP_K, 500,
                        		LuceneNGram.PARAM_NGRAM_STOPWORDS_FILE, "src/main/resources/Data/spanish_stopwords.txt"
                        	)));				

		ParameterSpace pSpace = new ParameterSpace(Dimension.createBundle("readers", dimReaders),
			Dimension.create(DIM_LEARNING_MODE, LM_SINGLE_LABEL),
			Dimension.create(DIM_FEATURE_MODE, FM_DOCUMENT),
			dimFeatureSets, dimClassificationArgs);
				
		return pSpace;
	} 
	
	BatchCrossValidationReport a = new BatchCrossValidationReport();
	
	
	// Cross-Validation
	protected void runCrossValidation(ParameterSpace pSpace)
		throws Exception
	{
		
        ExperimentCrossValidation batch = new ExperimentCrossValidation("TwitterStanceCV", 
        		WekaClassificationAdapter.class, 10);
        batch.setPreprocessing(getPreprocessing());
        batch.setParameterSpace(pSpace);
        batch.addReport(BatchOwnCVReport.class);

        // Run
        Lab.getInstance().run(batch);
		
	}
	

	// Train-Test
	protected void runTrainTest(ParameterSpace pSpace) 
		throws Exception
	{
        ExperimentTrainTest batch = new ExperimentTrainTest("TwitterStanceTrainTest", WekaClassificationAdapter.class);
        batch.setPreprocessing(getPreprocessing());
        batch.setParameterSpace(pSpace);
        batch.addReport(BatchOwnTTReport.class);

        // Run
        Lab.getInstance().run(batch);
		
	}

	private AnalysisEngineDescription getPreprocessing()
		throws ResourceInitializationException
	{	    
        return
        		createEngineDescription(createEngineDescription(ArktweetTokenizer.class)); 

	}
}

