package mypackage;

import java.io.*;
import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.stream.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@WebServlet(urlPatterns = {"/new"})
public class CarRentalNew extends HttpServlet {

  

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String  model = req.getParameter("model_vehicle");
    String submodel = req.getParameter("sub_model_vehicle");
    String numvh = req.getParameter("dies_lloguer");
    String  numd = req.getParameter("num_vehicles");
    String des = req.getParameter("descompte");
	double aumento = 1;
    
    if (isNumeric(numd) == -2)  out.println("Los dias de alquiler no contienen un numero <br>");
	else if  (isNumeric(numd) == -1) out.println("Los dias de alquiler no pueden ser menor o igual a 1 <br>");
	if (isNumeric(numvh) == -2)  out.println("El numero de vehiculos no contiene un numero <br>");
	else if  (isNumeric(numvh) == -1) out.println("El numero de vehiculos no puede ser menor o igual a 1 <br>");
      switch (isPositive(des)) {
          case -2:
              out.println("El descuento no contiene un numero <br>");
              break;
          case -1:
              out.println("El descuento tiene que ser un numero igual o mayor que 0 <br>");
              break;
          default:
              if (submodel.equals("Diesel")) aumento = 1.4;
              break;
      }
      
        String aux = "";
        String cadena;
        BufferedReader b;
      try (FileReader f = new FileReader("C:\\WORKSPACE\\Universidad\\PTI\\P2\\Practica2PTI\\BBDD.json")) {
          b = new BufferedReader(f);
          aux = b.readLine();
      }
        b.close();
        JSONParser parser = new JSONParser(); 
        JSONObject arch;
        try {
         arch = (JSONObject) parser.parse(aux);
      } catch (ParseException ex) {
          arch = new JSONObject();
      }
        
        JSONObject obj = new JSONObject();
        obj.put("model",model);
        obj.put("submodel",submodel);
        obj.put("numvehicles",numvh);
        obj.put("numdies",numd);
        obj.put("descompte",des);
        JSONObject objprin = new JSONObject();
        objprin.put("rental",obj);
        out.println(arch.toJSONString());
        arch.put("listas", objprin);
        
        
       
     
        
      try (FileWriter file = new FileWriter("C:\\WORKSPACE\\Universidad\\PTI\\P2\\Practica2PTI\\BBDD.json")) {
          file.write(arch.toJSONString());
          file.flush();
          file.close();
      }

	
      
	
        
        
        
	
	out.println("<h2>Su peticion de alquiler ha sido aceptada</h2><br><hr><br>");
	out.println( "<h3>Factura simplificada:</h3>");
	out.println( "Precio del modelo= "+model+"<br>");
	out.println( "Supslemento por submodelo= "+aumento+"<br>");
	out.println( "Dias de alquiler= "+numd+"<br>");
	out.println( "Numero de vehiculos= "+ numvh+"<br>");
	out.println( "Descuento= "+des+"<br>");
	out.println( "<hr>");
	double total = (Integer.parseInt(model)*(aumento)*Integer.parseInt(numd)*Integer.parseInt(numvh))*((100-Double.parseDouble(des))/100);
	out.println( "<b>Total :</b>"+total+"<br><br>");
	out.println( "<a href='http://localhost/p1/carrental_form_new.html' > Volver </a>"	);
    
    
    
  }

  
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
  
   private static int isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
			if (Integer.parseInt(cadena) < 1) return -1;
            return 1;
        } catch (NumberFormatException nfe) {
            return -2;
        }
	}
  private static int isPositive(String cadena) {
	try {
      
			if (Double.parseDouble(cadena) < 0) return -1;
            return 1;
        } catch (NumberFormatException nfe) {
            return -2;
        }
	}

}


