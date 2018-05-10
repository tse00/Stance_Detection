package de.ba.tiagosenc.Features;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.features.FeatureExtractor;
import org.dkpro.tc.api.features.FeatureExtractorResource_ImplBase;
import org.dkpro.tc.api.type.TextClassificationTarget;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class ListsCount3 
	extends FeatureExtractorResource_ImplBase
	implements FeatureExtractor
	
{
	
	int count = 0;
	public static final String COUNT = "countWords";
	List<String> listW = new ArrayList<String>();

	@Override
	public Set<Feature> extract(JCas jcas, TextClassificationTarget target) throws TextClassificationException {

	  listW.add("jxs");
	  listW.add("unidad");
	  listW.add("juntsmillor");

		
		
		for (Token tk : JCasUtil.selectCovered(jcas, Token.class, target)) {
	       	
	        String text = tk.getCoveredText().toLowerCase();
	        
			for (String c : listW) {
				
				if((text).contains(c.toLowerCase())) { 
					
			    	count += c.split("[A-Za-z0-9_áéíóúüçñ]").length;
			    	break;	
				}
			}
		}
		
		Set<Feature> features = new HashSet<Feature>();	
	    features.add(new Feature(COUNT, count));		      
	
	    return features;
	}

}
