package de.ba.tiagosenc;

import static de.ba.tiagosenc.Features.Baseline.AGAINST_IND;
import static de.ba.tiagosenc.Features.Baseline.FOR_IND;
import static de.ba.tiagosenc.Features.Baseline.NEUTRAL_IND;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.dkpro.tc.testing.FeatureTestUtil.assertFeature;

import java.util.HashSet;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;
import org.junit.Assert;
import org.junit.Test;

import de.ba.tiagosenc.Features.Baseline;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class BaselineTest {
	
	@Test
	public void ListsExtractorTest() 
			throws Exception
	
	{
        AnalysisEngineDescription desc = createEngineDescription(
                createEngineDescription(BreakIteratorSegmenter.class));

        AnalysisEngine engine = createEngine(desc);

        JCas jcas = engine.newJCas();
        jcas.setDocumentLanguage("es");
        
        jcas.setDocumentText("Yo voto ppopular Rajoy. Basta juntspelsi");
        engine.process(jcas);
        
        System.out.println();
        TextClassificationTarget aTarget = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
        aTarget.addToIndexes();               
        
		Set<String> stopwords = new HashSet<String>();		
		stopwords.add("yo");		
		
		
        Baseline extractor = new Baseline();
        Set<Feature> features = extractor.extract(jcas, aTarget);
        
        Assert.assertEquals(2, features.size());
                
        
        for (Feature feature : features) {

        	if (feature.getName().equals(FOR_IND)) { 
                assertFeature(FOR_IND, 1, feature);  
            }
            else if (feature.getName().equals(AGAINST_IND))
                assertFeature(AGAINST_IND, 2, feature);
            else
            	assertFeature(NEUTRAL_IND, 0, feature);          
        }
	}
}