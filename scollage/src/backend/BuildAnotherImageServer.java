package backend;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class BuildAnotherImageServer
 */
@WebServlet("/BuildAnotherImageServer")
public class BuildAnotherImageServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String querry = (String) request.getParameter("topic");

		CollageBuilder cb = new CollageBuilder();
		Collage collage = cb.buildCollage(querry);

		byte[] encoded = Base64.getEncoder().encode(collage.getImage());
		String collageImageStr = new String(encoded);
		String collageTitle = collage.getTitle();

		Map<String, String> options = new LinkedHashMap<>();
		options.put("collageTitle", collageTitle);
		options.put("collageImage", collageImageStr);
		options.put("collageValid", String.valueOf(collage.getDisplay()));
		String json = new Gson().toJson(options);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}
