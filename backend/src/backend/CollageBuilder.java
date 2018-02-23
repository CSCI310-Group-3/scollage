import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollageBuilder {
	
	private List<Collage> collages;
	
	public CollageBuilder() {
		collages = new ArrayList<Collage>();
		
	}
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		return (collages.size() == 30);
	}
	
	//builds the collage
	public void buildCollage() {
		//get json from google
		//populate the collages list with 30 collage objects
		//apply rotations and sizing to all images in list
	}
	
	
	//rotates a given image with an angle
	public BufferedImage rotate(BufferedImage img, int angle) {  
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
	public BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  
	
	
	
	
	
	
	
	
	
	
	

}
