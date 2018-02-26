package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

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
    
}
