package de.ba.tiagosenc.WikiParser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WikiParserIntro {
	
	public static void main(String[] args) throws IOException{
		

	
	String url = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
	
	Document doc = Jsoup.connect(url).get();

    Element intro = doc.select("p").first();
    	
	    while (intro.tagName().equals("p")) {
	        System.out.println(intro.select("a").text());
	        intro = intro.nextElementSibling();
		
	    }
    
	}
		    
}
