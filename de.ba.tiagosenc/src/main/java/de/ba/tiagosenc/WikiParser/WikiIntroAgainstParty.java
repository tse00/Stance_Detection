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

public class WikiIntroAgainstParty {
	
	public static void main(String[] args) throws IOException {
		
		String urlPP = "https://es.wikipedia.org/wiki/Partido_Popular";
		String urlCs = "https://es.wikipedia.org/wiki/Ciudadanos_(España)";
		String urlPSC = "https://es.wikipedia.org/wiki/Partido_de_los_Socialistas_de_Cataluña"; 
				
		Document docPP = Jsoup.connect(urlPP).get();
		Document docCs = Jsoup.connect(urlCs).get();
		Document docPSC = Jsoup.connect(urlPSC).get();
		
		Elements linksPP = docPP.select("p a[href^=\"/wiki/\"]");
		Elements linksCs = docCs.select("p a[href^=\"/wiki/\"]");
		Elements linksPSC = docPSC.select("p a[href^=\"/wiki/\"]");

		
		File listResults = new File ("src/main/resources/ExpList_TEMP/ExpListIntro_AgainstParty.txt");

		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
			
			for (Element linkE : linksPP) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
				}
			}
			
			for (Element linkE : linksCs) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
					
				}
			}
			
			for (Element linkE : linksPSC) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
				}
			}

		}
	}

}
