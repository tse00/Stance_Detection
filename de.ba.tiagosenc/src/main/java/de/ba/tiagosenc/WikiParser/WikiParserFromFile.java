package de.ba.tiagosenc.WikiParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.dkpro.tc.features.ngram.util.NGramUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;

public class WikiParserFromFile {
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException{
		
		String searchWord = "";

		String url = "https://es.wikipedia.org/wiki/";
		

		File againstPolitician = new File("src/main/resources/Lists/Only_Politicians_Against.txt");
		ArrayList<String> againstPoliticianL = new ArrayList<String>();

        for (String keyWord : FileUtils.readLines(againstPolitician, "UTF-8")) {
        	againstPoliticianL.add(keyWord.toLowerCase());
    		
//    		Document doc = Jsoup.connect(url + URLEncoder.encode(keyWord,"UTF-8")).get();

    		Document google = Jsoup.connect("https://es.wikipedia.org/wiki/" + URLEncoder.encode(keyWord,"UTF-8")).get();
        	
    		String wikiURL = google.getElementsByTag("cite").get(0).text();
    		System.out.println(wikiURL);
    		
//    		keyWord = wikiURL.replaceAll("https://es.wikipedia.org/wiki/", "");
    		
//        	System.out.println(keyWord);
//        	searchWord = keyWord;
        }
        

		System.out.println(searchWord);
		

/*
		Elements links = doc.select("p a[href^=\"/wiki/\"]").after("div#toc");
		
	    Element intro = doc.select("div#toc.toc").first();
		
		File listResults = new File ("src/main/resources/Expansion_list/ExpList.txt");
	
			
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
				
			for (Element link : links) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(link.text().length() > 2 && !(link.text().matches("(.*)(\\d+)(.*)"))) {
	
//					out.write(link.text());
//	                out.write(System.getProperty("line.separator"));
	//        		System.out.println("\nlink :" + link.attr("href"));
//	    			System.out.println("Text: " + link.text());
//			        link = link.nextElementSibling();

				}
			}
		}*/

	}
}
