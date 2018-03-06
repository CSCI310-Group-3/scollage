package backend;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.Test;
import org.junit.Ignore;

public class CollageBuilderTest {

	@Test
	public void testValidCalculateSufficiecy() {
		CollageBuilder cb = new CollageBuilder();
        cb.buildCollage("dog");
        boolean valid = cb.calculateSufficiecy();
        assertTrue(valid);
	}
	
	@Test
	public void testInvalidCalculateSufficiecy() {
		CollageBuilder cb = new CollageBuilder();
        cb.buildCollage("asdfug3i17ga9we7fg3or899asdvuga7w3o8r7giuasef");
        boolean valid = cb.calculateSufficiecy();
        assertFalse(valid);
	}

	@Test
	public void testValidBuildCollage() {
		 CollageBuilder cb = new CollageBuilder();
		 Collage test = cb.buildCollage("dog");
		 boolean valid = true;
		 if(test == null) {
			 valid = false;
		 }
		 if(test.getDisplay() == false) {
			 valid = false;
		 }
		 assertTrue(valid);
	}
	
	public void testInvalidBuildCollage() {
		CollageBuilder cb = new CollageBuilder();
		Collage test = cb.buildCollage("asdfuig2ior79iu73t9aosdufoogaowiurg23f3ougaef2jkasdhfiu");
		boolean valid = true;
		if(test.getDisplay() == false || test.getImage().equals("")) {
			valid = false;
		}
		assertFalse(valid);
	}

	@Test
	public void testConcatenation() {
		CollageBuilder cb = new CollageBuilder();
		List<BufferedImage> images = cb.getImageResults("dog");
		BufferedImage collage = cb.concatenation(images);
		assertNotNull(collage);
	}

	@Test
	public void testValidGetImageResults() {
		CollageBuilder cb = new CollageBuilder();
		List<BufferedImage> test = cb.getImageResults("dog");
		assertTrue(test.size() == 30);
	}
	
	@Test
	public void testInvalidGetImageResults() {
		CollageBuilder cb = new CollageBuilder();
		List<BufferedImage> test = cb.getImageResults("asdfiug32r7o8agfo238rgalseiefuagsdbfjh3g2iryawie78fgsa");
		assertTrue(test.size() < 30);
	}

	@Test
	public void testRotate() {
		CollageBuilder cb = new CollageBuilder();
		BufferedImage testImg = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		BufferedImage testImg2 = cb.rotate(testImg, 10);
		assertNotSame(testImg, testImg2);
	}

	@Test
	public void testGenerateRandomAngle() {
		boolean valid = true;
		CollageBuilder cb = new CollageBuilder();
		int randomAngle = 0;
		for(int i = 0; i < 100; i++) {
			randomAngle = cb.generateRandomAngle();
			if(randomAngle > 45 || randomAngle < -45) {
				valid = false;
			}
		}
		assertTrue(valid);
	}

	@Test
	public void testResize() {
		BufferedImage img = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		CollageBuilder cb = new CollageBuilder();
		BufferedImage resizedImg = cb.resize(img, 100, 100);
		assertTrue(resizedImg.getWidth() == 100 && resizedImg.getHeight() == 100);
	}

	@Test
	public void testAddBorder() {
		CollageBuilder cb = new CollageBuilder();
		BufferedImage testImg = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		BufferedImage testImg2 = cb.addBorder(testImg, 5);
		assertNotSame(testImg, testImg2);
	}

}
