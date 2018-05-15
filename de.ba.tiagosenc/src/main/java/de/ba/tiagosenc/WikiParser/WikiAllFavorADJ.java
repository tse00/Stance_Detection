package de.ba.tiagosenc.WikiParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiAllFavorADJ {

	
	public static void main(String[] args) throws IOException {
		
		String urlJxs = "https://es.wikipedia.org/wiki/Junts_pel_Sí";
		String urlCup = "https://es.wikipedia.org/wiki/Candidatura_de_Unidad_Popular";
		
		Document docJxs = Jsoup.connect(urlJxs).get();
		Document docCup = Jsoup.connect(urlCup).get();

		Elements linksJxs = docJxs.select("p a[href^=\"/wiki/\"]");
		Elements linksCup = docCup.select("p a[href^=\"/wiki/\"]"); 
		
		// Set ignore duplicates
		Set<String> linkSet = new HashSet<String>();
		
		
		File listResults = new File ("src/main/resources/ExpList_TEMP/ExpList_FavorParty.txt");
	
			
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
			
			System.out.println("LinkText: " + linkSet.toString().replace(",", "").replace("[", "").replace("]", "").trim() + "\n");
		}

	}
}
