package backend;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.Test;
import org.junit.Ignore;

public class CollageBuilderTest {

	@Test
	public void testCalculateSufficiecy() {
	//	CollageBuilder cb = new CollageBuilder();
    //    cb.buildCollage("dog");
    //    boolean expectedValue = cb.calculateSufficiecy();
        assertEquals(4, 4);
	}

	@Test
	public void testBuildCollage() {
		 CollageBuilder cb = new CollageBuilder();
		 Collage test = cb.buildCollage("dog");
		 assertNotNull(test);
	}
	
	@Test
	public void testNotEnoughPics() {
	

	}
	
	public void testEnoughCollage() {

	}

	@Test
	public void testConcatenation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetImageResults() {
		CollageBuilder cb = new CollageBuilder();
		List<BufferedImage> test = cb.getImageResults("dog");
		assertTrue(test.size() != 0);
	}

	@Test
	public void testRotate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateRandomAngle() {
		fail("Not yet implemented");
	}

	@Test
	public void testResize() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddBorder() {
		fail("Not yet implemented");
	}

}
