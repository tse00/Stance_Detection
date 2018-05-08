package de.ba.tiagosenc.WikiParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import info.bliki.api.query.Parse;


public class WikiParserAll {

	
	public static void main(String[] args) throws IOException {
		
		
		String url = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
		
		Document doc = Jsoup.connect(url).get();

		Elements links = doc.select("p a[href^=\"/wiki/\"]");
		
//	    Element intro = doc.select("div#toc.toc").first();
		
		File listResults = new File ("src/main/resources/Expansion_list/ExpList.txt");
	
			
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
				
			for (Element link : links) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(link.text().length() > 2 && !(link.text().matches("(.*)(\\d+)(.*)"))) {
	
					out.write(link.text());
	                out.write(System.getProperty("line.separator"));
	//        		System.out.println("\nlink :" + link.attr("href"));
	    			System.out.println("Text: " + link.text());
//			        link = link.nextElementSibling();

				}
			}
		}
	}
}