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

public class WikiIntroFavorPolitic {
	
	public static void main(String[] args) throws IOException {
		
		
		String urlMas = "https://es.wikipedia.org/wiki/Artur_Mas";
			
		Document docMas = Jsoup.connect(urlMas).get();
	
		Elements linksMas = docMas.select("p a[href^=\"/wiki/\"]");


		File listResults = new File ("src/main/resources/ExpList_TEMP/ExpListIntro_FavorPolitic.txt");

		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
			
			for (Element linkE : linksMas) {
									
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));	
	                System.out.println(linkE.text());
	            }
			}			
		}
	}

}
