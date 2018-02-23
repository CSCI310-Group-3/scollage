import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class Collage {
	
	private String title;
	private URL imageURL;
	private Boolean display;
	private BufferedImage image; //obtained from the url passed in constructor
	
	public Collage(String title, URL imageURL, Boolean display) {
		this.title = title;
		this.imageURL = imageURL;
		this.display = display;
		
		ImageIcon icon = new ImageIcon(imageURL);
		this.image = (BufferedImage) icon.getImage();
	}


	public String getTitle() {
		return title;
	}


	public URL getImageURL() {
		return imageURL;
	}


	public Boolean getDisplay() {
		return display;
	}


	public Image getImage() {
		return image;
	}

}
