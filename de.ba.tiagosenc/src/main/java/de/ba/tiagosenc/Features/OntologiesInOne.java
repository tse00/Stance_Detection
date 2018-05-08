package de.ba.tiagosenc.Features;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.features.FeatureExtractor;
import org.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

public class OntologiesInOne 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
	{
	
	int allC = 0;
	
	public static final String AllKeyWords = "AllKeyWords";
	
	File AllFile = new File("src/main/resources/Lists/All.txt");	
	File masFile = new File("src/main/resources/Lists/OnlyMasUppercase.txt");	

	
	ArrayList<String> list = new ArrayList<String>();
	ArrayList<String> mas = new ArrayList<String>();

	
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
	    
/*		try {
			for (String keyWord : FileUtils.readLines(masFile, "UTF-8")) {
			    mas.add(keyWord);

			    allC += NGramUtils.getDocumentNgrams(jcas, aTarget, false, false, 1, 3).getCount(keyWord);

//				System.out.println(keyWord);
//				System.out.println("Count favor: " + allC);
				
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}*/
	    	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(AllKeyWords, allC));		      
	
	    return features;
	}	
	
}