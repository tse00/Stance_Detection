package de.ba.tiagosenc.WikiParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import info.bliki.api.query.Parse;


public class WikiAllFavorParty {

	
	public static void main(String[] args) throws IOException {
		
		String urlJxs = "https://es.wikipedia.org/wiki/Junts_pel_Sí";
		String urlCup = "https://es.wikipedia.org/wiki/Candidatura_de_Unidad_Popular";
		
		Document docJxs = Jsoup.connect(urlJxs).get();
		Document docCup = Jsoup.connect(urlCup).get();

		Elements linksJxs = docJxs.select("p a[href^=\"/wiki/\"]");
		Elements linksCup = docCup.select("p a[href^=\"/wiki/\"]"); 
		
		// Set ignore duplicates
		Set<String> linkSet = new HashSet<String>();
		
		
		File listResults = new File ("src/main/resources/Expanded Lists/ExpList_FavorParty.txt");
	
			
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
							
			for (Element linkE : linksJxs) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
	
					//Add to set to ignore duplicates
					linkSet.add(linkE.text().toLowerCase());

				}
			}

			
			for (Element linkE : linksCup) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
	
					//Add to set to ignore duplicates
					linkSet.add(linkE.text().toLowerCase());

				}
			}
			
			//add to File with break line
			for (String linkS : linkSet) {
				
				out.write(linkS);
                out.write(System.getProperty("line.separator"));
			}
			
		}

	}
}