package backendTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import backend.SearchThread;

public class SearchThreadTest {
	private String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyBGtIg8lDEoz1y9stGxIBgBu37eEWgRt4s&" 
			+ "cx=001699835611631837436:s1dpcehsldo&q=dog&searchType=image&fields=items(link)";
	@Test
	public void testGetURLs() throws IOException {
		URL url = new URL(this.url);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		SearchThread st = new SearchThread(c);
		assertEquals(st.getURLs().size(),0);
	}
	
	@Test
	public void testConstructor() throws IOException {
		URL url = new URL(this.url);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		SearchThread st = new SearchThread(c);
		assertNotNull(st.getURLs());
	}
	
	@Test
	public void testRun() throws IOException, InterruptedException {
		URL url = new URL(this.url);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		SearchThread st = new SearchThread(c);
		st.run();
		st.join();
		assertNotSame(st.getURLs().size(),0);
	}
	
	@Test
	public void testInvalidRun() throws MalformedURLException, IOException, InterruptedException{
		URL url = new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyBGtIg8lDEoz1y9stGxIBgBu37eEWgRt4s&" 
				+ "cx=001699835611631837436:s1dpcehsldo&q=dog cat&searchType=image&fields=items(link)");
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		SearchThread st = new SearchThread(c);
		st.run();
		st.join();
		assertEquals(st.getURLs().size(),0);
	}
}
