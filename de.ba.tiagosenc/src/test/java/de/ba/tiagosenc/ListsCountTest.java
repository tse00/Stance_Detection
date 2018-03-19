package de.ba.tiagosenc;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.dkpro.tc.testing.FeatureTestUtil.assertFeature;

import java.util.HashSet;
import java.util.Set;

import static de.ba.tiagosenc.ListsCount.FOR_IND;
import static de.ba.tiagosenc.ListsCount.AGAINST_IND;
import static de.ba.tiagosenc.ListsCount.NEUTRAL_IND; 

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

public class ListsCountTest {
	
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
        

        jcas.setDocumentText("4ebb9e6db369a6f3d8d4ca62abfe61a3:::De camino a BARCELONA a Colaborar con nuestros compañeros @CiudadanosCs @InesArrimadas @Albert_Rivera #InesPresidenta http://t.co/BG15fEWWgE"
        		+ "55243fd5b76d3a58b978ddb0d5fb8061:::Mucho ánimo a todos los #ApoderadosCs! ¡A por todas @CiudadanosCs! Ha llegado el día de hacer que Artur Mas dimita por las urnas."
        		+ "903894bc15aa91055c40d7f5c31503f5:::Y entre quedarse quieto o cagarla, yo siempre he sido de los que prefieren pedir perdon #JuntsPelSi #27S http://t.co/rPRSlYJqWj"); 		
        		
        
        engine.process(jcas);
        
        System.out.println();
        TextClassificationTarget aTarget = new TextClassificationTarget(jcas, 0, jcas.getDocumentText().length());
        aTarget.addToIndexes();               

		System.out.println("Get Keys: " + NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 2).getKeys());

        ListsCount extractor = new ListsCount();
        Set<Feature> features = extractor.extract(jcas, aTarget);
        
        Assert.assertEquals(2, features.size());
                
        
        for (Feature feature : features) {

        	if (feature.getName().equals(FOR_IND)) { 
                assertFeature(FOR_IND, 2, feature);  
            }
            else if (feature.getName().equals(AGAINST_IND))
                assertFeature(AGAINST_IND, 5, feature);
/*            else
            	assertFeature(NEUTRAL_IND, 9, feature); */          
        }
	}
}