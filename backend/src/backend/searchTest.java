package backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class searchTest {
	public static void main(String[] args) {
		/*
		try {
			URL url = new URL("http://www.petmd.com/sites/default/files/kneecap-dislocation-dogs.jpg");
			Image image = ImageIO.read(url);
			if(image == null) {
				System.out.print("NULL");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		CollageBuilder cb = new CollageBuilder();
		BufferedImage image = cb.buildCollage("dog");
		File outputfile = new File("test1.jpg");
		try {
			ImageIO.write(image, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
