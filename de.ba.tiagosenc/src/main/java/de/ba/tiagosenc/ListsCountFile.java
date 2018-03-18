package de.ba.tiagosenc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
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



public class ListsCountFile
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
	{
	
	int favorC = 0;
	int againstC = 0;
	int neutralC = 0;
	
	public static final String FOR_IND = "ForIndList";
	public static final String AGAINST_IND = "AgainstIndList";
	public static final String NEUTRAL_IND = "NeutralIndList";
	
	ArrayList<String> list = new ArrayList<String>();
	
	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
		Scanner scan;
		try {
			scan = new Scanner (new File("src/main/resources/Lists/List_Favor.txt"));
			
			for (Token tk : JCasUtil.selectCovered(jcas, Token.class, aTarget)) {
				
				String text = tk.getCoveredText().toLowerCase();
				
				Pattern p = Pattern.compile("[a-zA-Z0-9_]");
				
				Matcher m = p.matcher(text);
				
				
				while(scan.hasNext()){ // .hasNextLine ?
						
					list.add(scan.next());

					for(String keyWord : list){
						
						if (m.find() && keyWord.equalsIgnoreCase(text))
						favorC++;
						System.out.println(keyWord);
						
					}		
				}	
			}
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	/* public int countWord(String word, File file) {
		
		int count = 0;
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNextLine()) {
			
			String nextToken = scanner.next();
			if (nextToken.equalsIgnoreCase(word))
			count++;
		}
		
		return count;
	} */
	    
		Set<Feature> features = new HashSet<Feature>();
		
	    features.add(new Feature(FOR_IND, favorC));		      
	    //features.add(new Feature(AGAINST_IND, againstC));
	   //features.add(new Feature(NEUTRAL_IND, neutralC, FeatureType.NUMERIC));
	
	    return features;
	}	

}

