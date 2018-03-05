#! /usr/bin/python

# Llibreries
import cgi, os, re, sys, string, time, csv
print "Content-type: text/html\n\n"

form = cgi.FieldStorage()
required_fields = ('userid','password')
if form['password'].value == "admin" and form['userid'].value == "admin":
	
	cr = csv.reader(open("BBDD.csv","rb"))
	print "<table>"
	print "<tr>"
	print "<th> Modelo </th>"
	print "<th> Submodelo </th>"
	print "<th> Dias de alquiler </th>"
	print "<th> Numero de vehiculos </th>"
	print "<th> Descuento </th>"
	print "</tr>"
	
	
	for row in cr:    
		print "<tr>"
		print "<td>" + row[0] + "</td>"
		print "<td>" + row[1] + "</td>"
		print "<td>" + row[2] + "</td>"
		print "<td>" + row[3] + "</td>"
		print "<td>" + row[4] + "</td>"
		print "</tr>"
	print "</table>"
else:
    print "user or password invalid"
    
    

print "<br> <a href='http://localhost/p1/carrental_form_list.html' > Volver </a>"	
