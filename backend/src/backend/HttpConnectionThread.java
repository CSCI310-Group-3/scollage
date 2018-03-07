package backend;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
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
		// loop through list of urls to make connections and get buffered image
		for(int i = 0; i < urls.size(); i++) {
			HttpURLConnection httpcon = null;
			try {
				// open http connection
				httpcon = (HttpURLConnection) urls.get(i).openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/64.0.3282 Safari/537.36");
				// timeout if conection takes longer than half a second
				httpcon.setConnectTimeout(500);
				System.out.println("Connection opened");
				// get the image and convert it to a buffered image
				BufferedImage img = ImageIO.read(httpcon.getInputStream());
				if(img == null) {
					System.out.println("NULL IMAGE");
				}
				else {
					// add it list of images
					images.add(img);
					System.out.println("ADDED IMAGE");
				}
			} catch(IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
		}
	}
}
