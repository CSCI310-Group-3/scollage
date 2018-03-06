package backend;

import static org.junit.Assert.*;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HttpConnectionThreadTest{	
	@Test
	public void testGetImages() throws MalformedURLException {
		URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		assertTrue(conn.getImages().size() == 0);
	}
	
	@Test
	public void testConstructor() throws MalformedURLException {
		URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		assertTrue(conn.getImages() != null);
	}
	
	@Test
	public void testRun() throws MalformedURLException, InterruptedException {
		URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		conn.run();
		conn.join();
		assertTrue(conn.getImages().size() == 1 && conn.getImages().get(0) != null);
	}
}
