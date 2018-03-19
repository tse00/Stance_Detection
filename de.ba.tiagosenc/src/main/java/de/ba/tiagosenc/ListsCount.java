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
	int favorC = 0;
	int againstC = 0;
	int neutralC = 0;
	
    public static final String FOR_IND = "ForIndList";
    public static final String AGAINST_IND = "AgainstIndList";
	public static final String NEUTRAL_IND = "NeutralIndList";
	

	public Set<Feature> extract(JCas jcas, TextClassificationTarget aTarget) throws TextClassificationException 
	{
			
		Set<String> stopwords = new HashSet<String>();		
		stopwords.add("yo");
		
		
		ArrayList<String> favorInd = new ArrayList<String>(); 
		
		//favorInd.add("cup");  -> redudant
		favorInd.add("arturo");
		favorInd.add("juntspelsi");
		favorInd.add("cupnacional");
		favorInd.add("somrierecup");
		favorInd.add("governemos");
		favorInd.add("libertad");
		favorInd.add("llieure");
		favorInd.add("independe");
				
		favorInd.add("Artur_Mas");
		favorInd.add("Visca_Catalunya");
		favorInd.add("fent_historia");
		favorInd.add("haciendo_historia");
		favorInd.add("som_la_historia");
		
		//favorInd.add("#Si");

		
		ArrayList<String> againstInd = new ArrayList<String>();
		
		againstInd.add("Rajoy");
		againstInd.add("Albiol");
		againstInd.add("inesarrimadas");
		//againstInd.add("arrimadas");  -> redudant with inesarrimadas
		againstInd.add("Rivera");
		againstInd.add("icelopeta");
		againstInd.add("miqueliceta");
		//againstInd.add("iceta");  -> redudant with miqueliceta
		againstInd.add("socialistas");
		//againstInd.add("pp");  -> redudant with ppopular
		againstInd.add("ppopular");
		againstInd.add("plantemoscara");
		againstInd.add("CiudadanosCs");
		againstInd.add("ApoderadosCs");
		//againstInd.add("Cs");      -> redudant 
		againstInd.add("Catsiqueespot");
		againstInd.add("PSC");
		againstInd.add("Socialismo");
		againstInd.add("PSC");	
		againstInd.add("unidos");
		againstInd.add("unida");
		//againstInd.add("unidad"); -> redudant with unida
		againstInd.add("indivisible");
		againstInd.add("juntsmillor");
		againstInd.add("caminemjunts");
		againstInd.add("llamadasqueunen");
		
		againstInd.add("Mariano_Rajoy");
		againstInd.add("Ines Arrimadas");
		againstInd.add("Albiol_XG");		// -> !!!!!!!!!!!
		againstInd.add("Albert_Rivera");
		againstInd.add("C's");
		againstInd.add("voto_naranja");
		againstInd.add("partidos_constitucionalistas");
		againstInd.add("catalunya_si_que_es_pot");
		againstInd.add("socialistes_cat");
		againstInd.add("viva_espana");
		againstInd.add("Cataluna_dentro_Espana");
		

		
		FrequencyDistribution<String> fd= NGramUtils.getDocumentNgrams(jcas, aTarget, true, false, 1, 2);

		for (String keyWord : favorInd) {

																									
				favorC += fd.getCount(keyWord.toLowerCase());

				//System.out.println(keyWord);
				//System.out.println("Count: " + favorC);
				
		}
		
		//System.out.println("\n" + "Total count favor: " + favorC);	

		for (String keyWord : againstInd) {

																									
				againstC += fd.getCount(keyWord.toLowerCase());

				//System.out.println(keyWord);
				//System.out.println("Count: " + againstC);			
		}	
		
		//System.out.println("\n" + "Total count against: " + againstC);	

		
		Set<Feature> features = new HashSet<Feature>();
		
        features.add(new Feature(FOR_IND, favorC));		      
        features.add(new Feature(AGAINST_IND, againstC));
        //features.add(new Feature(NEUTRAL_IND, neutralC));

        return features;
    }	
}                    