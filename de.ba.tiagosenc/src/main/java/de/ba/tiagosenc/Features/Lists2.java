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


public class Lists2
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor

{
	int favorC = 0;
	int againstC = 0;
	int neutralC = 0;
	
	public static final String FOR_IND = "ForIndList";
	public static final String AGAINST_IND = "AgainstIndList";
	public static final String NEUTRAL_IND = "NeutralIndList";
	
	File favorFile = new File("src/main/resources/Lists/List_Favor.txt");
	File againstFile = new File("src/main/resources/Lists/List_Against.txt");	

	
	ArrayList<String> listF = new ArrayList<String>();
	ArrayList<String> listA = new ArrayList<String>();

	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
		
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 3);
		
	    ///////////////////   FAVOR    //////////////////////////

		
        try {
            
            for (String keyWord : FileUtils.readLines(favorFile, "UTF-8")) {
                listF.add(keyWord.toLowerCase());
                
				favorC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count favor: " + favorC);	
            }
			
//            System.out.println("\n" + "Total count favor: " + favorC + "\n");	

		} catch (IOException e2) {
			e2.printStackTrace();
		}
       
	    ///////////////////   AGAINST    //////////////////////////

        
        try {
            
            for (String keyWord : FileUtils.readLines(againstFile, "UTF-8")) {
                listA.add(keyWord.toLowerCase());
                
                againstC += fd.getCount(keyWord.toLowerCase());
				
//				System.out.println(keyWord);
//				System.out.println("Count against: " + againstC);	
            }
			
//            System.out.println("\n" + "Total count against: " + againstC);	

		} catch (IOException e2) {
			e2.printStackTrace();
		}
	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FOR_IND, favorC));		      
	    features.add(new Feature(AGAINST_IND, againstC));
	
	    return features;
	}	

}

