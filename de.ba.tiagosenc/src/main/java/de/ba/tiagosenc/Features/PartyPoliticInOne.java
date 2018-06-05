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

public class PartyPoliticInOne	
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
{

	int favorPPc = 0;
	int againstPPc = 0;
	
	public static final String FAVOR_PP = "FavorPartyPolitician";
	public static final String AGAINST_PP = "AgainstPartyPolitician";
	
	
	File favorPPf = new File("src/main/resources/Lists/All_PP_Favor.txt");	

	File againstPPf = new File("src/main/resources/Lists/All_PP_Against.txt");
	
	
	ArrayList<String> favorPPl = new ArrayList<String>();	
	ArrayList<String> againstPPl = new ArrayList<String>();
	
	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
		
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3);
	
	    ///////////////////   FAVOR    //////////////////////////
		
	    try {
	        
	        for (String keyWord : FileUtils.readLines(favorPPf, "UTF-8")) {
	        	favorPPl.add(keyWord.toLowerCase());
	            
	        	favorPPc += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + favorPPc);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + favorPPc + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
  
	    ///////////////////   AGAINST    //////////////////////////
	    
	    try {
	        
	        for (String keyWord : FileUtils.readLines(againstPPf, "UTF-8")) {
	        	againstPPl.add(keyWord.toLowerCase());
	            
	        	againstPPc += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + againstPPc);	
	        }
			
//	        System.out.println("\n" + "Total count favor: " + againstPPc + "\n");	
	
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FAVOR_PP, favorPPc));		      	    
	    features.add(new Feature(AGAINST_PP, againstPPc));
	
	    return features;
	}	
}
