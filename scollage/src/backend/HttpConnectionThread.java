package backend;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class HttpConnectionThread extends Thread{
	private List<URL> urls;
	private List<BufferedImage> images;
	
	public List<BufferedImage> getImages(){
		return images;
	}
	
	public HttpConnectionThread(List<URL> urls) {
		this.urls = urls;
		images = new ArrayList<BufferedImage>();
	}
	
	public void run() {
		/*System.out.println("Parsing json");
		// create json objects and parse out the image links
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("items");
		// get the link strings and add them to the list of images
		String link = "";
		if(jarray == null) {
			return;
		}
		for(int j = 0; j < jarray.size(); j++) {
			jobject = jarray.get(j).getAsJsonObject();
			link = jobject.get("link").getAsString();
			System.out.println(link);*/
		for(int i = 0; i < urls.size(); i++) {
			HttpURLConnection httpcon = null;
			try {
				httpcon = (HttpURLConnection) urls.get(i).openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/64.0.3282 Safari/537.36");
				httpcon.setConnectTimeout(500);
				System.out.println("Connection opened");
				BufferedImage img = ImageIO.read(httpcon.getInputStream());
				if(img == null) {
					System.out.println("NULL IMAGE");
				}
				else {
					images.add(img);
					System.out.println("ADDED IMAGE");
				}
			} catch(FileNotFoundException fnfe) {
				System.out.println("FILE NOT FOUND");
			} catch(SocketTimeoutException ste){
				System.out.println("CONNECTION TIMEOUT");
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}finally {
				if(httpcon != null) {
					httpcon.disconnect();
				}
			}
		}
	}
}
