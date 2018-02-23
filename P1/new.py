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
	c = csv.writer(open("BBDD.csv", "a"))
	c.writerow([model,submodel,dieslloguer,numvehi,des])
	print "<h2>Su peticion de alquiler ha sido aceptada</h2><br><hr><br>"
	print "<h3>Factura:</h3>"
	print "Precio del modelo= "+model
"""Falta hacer factura"""




		


