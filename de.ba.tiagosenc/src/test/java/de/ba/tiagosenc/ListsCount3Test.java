package de.ba.tiagosenc;

import static de.ba.tiagosenc.ListsCount3.COUNT;
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

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;


public class ListsCount3Test{
	
	
	@Test
	public void ListsExtractorTest() 
			throws Exception
	{
		AnalysisEngineDescription desc = createEngineDescription(
		    createEngineDescription(BreakIteratorSegmenter.class));
		
		AnalysisEngine engine = createEngine(desc);
		
		JCas jcas = engine.newJCas();
		jcas.setDocumentLanguage("es");
		
        jcas.setDocumentText("miqueliceta juntsmillor");
        engine.process(jcas);
        
        System.out.println();
        TextClassificationTarget aTarget = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
        aTarget.addToIndexes();               
        
		Set<String> stopwords = new HashSet<String>();		
		stopwords.add("yo");
		
		
		System.out.println("Get Keys: " + NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3, stopwords, Token.class).getKeys());

		ListsCount3 extractor = new ListsCount3(); 
		
        Set<Feature> features = extractor.extract(jcas, aTarget);
        
        Assert.assertEquals(1, features.size());
                        
        for (Feature feature : features) {

        	if (feature.getName().equals(COUNT)) { 
                assertFeature(COUNT, 1, feature);  
            }
        }
	}	
}
