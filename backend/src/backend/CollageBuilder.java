import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollageBuilder {
	
	private List<Collage> collages;
	
	public CollageBuilder() {
		collages = new ArrayList<Collage>(30);
		
	}
	
	//returns true/false whether the collage list has 30 images or not
	public boolean calculateSufficiecy() {
		return (collages.size() >= 30);
	}
	
	//builds the collage
	public void buildCollage() {
		
	}
	
	
	//rotates a given image with an angle
	public static BufferedImage rotate(BufferedImage img, int angle) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
		BufferedImage dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.rotate(Math.toRadians(angle), w/2, h/2);  
        g.drawImage(img, null, 0, 0);  
        return dimg;  
    }  
	
	//generates a random angle
	private int generateRandomAngle() {
		Random rand = new Random();
		int  n = rand.nextInt(91) - 45; //generates a random number 0-90, and scales it to be from -45 to 45
		
		return n;
	}
	
	
	
	
	
	
	
	

}
