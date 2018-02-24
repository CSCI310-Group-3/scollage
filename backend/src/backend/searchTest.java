package backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class searchTest {
	public static void main(String[] args) {
		CollageBuilder cb = new CollageBuilder();
		BufferedImage image = cb.buildCollage("dogs");
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
