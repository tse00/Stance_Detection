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

public class OntologyOnlyAdjectiv
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
{
	
	int favorAdjectivC = 0;
	int againstAdjectivC = 0;

	public static final String FAVOR_ADJECTIVONLY = "FavorAdjectivOnly";
	public static final String AGAINST_ADJECTIVONLY = "AgainstAdjectivOnly";

	File favorAdjectiv = new File("src/main/resources/Lists/Only_Adjectives_Favor.txt");
	File againstAdjectiv = new File("src/main/resources/Lists/Only_Adjectives_Against.txt");

	ArrayList<String> favorAdjectivL = new ArrayList<String>();
	ArrayList<String> againstAdjectivL = new ArrayList<String>();

	
	@Override
	public Set<Feature> extract(JCas jcas, TextClassificationTarget target) throws TextClassificationException {
		
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, target, true, false, 1, 3);

	    try {
	        
	        for (String keyWord : FileUtils.readLines(favorAdjectiv, "UTF-8")) {
	        	favorAdjectivL.add(keyWord.toLowerCase());
	            
	        	favorAdjectivC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + favorAdjectivC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + favorAdjectivC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
	    try {
	        
	        for (String keyWord : FileUtils.readLines(againstAdjectiv, "UTF-8")) {
	        	againstAdjectivL.add(keyWord.toLowerCase());
	            
	        	againstAdjectivC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + againstAdjectivC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + againstAdjectivC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FAVOR_ADJECTIVONLY, favorAdjectivC));		      
	    features.add(new Feature(AGAINST_ADJECTIVONLY, againstAdjectivC));

	    return features;


	}
}
