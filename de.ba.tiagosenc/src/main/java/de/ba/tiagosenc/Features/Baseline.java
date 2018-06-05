package de.ba.tiagosenc.Features;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.features.FeatureExtractor;
import org.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.dkpro.tc.api.type.TextClassificationTarget;


import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Baseline 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
	{
	
	int favorC = 0;
	int againstC = 0;
	int neutralC = 0;
	
	public static final String FOR_IND = "ForIndList";
	public static final String AGAINST_IND = "AgainstIndList";
	public static final String NEUTRAL_IND = "NeutralIndList";
	
	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
					
		ArrayList<String> favorInd = new ArrayList<String>(); 
		
		favorInd.add("arturo");
		favorInd.add("juntspelsi");
		favorInd.add("cupnacional");

	
		ArrayList<String> againstInd = new ArrayList<String>();

		againstInd.add("Rajoy");
		againstInd.add("Albiol");
		againstInd.add("inesarrimadas");
		againstInd.add("Rivera");
		againstInd.add("icelopeta");
		againstInd.add("miqueliceta");
		againstInd.add("ppopular");
		againstInd.add("CiudadanosCs");
		againstInd.add("PSC");


	    for (Token tk : JCasUtil.selectCovered(jcas, Token.class, aTarget)) {
		 	
	        String text = tk.getCoveredText().toLowerCase();
	
			Pattern p = Pattern.compile("^[a-zA-Z0-9_]+");
	        //Pattern p = Pattern.compile("[A-Za-z0-9_áéíóúüçñ]");
	
			Matcher m = p.matcher(text);
		
			while(m.find()) {

				for (String keyWord : againstInd) {
								
					if(text.contains(keyWord.toLowerCase())) { 

						againstC++;	
				    	
//						System.out.println(keyWord);
//						System.out.println("Count: " + againstC);		
					}
				}
			}	
	    }	
	    for (Token tk : JCasUtil.selectCovered(jcas, Token.class, aTarget)) {
	       	
	        String text = tk.getCoveredText().toLowerCase();
	
	//		Pattern p = Pattern.compile("[a-zA-Z0-9_]");
	//      Pattern p = Pattern.compile("[A-Za-z0-9_áéíóúüçñ]");
	        
			Pattern p = Pattern.compile("^[a-zA-Z0-9_]+");
	
			Matcher m = p.matcher(text);
		
			while(m.find()) {
				
				for (String keyWord : favorInd) {
								
					if(text.contains(keyWord.toLowerCase())) { 

						favorC++;
						
//						System.out.println(keyWord);
//						System.out.println("Count: " + favorC);		
	
					}
				}
			}	
		}	    
		
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FOR_IND, favorC));		      
	    features.add(new Feature(AGAINST_IND, againstC));
	
	    return features;
	}	

}
