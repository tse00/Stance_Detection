package de.ba.tiagosenc;

import static de.ba.tiagosenc.Features.ListsCountNgram.AGAINST_IND;
import static de.ba.tiagosenc.Features.ListsCountNgram.FOR_IND;
import static de.ba.tiagosenc.Features.ListsCountNgram.NEUTRAL_IND;
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

import de.ba.tiagosenc.Features.ListsCountNgram;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class ListsCountNgramTest {
	
	@Test
	public void ListsExtractorTest() 
			throws Exception
	
	{
        AnalysisEngineDescription desc = createEngineDescription(
                createEngineDescription(BreakIteratorSegmenter.class));

        AnalysisEngine engine = createEngine(desc);

        JCas jcas = engine.newJCas();
        //jcas.setDocumentLanguage("es");
/*        jcas.setDocumentText("4ebb9e6db369a6f3d8d4ca62abfe61a3:::De @InesArrimadas Juntspelsi camino a BARCELONA a Colaborar con "
        		+ "nuestros compañeros @CiudadanosCs  @Albert_Rivera #InesPresidenta "
        		+ "http://t.co/BG15fEWWgE" + "/n" + "55243fd5b76d3a58b978ddb0d5fb8061:::Mucho ánimo a todos los "
        				+ "#ApoderadosCs! ¡A por todas ! Ha llegado el día de hacer que Artur "
        				+ "mas dimita por las urnas.");*/
        

        jcas.setDocumentText("Vamos cataluña, despierta, tu voto hoy es muy importante. Hoy puede ser un\r\n" + 
        		"buen dia. @miqueliceta #27S @socialistes cat"); 		
        		
        
        engine.process(jcas);
        
        System.out.println();
        TextClassificationTarget aTarget = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
        aTarget.addToIndexes();               

		System.out.println("Get Keys: " + NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 2).getKeys());

        ListsCountNgram extractor = new ListsCountNgram();
        Set<Feature> features = extractor.extract(jcas, aTarget);
        
        Assert.assertEquals(2, features.size());
                
        
        for (Feature feature : features) {

        	if (feature.getName().equals(FOR_IND)) { 
                assertFeature(FOR_IND, 0, feature);  
            }
            else if (feature.getName().equals(AGAINST_IND))
                assertFeature(AGAINST_IND, 2, feature);
/*            else
            	assertFeature(NEUTRAL_IND, 9, feature); */          
        }
	}
}
