package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CarRentalNew extends HttpServlet {

  

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
	if (isPositive(des) == -2)  out.println("El descuento no contiene un numero <br>");
	else if  (isPositive(des) == -1) out.println("El descuento tiene que ser un numero igual o mayor que 0 <br>");

	else {
		if (submodel.equals("Diesel")) aumento = 1.4;

	}
	

	
	out.println("<h2>Su peticion de alquiler ha sido aceptada</h2><br><hr><br>");
	out.println( "<h3>Factura simplificada:</h3>");
	out.println( "Precio del modelo= "+model+"<br>");
	out.println( "Suplemento por submodelo= "+aumento+"<br>");
	out.println( "Dias de alquiler= "+numd+"<br>");
	out.println( "Numero de vehiculos= "+ numvh+"<br>");
	out.println( "Descuento= "+des+"<br>");
	out.println( "<hr>");
	double total = (Integer.parseInt(model)*(aumento)*Integer.parseInt(numd)*Integer.parseInt(numvh))*((100-Double.parseDouble(des))/100);
	out.println( "<b>Total :</b>"+total+"<br><br>");
	out.println( "<a href='http://localhost/p1/carrental_form_new.html' > Volver </a>"	);
    
    
    
  }

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


