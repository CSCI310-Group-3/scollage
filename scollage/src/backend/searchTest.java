package backend;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
		Collage collage = cb.buildCollage("cats");
		try {
			OutputStream out = new FileOutputStream("test.jpg");
			out.write(collage.getImage());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
