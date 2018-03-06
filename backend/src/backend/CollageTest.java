package backend;

import static org.junit.Assert.*;
import org.junit.Test;

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
		boolean valid = false;
		if(c.getTitle().equals("USC Fight On") && c.getDisplay() && c.getImage() == null) {
			valid = true;
		}
		assertTrue(valid);
	}
}
