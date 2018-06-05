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

public class InOne 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
	{
	
	int allC = 0;
	
	public static final String AllKeyWords = "AllKeyWords";
	
	File AllFile = new File("src/main/resources/Lists/All.txt");	
	
	ArrayList<String> list = new ArrayList<String>();

	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
			
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3);
	

	    try {
	        
	        for (String keyWord : FileUtils.readLines(AllFile, "UTF-8")) {
	            list.add(keyWord.toLowerCase());
	            
				allC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + allC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + allC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
	    	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(AllKeyWords, allC));		      
	
	    return features;
	}	
	
}