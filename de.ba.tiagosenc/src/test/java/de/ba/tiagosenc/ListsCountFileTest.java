package de.ba.tiagosenc;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.dkpro.tc.testing.FeatureTestUtil.assertFeature;

import java.util.HashSet;
import java.util.Set;
 
import static de.ba.tiagosenc.ListsCountFile.FOR_IND;
import static de.ba.tiagosenc.ListsCountFile.AGAINST_IND;
import static de.ba.tiagosenc.ListsCountFile.NEUTRAL_IND;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;
import org.junit.Assert;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;





public class ListsCountFileTest {
	
	@Test
	public void ListsExtractorTest() 
			throws Exception
	
	{
        AnalysisEngineDescription desc = createEngineDescription(
                createEngineDescription(BreakIteratorSegmenter.class));

        AnalysisEngine engine = createEngine(desc);

        JCas jcas = engine.newJCas();
        jcas.setDocumentLanguage("es");
        
        jcas.setDocumentText("Yo soy cupnacional y governemos");
        engine.process(jcas);
        
        System.out.println();
        TextClassificationTarget aTarget = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
        aTarget.addToIndexes();               
        
		
        ListsCountFile extractor = new ListsCountFile();
        Set<Feature> features = extractor.extract(jcas, aTarget);
        
        Assert.assertEquals(1, features.size());
                
        
        for (Feature feature : features) {

        	if (feature.getName().equals(FOR_IND)) { 
                assertFeature(FOR_IND, 2, feature);  
            }
            else if (feature.getName().equals(AGAINST_IND))
                assertFeature(AGAINST_IND, 0, feature);
            else
            	assertFeature(NEUTRAL_IND, 0, feature);          
        }
	}
}