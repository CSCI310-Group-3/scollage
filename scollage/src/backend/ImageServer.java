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

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String querry = (String) request.getParameter("topic");

		CollageBuilder cb = new CollageBuilder();
		Collage collage = cb.buildCollage(querry);

		HttpSession session = request.getSession(true);

		byte[] encoded = Base64.getEncoder().encode(collage.getImage());
		String collageImageStr = new String(encoded);
		System.out.println(collageImageStr);

		session.setAttribute("collageImage", collageImageStr);
		session.setAttribute("collageTitle", collage.getTitle());
		session.setAttribute("collageValid", collage.getDisplay());
		RequestDispatcher dispatch = request.getRequestDispatcher("/displayPage.jsp");
		dispatch.forward(request, response);
	}

}
