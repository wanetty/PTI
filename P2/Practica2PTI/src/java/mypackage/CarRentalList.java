package mypackage;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
@WebServlet(urlPatterns = {"/list"})
public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
   doPost(req,res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
        
      PrintWriter out = res.getWriter();
      String user = req.getParameter("userid");
      String pass = req.getParameter("password"); 
      
      if (user.equals("admin") && pass.equals("admin")) {
      
      JSONParser parser = new JSONParser();

        try {

           Object obj = parser.parse(new FileReader("C:\\WORKSPACE\\Universidad\\PTI\\P2\\Practica2PTI\\BBDD.json"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray rentals = (JSONArray) jsonObject.get("rentals");
            Iterator<String> iterator = rentals.iterator();
           
      
      
      res.setContentType("text/html");
      
      out.println( "<table>");
	out.println( "<tr>");
	out.println( "<th> Modelo </th>");
	out.println( "<th> Submodelo </th>");
	out.println( "<th> Dias de alquiler </th>");
	out.println( "<th> Numero de vehiculos </th>");
	out.println( "<th> Descuento </th>");
	out.println( "</tr>");
	
	
	  while (iterator.hasNext()){ 
		out.println( "<tr>");
		out.println( "<td>" + iterator.next() + "</td>");
		out.println( "<td>" + iterator.next() + "</td>");
		out.println( "<td>" + iterator.next() + "</td>");
		out.println( "<td>" + iterator.next() + "</td>");
		out.println( "<td>" + iterator.next() + "</td>");
		out.println( "</tr>");
          }
	out.println("</table>");

  }   catch (ParseException ex) {
          Logger.getLogger(CarRentalList.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
      else {
          out.println("User or password incorrect");
      }
      
  }
  
}
