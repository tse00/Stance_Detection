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

public class WikiGeneralLexica {
	
	public static void main(String[] args) throws IOException {
		
		String url1 = "https://es.wikipedia.org/wiki/Independentismo_catalán";
		String url2 = "https://es.wikipedia.org/wiki/Proceso_soberanista_de_Cataluña_de_2012-2018#Elecciones_«plebiscitarias»_del_27_de_septiembre_de_2015";
		String url3 = "https://es.wikipedia.org/wiki/Historia_del_independentismo_catalán"; 
		String url4 = "https://es.wikipedia.org/wiki/Elecciones_al_Parlamento_de_Cataluña_de_2015"; 

		Document doc1 = Jsoup.connect(url1).get();
		Document doc2 = Jsoup.connect(url2).get();
		Document doc3 = Jsoup.connect(url3).get();
		Document doc4 = Jsoup.connect(url4).get();

		Elements links1 = doc1.select("p a[href^=\"/wiki/\"]");
		Elements links2 = doc2.select("p a[href^=\"/wiki/\"]");
		Elements links3 = doc3.select("p a[href^=\"/wiki/\"]");
		Elements links4 = doc4.select("p a[href^=\"/wiki/\"]");

		// Set ignore duplicates
		Set<String> linkSet = new HashSet<String>();
		
		
		File listResults = new File ("src/main/resources/ExpList_TEMP/ExpList_GeneralLexica.txt");

		
		try (BufferedWriter out = new BufferedWriter(new FileWriter(listResults))) {
			
			for (Element linkE : links1) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					//Add to set to ignore duplicates
					linkSet.add(linkE.text().toLowerCase());
				}
			}
			
			for (Element linkE : links2) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					//Add to set to ignore duplicates
					linkSet.add(linkE.text().toLowerCase());
				}
			}
			
			for (Element linkE : links3) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
					//Add to set to ignore duplicates
					linkSet.add(linkE.text().toLowerCase());
				}
			
			}

			for (Element linkE : links4) {
					
				
//				if(link.text().length() > 2 && !(link.text().matches("([^A-Za-záéíóúüçñ]+)"))) {
				if(linkE.text().length() > 2 && !(linkE.text().matches("(.*)(\\d+)(.*)"))) {
			
//					Add to set to ignore duplicates
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
