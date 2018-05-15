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

public class OntologyPartyPolitc
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
{
	
	int favorPartyC = 0;
	int favorPoliticianC = 0;
	
	int againstPartyC = 0;
	int againstPoliticianC = 0;
	
	public static final String FAVOR_PARTY2 = "FavorParty2";
	public static final String FAVOR_POLITICIAN2 = "FavorPolitician2";
	
	public static final String AGAINST_PARTY2 = "AgainstParty2";
	public static final String AGAINST_POLITICIAN2 = "AgainstPolitician2";
	
	
	File favorParty = new File("src/main/resources/Ontologies+Wiki/Only_Parties_Favor.txt");
	File favorPolitician = new File("src/main/resources/Ontologies+Wiki/Only_Politicians_Favor.txt");

	
	File againstParty = new File("src/main/resources/Ontologies+Wiki/Only_Parties_Against.txt");
	File againstPolitician = new File("src/main/resources/Ontologies+Wiki/Only_Politicians_Against.txt");
	
	
	ArrayList<String> favorPartyL = new ArrayList<String>();
	ArrayList<String> favorPoliticianL = new ArrayList<String>();
	
	ArrayList<String> againstPartyL = new ArrayList<String>();
	ArrayList<String> againstPoliticianL = new ArrayList<String>();
	
	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
		
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3);
	
	    ///////////////////   FAVOR    //////////////////////////
		
	    try {
	        
	        for (String keyWord : FileUtils.readLines(favorParty, "UTF-8")) {
	        	favorPartyL.add(keyWord.toLowerCase());
	            
	        	favorPartyC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + favorPartyC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + favorPartyC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	
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
	    
  
	    ///////////////////   AGAINST    //////////////////////////
	    
	    try {
	        
	        for (String keyWord : FileUtils.readLines(againstParty, "UTF-8")) {
	        	againstPartyL.add(keyWord.toLowerCase());
	            
	        	againstPartyC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + againstPartyC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + againstPartyC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
	    try {
	        
	        for (String keyWord : FileUtils.readLines(againstPolitician, "UTF-8")) {
	        	againstPartyL.add(keyWord.toLowerCase());
	            
	        	againstPoliticianC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + againstPoliticianC);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + againstPoliticianC + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FAVOR_PARTY2, favorPartyC));		      
	    features.add(new Feature(FAVOR_POLITICIAN2, favorPoliticianC));
	    
	    features.add(new Feature(AGAINST_PARTY2, againstPartyC));
	    features.add(new Feature(AGAINST_POLITICIAN2, againstPoliticianC));		      
	
	    return features;
	}	
}
