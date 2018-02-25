package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ImageServer")
public class ImageServer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getQueryString();
		
	CollageBuilder cb = new CollageBuilder();
	BufferedImage collage = cb.buildCollage(searchTerm);
		
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write(collage, "jpg", baos);
	byte[] bytes = baos.toByteArray();
    }
    
    private void makeCollage () {

    }
    
}
