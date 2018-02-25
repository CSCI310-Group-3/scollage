package backend;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CollageBuilder {
    
	public CollageBuilder() {
		
	}
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		// TODO
		return true;
	}
	
	//builds the collage
	public BufferedImage buildCollage(String querry) {
		//get json from google
		List<BufferedImage> images = getImageResults(querry);
		//populate the collages list with 30 collage objects
		//apply rotations and sizing to all images in list
        for (int i=0; i < images.size(); i++) {
        	System.out.println(i);
            images.set(i, resize(images.get(i), 180, 170));
            images.set(i, addBorder(images.get(i), 3));
            images.set(i, rotate(images.get(i), generateRandomAngle()));
        }
        BufferedImage collage = concatenation(images);
        
        return collage;
	}
    
    public static BufferedImage concatenation(List<BufferedImage> images) {
        BufferedImage resultCollage = new BufferedImage(750, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resultCollage.createGraphics();
        //int count = 0;
        //for (int i=0; i<=500; i += 100) {
        //    for (int j=0; j<=400; j+= 100) {
        //        g2d.drawImage(images.get(count++), i, j, null);
        //    }
        //}
        int x = -57;
        int y = -60;
        for(int i = 0; i < images.size(); i++) {
        	g2d.drawImage(images.get(i),null,x,y);
        	x += 125;
        	if(x > 568) {
        		y += 125;
        		x = -57;
        	}
        }
        g2d.dispose();
        return resultCollage;
    }
	
	public List<BufferedImage> getImageResults(String querry){
		String key = "AIzaSyBGtIg8lDEoz1y9stGxIBgBu37eEWgRt4s";
		String id = "001699835611631837436:s1dpcehsldo";
		String searchTerms = querry;
		List<BufferedImage> imageResults = new ArrayList<BufferedImage>();
		List<BufferedReader> brArray = new ArrayList<BufferedReader>();
		List<String> jsonStrings = new ArrayList<String>();
		
		try {
			// construct url for search api call
			// get first 10
			URL url1 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
				+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=1&fileType=jpg");
			// get next 10
			URL url2 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
					+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=11&fileType=jpg");
			// get final 10
			URL url3 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
					+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=21&fileType=jpg");
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setRequestMethod("GET");
			conn1.setRequestProperty("Accept", "application/json");
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Accept", "application/json");
			HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
			conn3.setRequestMethod("GET");
			conn3.setRequestProperty("Accept", "application/json");
			// setup input readers
			brArray.add(new BufferedReader(new InputStreamReader(conn1.getInputStream())));
			brArray.add(new BufferedReader(new InputStreamReader(conn2.getInputStream())));
			brArray.add(new BufferedReader(new InputStreamReader(conn3.getInputStream())));
			// loop through each connection/br and get json strings
			for(int i = 0; i < 3; i++) {
				// construct json string
				String output = "";
				String json = "";
				while((output = brArray.get(i).readLine()) != null) {
					json += output;
				}
				jsonStrings.add(json);
			}
			// parse out each json string
			for(int i = 0; i < 3; i++) {
				// create json objects and parse out the image links
				JsonElement jelement = new JsonParser().parse(jsonStrings.get(i));
				JsonObject jobject = jelement.getAsJsonObject();
				JsonArray jarray = jobject.getAsJsonArray("items");
				// get the link strings and add them to the list of images
				String link = "";
				for(int j = 0; j < jarray.size(); j++) {
					jobject = jarray.get(j).getAsJsonObject();
					link = jobject.get("link").getAsString();
					System.out.println(link);
					URL temp = new URL(link);
					HttpURLConnection httpcon = (HttpURLConnection) temp.openConnection();
					httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
					BufferedImage img = ImageIO.read(httpcon.getInputStream());
					if(img == null) {
						System.out.println("ADDING NULL IMAGE");
					}
					else {
						imageResults.add(img);
					}
				}
			}
			
			if(imageResults.size() < 30) {
				URL fillUrl = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
						+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=31&fileType=jpg");
				HttpURLConnection conn = (HttpURLConnection) fillUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String output = "";
				String json = "";
				while((output = br.readLine()) != null) {
					json += output;
				}
				JsonElement jelement = new JsonParser().parse(json);
				JsonObject jobject = jelement.getAsJsonObject();
				JsonArray jarray = jobject.getAsJsonArray("items");
				// get the link strings and add them to the list of images
				String link = "";
				for(int j = 0; j < jarray.size(); j++) {
					jobject = jarray.get(j).getAsJsonObject();
					link = jobject.get("link").getAsString();
					System.out.println(link);
					URL temp = new URL(link);
					HttpURLConnection httpcon = (HttpURLConnection) temp.openConnection();
					httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
					BufferedImage img = ImageIO.read(httpcon.getInputStream());
					if(img == null) {
						System.out.println("ADDING NULL IMAGE");
					}
					else {
						imageResults.add(img);
						if(imageResults.size() == 30) {
							break;
						}
					}
				}
				
			}
		} catch(MalformedURLException mue) {
			mue.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return imageResults;
	}
	
	//rotates a given image with an angle
	public static BufferedImage rotate(BufferedImage img, int angle) { 
		double rads = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();  
        int h = img.getHeight();
        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);
        
		BufferedImage rotatedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g = rotatedImage.createGraphics(); 
        AffineTransform transform = new AffineTransform();
        transform.translate((newW - w) / 2, (newH - h) / 2);
        
        int x = w / 2;
        int y = h / 2;
        
        transform.rotate(rads,x,y);
        g.setTransform(transform);
        g.drawImage(img, 0, 0, null);
       // g.setColor(Color.WHITE);
        //g.drawRect(0, 0, newW - 1, newH - 1);
        g.dispose();
        return rotatedImage; 
    }
	
	//generates a random angle
	public int generateRandomAngle() {
		Random rand = new Random();
		int  n = rand.nextInt(91) - 45; //generates a random number 0-90, and scales it to be from -45 to 45
		return n;
	}
	
	//takes in an image, and the new sizing requirements
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
		if(img == null) {
			System.out.println("NULL IMAGE");
		}
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  
	
    //add border
	public static BufferedImage addBorder(BufferedImage img, int padding) {  
        /*int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage newImage = new BufferedImage(w + padding, h + padding, img.getType());  
        Graphics2D g = newImage.createGraphics(); 
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, img.getHeight()+padding, img.getWidth()+padding);
        g.drawImage(img, img.getHeight(),img.getWidth(), null);
        g.dispose();  
        return newImage;*/
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage frame = new BufferedImage(w + 2 * padding, h + 2 * padding, img.getType());
		Graphics2D graph = frame.createGraphics();
		graph.setColor(Color.WHITE);
		graph.fill(new Rectangle(0, 0, w + 2 * padding, h + 2 * padding));
		graph.drawImage(img, padding, padding, null);
		graph.dispose();
		return frame;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
