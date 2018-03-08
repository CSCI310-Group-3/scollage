package backendTests;

import static org.junit.Assert.*;
import org.junit.Test;

import backend.Collage;

public class CollageTest {
	@Test
	public void testGetTitle() {
		Collage c = new Collage("USC Fight On", null, true);
		assertEquals(c.getTitle(),"USC Fight On");
	}
	
	@Test
	public void testGetDisplay() {
		Collage c = new Collage("USC Fight On", null, true);
		assertTrue(c.getDisplay());
	}
	
	@Test
	public void testGetImage() {
		Collage c = new Collage("USC Fight On", null, true);
		assertNull(c.getImage());
	}
	
	@Test
	public void testCollageConstructor() {
		Collage c = new Collage("USC Fight On", null, true);
		assertTrue(c.getTitle().equals("USC Fight On"));
	}
}
