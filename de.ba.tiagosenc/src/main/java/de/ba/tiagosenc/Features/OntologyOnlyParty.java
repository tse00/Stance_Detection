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

public class OntologyOnlyParty 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
{
	
	int favorPartyC = 0;
	int againstPartyC = 0;
	
	public static final String FAVOR_PARTYONLY = "FavorPartyOnly";
	public static final String AGAINST_PARTYONLY = "AgainstPartyOnly";
	
	File favorParty = new File("src/main/resources/Lists/Only_Parties_Favor.txt");
	File againstParty = new File("src/main/resources/Lists/Only_Parties_Against.txt");
	
	ArrayList<String> favorPartyL = new ArrayList<String>();
	ArrayList<String> againstPartyL = new ArrayList<String>();


	@Override
	public Set<Feature> extract(JCas jcas, TextClassificationTarget target) throws TextClassificationException {

		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, target, true, false, 1, 3);
		
		
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
	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FAVOR_PARTYONLY, favorPartyC));		      
	    features.add(new Feature(AGAINST_PARTYONLY, againstPartyC));

	    return features;

	}	
	
	
	
	
}
