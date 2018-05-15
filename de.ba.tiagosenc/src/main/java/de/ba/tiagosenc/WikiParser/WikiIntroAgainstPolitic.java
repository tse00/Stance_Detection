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

public class WikiIntroAgainstPolitic {
	
	public static void main(String[] args) throws IOException {
		
		String urlRajoy = "https://es.wikipedia.org/wiki/Mariano_Rajoy";
		String urlAlbiol = "https://es.wikipedia.org/wiki/Xavier_García_Albiol";
		String urlInes = "https://es.wikipedia.org/wiki/Inés_Arrimadas"; 
		String urlRivera = "https://es.wikipedia.org/wiki/Albert_Rivera"; 
		String urlIceta = "https://es.wikipedia.org/wiki/Miquel_Iceta"; 

		Document docRajoy = Jsoup.connect(urlRajoy).get();
		Document docAlbiol = Jsoup.connect(urlAlbiol).get();
		Document docInes = Jsoup.connect(urlInes).get();
		Document docRivera = Jsoup.connect(urlRivera).get();
		Document docIceta = Jsoup.connect(urlIceta).get();

		Elements linksRajoy = docRajoy.select("p a[href^=\"/wiki/\"]");
		Elements linksAlbiol = docAlbiol.select("p a[href^=\"/wiki/\"]");
		Elements linksInes = docInes.select("p a[href^=\"/wiki/\"]");
		Elements linksRivera = docRivera.select("p a[href^=\"/wiki/\"]");
		Elements linksIceta = docIceta.select("p a[href^=\"/wiki/\"]");

		
		File listResults = new File ("src/main/resources/ExpList_TEMP/ExpListIntro_AgainstPolitic.txt");

		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
			
			for (Element linkE : linksRajoy) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
					
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
				}
			}
			
			for (Element linkE : linksAlbiol) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));

				}
			}
			
			for (Element linkE : linksInes) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
					
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));

				}
			
			}

			for (Element linkE : linksRivera) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
					
				}
			
			}

			for (Element linkE : linksIceta) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					out.write(linkE.text());
	                out.write(System.getProperty("line.separator"));
				}		
			}
			
		}
	}

}
