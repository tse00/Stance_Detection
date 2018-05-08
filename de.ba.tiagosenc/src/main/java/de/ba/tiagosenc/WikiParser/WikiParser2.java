package de.ba.tiagosenc.WikiParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiParser2 {
	
	public static void main(String[] args) throws IOException{
		
		String url = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
		
		Document doc = Jsoup.connect(url).get();
	
	    Element intro = doc.select("p").first();
	    	    	
		    while (intro.tagName().equals("p")) {

		        System.out.println(intro.text());

		        intro = intro.nextElementSibling();
			
		    }

	}
		    
/*			String url = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
			
			Document doc = Jsoup.connect(url).get();
		
		    Element intro = doc.select("p").first();
		    	
			    while (intro.tagName().equals("p")) {

			        System.out.println(intro.select("a").text());
			        intro = intro.nextElementSibling();
				
			    }*/
		    

/*		String url = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
		
		Document doc = Jsoup.connect(url).get();
		String title = doc.html();
		System.out.println("Title: " + title);
		
		Elements links = doc.select("p a[href^=\"/wiki/\"]");
		
		for (Element link : links) {
			
			System.out.println("\nlink :" + link.attr("href"));
			System.out.println("Text: " + link.text());
			
		}*/
}
