package backend;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ImageServer")
public class ImageServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get user input from page
		String querry = (String) request.getParameter("topic");
		// construct collage based on querry
		CollageBuilder cb = new CollageBuilder();
		Collage collage = cb.buildCollage(querry);
		// create session
		HttpSession session = request.getSession(true);
		// convert byte array into base 64 string
		byte[] encoded = Base64.getEncoder().encode(collage.getImage());
		String collageImageStr = new String(encoded);
		// send tile, image, and valid boolean to displaypage
		session.setAttribute("collageImage", collageImageStr);
		session.setAttribute("collageTitle", collage.getTitle());
		session.setAttribute("collageValid", String.valueOf(collage.getDisplay()));
		RequestDispatcher dispatch = request.getRequestDispatcher("/displayPage.jsp");
		dispatch.forward(request, response);
	}

}
