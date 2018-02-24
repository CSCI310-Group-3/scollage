package backend;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    
	public CollageBuilder(string searchTerms) {
		
	}
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		return (collages.size() == 30);
	}
	
	//builds the collage
	public Collage buildCollage(String querry) {
		//get json from google
		List<BufferedImage> images = getImageResults(querry);
		//populate the collages list with 30 collage objects
		//apply rotations and sizing to all images in list
        for (int i=0; i<images.size; i++) {
            images[i] = resize(images[i], 65, 65);
            images[i] = addBorder(images[i], 5, 5);
            images[i] = rotate(images[i], generateRandomAngle);
        }
        BufferedImage collage = concatenation(images);
        
        return collage;
	}
    
    public static BufferedImage concatenation(List<BufferedImage> images) {
        BufferedImage resultCollage = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resultCollage.createGraphics();
        int count = 0;
        for (int i=0; i<=500; i += 100) {
            for (int j=0; j<=400; j+= 100) {
                g2d.drawImage(images.get(count++), i, j, null);
            }
        }
        g2d.dispose();
        return resultCollage;
    }
	
	public List<BufferedImage> getImageResults(String querry){
		String key = "%20AIzaSyBAcczm4OQMhZvTE5dIX8LeaKaYjcGt2aU";
		String id = "001699835611631837436:s1dpcehsldo";
		String searchTerms = querry;
		List<BufferedImage> imageResults = new ArrayList<BufferedImage>();
		List<BufferedReader> brArray = new ArrayList<BufferedReader>();
		List<String> jsonStrings = new ArrayList<String>();
		
		// loop 3 times, getting 10 search results each iteration
		try {
			// construct url for search api call
			// get first 10
			URL url1 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
				+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=1");
			// get next 10
			URL url2 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
					+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=11");
			// get final 10
			URL url3 = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
					+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=21");
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setRequestMethod("GET");
			conn1.setRequestProperty("Accept", "application/json");
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Accept", "application/json");
			HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
			conn3.setRequestMethod("GET");
			conn3.setRequestProperty("Accept", "application/json");
			// setup input reader
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
				for(int j = 0; j < 10; j++) {
					jobject = jarray.get(j).getAsJsonObject();
					link = jobject.get("link").getAsString();
					URL temp = new URL(link);
					imageResults.add(ImageIO.read(temp));
				}
			}
		} catch(MalformedURLException mue) {
			System.out.println("mue: " + mue.getMessage());
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		
		return imageResults;
	}
	
	//rotates a given image with an angle
	public static BufferedImage rotate(BufferedImage img, int angle) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
		BufferedImage rotatedImage = new BufferedImage(w, h, img.getType());  
        Graphics2D g = rotatedImage.createGraphics();  
        g.rotate(Math.toRadians(angle), w/2, h/2);  
        g.drawImage(img, null, 0, 0);  
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
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage newImage = new BufferedImage(w + padding, h + padding, img.getType());  
        Graphics2D g = newImage.createGraphics(); 
        g.setPaint(new Color(0,0,0,0));
        g.fillRect(0, 0, img.getHeight()+padding, img.getWidth()+padding);
        g.drawImage(img, img.getHeight(),img.getWidth(), null);
        g.dispose();  
        return newImage;  
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
