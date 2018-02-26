package backend;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class Collage {
	
	private String title;
	private byte[] image;
	private Boolean display; //obtained from the url passed in constructor
	
	public Collage(String title, byte[] bytes, Boolean display) {
		this.title = title;
		this.image = bytes;
		this.display = display;
		
		//ImageIcon icon = new ImageIcon(imageURL);
		//this.image = (BufferedImage) icon.getImage();
	}


	public String getTitle() {
		return title;
	}


	public Boolean getDisplay() {
		return display;
	}


	public byte[] getImage() {
		return image;
	}

}
