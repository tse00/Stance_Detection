package de.ba.tiagosenc.WikiParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WikiParserTest {
	
	public static void main(String[] args){
		
		String encoding = "UTF-8";
		
		try {
		String searchText = "Mariano Rajoy";
			
		searchText += "Wikipedia";
			
		Document google = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(searchText, encoding)).get();
		
		String wikiURL = google.getElementsByTag("cite").get(0).text();
		System.out.println(wikiURL);
		
		String wikiApiJSON = wikiURL.replaceAll("https://es.wikipedia.org/wiki/", "");
		
		System.out.println(wikiApiJSON);
		

		//"extract"
		HttpURLConnection httpcon = (HttpURLConnection) new URL(wikiApiJSON).openConnection();
//		httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
		
		//Reader line by line
		String responseSB = in.lines().collect(Collectors.joining());
		in.close();
		
		String result = responseSB.split("\"extract\":\"")[1];
		System.out.println(result);
		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
