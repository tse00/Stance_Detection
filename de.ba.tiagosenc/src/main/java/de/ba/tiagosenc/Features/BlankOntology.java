package de.ba.tiagosenc.Features;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.features.FeatureExtractor;
import org.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

public class BlankOntology 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
{
	
	int blankC = 0;
	
	public static final String AllKeyWords = "AllKeyWords";
	
	File blankFile = new File("src/main/resources/Lists/BlankList.txt");	

	
	ArrayList<String> list = new ArrayList<String>();
	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
			
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3);
	

	    try {
	        
	        for (String keyWord : FileUtils.readLines(blankFile, "UTF-8")) {
	            list.add(keyWord.toLowerCase());
	            
				blankC += fd.getCount(keyWord.toLowerCase());
			
	       }
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(AllKeyWords, blankC));		      
	
	    return features;
	}	
	
}
