package backendTests;

import static org.junit.Assert.*;
import org.junit.Test;

import backend.HttpConnectionThread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpConnectionThreadTest{	
	@Test
	public void testGetImages() throws MalformedURLException {
		URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		assertEquals(conn.getImages().size(),0);
	}
	
	@Test
	public void testConstructor() throws MalformedURLException {
		URL url = new URL("https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		assertNotNull(conn.getImages());
	}
	
	@Test
	public void testRun() throws MalformedURLException, InterruptedException {
		URL url = new URL("https://wallpapersite.com/images/wallpapers/canine-dog-4287x2858-puppy-4k-4953.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		conn.run();
		conn.join();
		assertEquals(conn.getImages().size(), 1);
	}
	
	@Test
	public void testInvalidRun() throws MalformedURLException, InterruptedException {
		URL url = new URL("https://dog.jpg");
		List<URL> urls = new ArrayList<URL>();
		urls.add(url);
		HttpConnectionThread conn = new HttpConnectionThread(urls);
		conn.run();
		conn.join();
		assertEquals(conn.getImages().size(), 0);
	}
}
