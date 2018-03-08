package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchThread extends Thread{
	private HttpURLConnection connection;
	private BufferedReader br;
	private String json;
	private List<URL> urls;
	
	public List<URL> getURLs(){
		return urls;
	}
	
	public SearchThread(HttpURLConnection c) {
		connection = c;
		json = "";
		urls = new ArrayList<URL>();
	}

	public void run() {
		// GET RESPONSE FROM GOOGLE API
		try {
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output = "";
			while((output = br.readLine()) != null) {
				json += output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOEXCEPTION IN SEARCH THREAD");
			return;
		}
		System.out.println("Created new json: " + json);
		
		// PARSE JSON
		System.out.println("Parsing json");
		// create json objects and parse out the image links
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("items");
		// get the link strings and add them to the list of images
		String link = "";
		if(jarray == null) {
			return;
		}
		// add links as urls to list
		for(int j = 0; j < jarray.size(); j++) {
			jobject = jarray.get(j).getAsJsonObject();
			link = jobject.get("link").getAsString();
			try {
				urls.add(new URL(link));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
