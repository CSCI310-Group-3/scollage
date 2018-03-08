package backend;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
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
    private boolean validCollage;
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		// TODO
		return validCollage;
	}
	
	//builds the collage
	public Collage buildCollage(String querry) {
		//get json from google
		List<BufferedImage> images = getImageResults(querry);
		// set valid collage based on number of imgaes found
		if(images.size() < 30) {
			validCollage = false;
		} else {
			validCollage = true;
		}
		//populate the collages list with 30 collage objects
		//apply rotations and sizing to all images in list
        for (int i=0; i < images.size(); i++) {
        	System.out.println(i);
            images.set(i, resize(images.get(i), 190, 175));
            images.set(i, addBorder(images.get(i), 3));
            images.set(i, rotate(images.get(i), generateRandomAngle()));
        }
        // compile all images into 1 image
        BufferedImage bufferedCollage = concatenation(images);
        // convert buffered image into byte array
        ByteArrayOutputStream baos;
        // construct collage to return
        Collage collage = null;
        try {
        	System.out.println("Throw Exception");
			baos = convertToBytes(bufferedCollage);
			System.out.println("asdf");
	        byte[] bytes = baos.toByteArray();
	        collage = new Collage(querry,bytes,calculateSufficiecy());
		} catch (IOException e) {
			System.out.println("IOEXCEPTION WITH IMAGEIO.WRITE()");
		}
        return collage;
	}
	
	public ByteArrayOutputStream convertToBytes(BufferedImage img) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		return baos;
	}
    
    public static BufferedImage concatenation(List<BufferedImage> images) {
    	// create collage image
        BufferedImage resultCollage = new BufferedImage(750, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resultCollage.createGraphics();
        // set starting indexes
        int x = -59;
        int y = -63;
        // loop through each image and add it to collage, updating x and y every iterations
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
		// initial variables
		String key = "AIzaSyBGtIg8lDEoz1y9stGxIBgBu37eEWgRt4s";
		String id = "001699835611631837436:s1dpcehsldo";
		String searchTerms = querry.replace(' ', '+');
		List<BufferedImage> imageResults = new ArrayList<BufferedImage>();
		List<URL> resultLinks = new ArrayList<URL>();
		List<HttpURLConnection> searchConnections = new ArrayList<HttpURLConnection>();
		try {
			// construct url for search api call
			// each call gets 10 results, so make 3 calls
			for(int i = 0; i < 3; i++) {
				URL url = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
				+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=" + (i*10 + 1) + "&fields=items(link)");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				searchConnections.add(conn);
			}
			System.out.println("OPENED CONNECTIONS");
			// setup input readers, have each connection open on a seperate thread
			SearchThread[] searches = new SearchThread[3];
			for(int i = 0; i < 3; i++) {
				searches[i] = new SearchThread(searchConnections.get(i));
				searches[i].start();
			}
			// wait for each thread to finish before continueing
			for(int i = 0; i < 3; i++) {
				try {
					searches[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// add the image url results from each thread into 1 list
			for(int i = 0; i < 3; i++) {
				resultLinks.addAll(searches[i].getURLs());
			}
			
			// setup up 10 threads to process 3 images each
			int httpThreadCount = 10;
			int subIndex = 30 / httpThreadCount;
			System.out.println(resultLinks.size());
			// if not enough image urls, just return a blank list
			if(resultLinks.size() < 30) {
				return new ArrayList<BufferedImage>();
			}
			HttpConnectionThread[] connections = new HttpConnectionThread[httpThreadCount];
			// start each thread
			for(int i = 0; i < httpThreadCount; i++) {
				connections[i] = new HttpConnectionThread(resultLinks.subList(i * subIndex, i * subIndex + subIndex));
				connections[i].start();
			}
			// wait for each thread to finish
			for(int i = 0; i < httpThreadCount; i++) {
				try {
					connections[i].join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			// combine the reultings buffered images from each thread into 1 list
			for(int i = 0; i < httpThreadCount; i++) {
				imageResults.addAll(connections[i].getImages());
			}
			// if not enough valid images due to 403 errors, do another iteration of the process
			URL fillUrl = new URL("https://www.googleapis.com/customsearch/v1?key=" + key 
					+ "&cx=" + id + "&q=" + searchTerms + "&searchType=image" + "&start=31&fields=items(link)");
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
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 Chrome/64.0.3282 Safari/537.36");
				httpcon.setConnectTimeout(500);
				try {
					BufferedImage img = ImageIO.read(httpcon.getInputStream());
					if(imageResults.size() == 30) {
						break;
					}
					imageResults.add(img);
				} catch(FileNotFoundException fnfe) {
					
				}
			}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return imageResults;
	}
	
	//rotates a given image with an angle
	public static BufferedImage rotate(BufferedImage img, int angle) { 
		// initialize variables
		double rads = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();  
        int h = img.getHeight();
        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);
        
        // create new image
		BufferedImage rotatedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g = rotatedImage.createGraphics(); 
        // rotate image
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
        int w = img.getWidth();  
        int h = img.getHeight();  
        // create target image
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics(); 
        // redraw the image to the desired size
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  
	
    //add border
	public static BufferedImage addBorder(BufferedImage img, int padding) {
		// initialize variables
		int w = img.getWidth();
		int h = img.getHeight();
		// draw a new image
		BufferedImage frame = new BufferedImage(w + 2 * padding, h + 2 * padding, img.getType());
		Graphics2D graph = frame.createGraphics();
		graph.setColor(Color.WHITE);
		// draw rectangle with padding
		graph.fill(new Rectangle(0, 0, w + 2 * padding, h + 2 * padding));
		// update image
		graph.drawImage(img, padding, padding, null);
		graph.dispose();
		return frame;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
