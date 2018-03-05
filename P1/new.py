#! /usr/bin/python
""" Precio:
    economico = 54
	Semi-Luxe = 71
	Luxe = 82
	Limusina = 139
	Diesel precio x1
	Gasolina precio x1.2
	"""
# Llibreries
import cgi, os, re, sys, string, time, csv

def num(s):
	try:
		return int(s)
	except ValueError:
		return -1
def flot(s):
	try:
		return float(s)
	except ValueError:
		return -1
# Programa principal
print "Content-type: text/html\n\n"
print "<h1>Alquier coches</h1>"
	
form = cgi.FieldStorage()
model= form.getvalue("model_vehicle", "")
submodel = form.getvalue("sub_model_vehicle", "")
dieslloguer = form.getvalue("dies_lloguer", "")
numvehi = form.getvalue("num_vehicles", "")
des = form.getvalue("descompte", "")

dieslloguer = num(dieslloguer)
numvehi = num(numvehi)
des = flot(des)
if dieslloguer == -1:
	print "Los dias de alquiler no contienen un numero"
elif dieslloguer <= 0:
	print "Los dias de alquiler no pueden ser menor o igual a 0"
elif numvehi == -1:
	print "El numero de vehiculos no contiene un numero"
elif numvehi <= 0:
	print "El numero de vehiculos no puede ser menor o igual a 0"
elif des == -1:
	print "El descuento no contiene un numero"
elif des < 0:
	print "El descuento tiene que ser un numero igual o mayor que 0"
else:
	if submodel == "Diesel":
		aumento = 1.4
	else:
		aumento = 1
	c = csv.writer(open("BBDD.csv", "a"))
	c.writerow([model,submodel,dieslloguer,numvehi,des])
	print "<h2>Su peticion de alquiler ha sido aceptada</h2><br><hr><br>"
	print "<h3>Factura simplificada:</h3>"
	print "Precio del modelo= "+model+"<br>"
	print "Suplemento por submodelo= "+str(aumento)+"<br>"
	print "Dias de alquiler= "+ str(dieslloguer) +"<br>"
	print "Numero de vehiculos= "+ str(numvehi)+"<br>"
	print "Descuento= "+str(des)+"<br>"
	print "<hr>"
	total = (num(model)*flot(aumento)*num(dieslloguer)*num(numvehi))*((100-des)/100)
	print "<b>Total :</b>"+str(total)+"<br><br>"
print "<a href='http://localhost/p1/carrental_form_new.html' > Volver </a>"	
	




		


