package backendTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import backend.BuildAnotherImageServer;
import backend.ImageServer;

public class ServletsTest {
	@Test
	public void testImageServer() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.addParameter("topic", "dog");
		
		new ImageServer().service(request,response);
		assertNull(response.getContentType());
	}
	
	@Test
	public void testBuildAnotherImageServer() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.addParameter("topic", "dog");
		
		new BuildAnotherImageServer().service(request,response);
		
		assertEquals("application/json",response.getContentType());
	}
}