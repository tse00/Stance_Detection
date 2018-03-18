package de.ba.tiagosenc;

import static org.dkpro.tc.core.Constants.NGRAM_GLUE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.features.FeatureExtractor;
import org.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;


import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.ngrams.util.NGramStringListIterable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@TypeCapability(inputs = { "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token" })
//"de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS"
//"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence"




public class ListsCount
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor

{
	long favorC = 0L;
	int againstC = 0;
	int neutralC = 0;
	
    public static final String FOR_IND = "ForIndList";
    public static final String AGAINST_IND = "AgainstIndList";
	public static final String NEUTRAL_IND = "NeutralIndList";
	

	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
			
		Set<String> stopwords = new HashSet<String>();		
		stopwords.add("yo");
		
		List<String> favorInd = new ArrayList<String>();
				
				
	/////////////////////////////////////////////////////////////////////////////////////////////////////

				
			 
/*		documentNgrams = NGramUtils.getDocumentNgrams(jcas, ngramLowerCase,
           filterPartialStopwordMatches, ngramMinN, ngramMaxN, stopwords);*/

/*		for(String a : favorInd.getKeys())  // ????
			
			if (documentNgrams.getKeys().contains(a)) {
				
				favorC += NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 2).getCount(keyWord);

			}
		}	*/
		
	////////////////////////////////////////////////////////////////////	

/*    FrequencyDistribution<String> teacherNgrams = new FrequencyDistribution<String>();
		
    for	(Token tk : JCasUtil.select(jcas, Token.class))	{
		
		String text = tk.getCoveredText().toLowerCase();
		
    	if(tk().contains("T"))	{
			
    		for (List<String> ngram : new NGramStringListIterable(toText(selectCovered(Token.class,tt)), minN, maxN)) 
            {
    			//List<String> cleanedNgrams = removeSpecialCharacters(ngram);
                //if (passesNgramFilter(cleanedNgrams, stopwords, filterPartialMatches)) {
                    String ngramString = StringUtils.join(cleanedNgrams, NGRAM_GLUE);
                    if (lowerCaseNGrams) {
                        ngramString = ngramString.toLowerCase();
                    }
                    teacherNgrams.inc(ngramString);
                }
            }	
    	}
    }
    return teacherNgrams;*/
	
	////////////////////////////////////////////////
	
/*	        for (List<String> ngram : new NGramStringListIterable(
                keywordList.toArray(new String[keywordList.size()]), minN, maxN)) {
            String ngramString = StringUtils.join(ngram, GLUE);
            documentNgrams.inc(ngramString);
        }*/

		
	///////////////////////////////////////////////////////////////////////////
	
		//favorInd.add("Mas");
		//favorInd.add("arturo");
		//favorInd.add("Mariano_Rajoy");

		favorInd.add("Artur Mas");
		favorInd.add("juntspelsi");
		favorInd.add("cup");
		favorInd.add("cupnacional");
		favorInd.add("somrierecup");
		favorInd.add("governemos");

		ArrayList<String> favorW= new ArrayList<String>();
		
		//favorW.add("Visca Catalunya");
		favorW.add("libertad");
		favorW.add("llieure");
		favorW.add("#Si");
		//favorW.add("fent historia");
		//favorW.add("haciendo historia");
		//favorW.add("som la historia");
		favorW.add("independecia");
		favorW.add("independents");
		favorW.add("independentismo");

		ArrayList<String> favorAll= new ArrayList<String>();
		
        favorAll.addAll(favorInd);
        favorAll.addAll(favorW);
		
		ArrayList<String> againstInd = new ArrayList<String>();
		
		//againstInd.add("Mariano Rajoy");
		againstInd.add("Rajoy");
		againstInd.add("Albiol");
		//againstInd.add("Albiol_XG");
		againstInd.add("inesarrimadas");
		//againstInd.add("arrimadas");
		againstInd.add("Rivera");
		//againstInd.add("Albert Rivera");
		againstInd.add("miqueliceta");
		//againstInd.add("iceta");
		againstInd.add("icelopeta");
		//againstInd.add("pp");
		againstInd.add("ppopular");
		againstInd.add("plantemoscara");
		againstInd.add("CiudadanosCs");
		againstInd.add("ApoderadosCs");
		//againstInd.add("Cs");
		//againstInd.add("C's");
		//againstInd.add("voto naranja");
		//againstInd.add("partidos constitucionalistas");
		againstInd.add("Catsiqueespot");
		//againstInd.add("catalunya si que es pot");
		againstInd.add("PSC");
		againstInd.add("socialistas");
		againstInd.add("socialismo");
		//againstInd.add("socialistes_cat");

		ArrayList<String> againstW= new ArrayList<String>();

		againstW.add("Unidos");
		againstW.add("unida");
		againstW.add("unidad");
		againstW.add("indivisible");
		//againstW.add("viva espana");
		//againstW.add("Cataluna dentro Espana");
		againstW.add("juntsmillor");
		againstW.add("caminemjunts");
		againstW.add("llamadasqueunen");
		
		ArrayList<String> againstAll= new ArrayList<String>();
		
		againstAll.addAll(againstInd);
		againstAll.addAll(againstW);
        		
		
		boolean lowerCaseNGram = true;
		
		FrequencyDistribution<String> documentNgrams = new FrequencyDistribution<String>();
		NGramUtils ngramClass = new NGramUtils();	

		
/*		for (String text : JCasUtil.toText(JCasUtil.select(jcas, Token.class))) {
 	
	//		Pattern p = Pattern.compile("[a-zA-Z0-9_]");
	//      Pattern p = Pattern.compile("[A-Za-z0-9_áéíóúüçñ]");
	        
			Pattern p = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9_ ]+$");
	
			Matcher m = p.matcher(text);
			
	        documentNgrams = NGramUtils.getAnnotationNgrams(jcas, aTarget, true,
	                false, 1, 2, stopwords);
			
			while(m.find()) {				

				for (String keyWord : favorInd) {
					
		            if (documentNgrams.getKeys().contains(keyWord.toLowerCase()) && (text).equals(keyWord.toLowerCase())) {
								
					//if((text).equals(keyWord.toLowerCase())) { 
						
												
						favorC += documentNgrams.getCount(keyWord);
												
						System.out.println(keyWord);

						System.out.println("Count: " + favorC);						
					}	
				}
            }
		}*/	
		
	  
/*		for (String text : JCasUtil.toText(JCasUtil.select(jcas, Token.class))) {
			
	        for(List<String> ngram : new NGramStringListIterable(favorInd, 1, 2)) {
	        	
	        	for (String keyWord : ngram) {
					

		            keyWord = StringUtils.join(ngram, NGRAM_GLUE);
	                documentNgrams.inc(keyWord);

		            
		            if(text.equals(keyWord.toLowerCase())){
		            	
	            		favorC += documentNgrams.getCount(keyWord);
	            		
	    				System.out.println(keyWord);
	    				System.out.println("Count: " + favorC);
		            }	
		        }

	        }
			
		}*/
  
	    
		for (String text : JCasUtil.toText(JCasUtil.select(jcas, Token.class))) {
	    	
			for (String keyWord : favorInd) {

				if((text).equals(keyWord.toLowerCase())) { 
																										
					favorC += NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 2).getCount(keyWord);
	
					System.out.println(keyWord);
					//System.out.println("Count: " + favorC);
				}	
			}					
        }
	    
		System.out.println("\n" + "Total count: " + favorC);	
		

		Set<Feature> features = new HashSet<Feature>();
		
        features.add(new Feature(FOR_IND, favorC));		      
       // features.add(new Feature(AGAINST_IND, againstC, FeatureType.NUMERIC));
        //features.add(new Feature(NEUTRAL_IND, neutralC, FeatureType.NUMERIC));

        return features;
    }	
}                    