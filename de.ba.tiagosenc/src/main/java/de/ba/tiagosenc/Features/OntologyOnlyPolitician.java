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

public class OntologyOnlyPolitician 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
{
	int favorPoliticianC = 0;
	int againstPoliticianC = 0;

	public static final String FAVOR_POLITICIANONLY = "FavorPoliticianOnly";
	public static final String AGAINST_POLITICIANONLY = "AgainstPoliticianOnly";
	
	File favorPolitician = new File("src/main/resources/Lists/Only_Politicians_Favor.txt");
	File againstPolitician = new File("src/main/resources/Lists/Only_Politicians_Against.txt");

	ArrayList<String> favorPoliticianL = new ArrayList<String>();
	ArrayList<String> againstPoliticianL = new ArrayList<String>();


	@Override
	public Set<Feature> extract(JCas jcas, TextClassificationTarget target) throws TextClassificationException {

		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, target, true, false, 1, 3);

	    try {
	        
	        for (String keyWord : FileUtils.readLines(favorPolitician, "UTF-8")) {
	        	favorPoliticianL.add(keyWord.toLowerCase());
	            
	        	favorPoliticianC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + favorPoliticianC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + favorPoliticianC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
	    try {
	        
	        for (String keyWord : FileUtils.readLines(againstPolitician, "UTF-8")) {
	        	againstPoliticianL.add(keyWord.toLowerCase());
	            
	        	againstPoliticianC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + againstPoliticianC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + againstPoliticianC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
		Set<Feature> features = new HashSet<Feature>();

	    features.add(new Feature(FAVOR_POLITICIANONLY, favorPoliticianC));
	    features.add(new Feature(AGAINST_POLITICIANONLY, againstPoliticianC));		      

	    return features;

	}

}
