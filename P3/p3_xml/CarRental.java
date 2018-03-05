import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;

public class CarRental {


	public static void main(String argv[]) {
			if(argv.length == 1) {
				String command = argv[0];
				if(command.equals("reset")) outputDocumentToFile(createDocument());
				else if(command.equals("new")) nuevo(readDocument());
				else if(command.equals("list")) outputDocument(readDocument());
				else if(command.equals("validate")) validate();
				else if(command.equals("xslt")) executeXSLT(readDocument());
				else {
					System.out.println(command + " is not a valid option.");
					printUsage();
				}
			} else {
            printUsage();
        }
    }
    public static void validate(){
		try {
		SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
		Document anotherDocument = builder.build(new File("carrental.xml"));
		System.out.println("Root: " + anotherDocument.getRootElement().getName());
		} catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        
	
	}
    
    public static Document createDocument() {
        // Create the root element
        Element carElement = new Element("carrental");
        //create the document
        Document myDocument = new Document(carElement);
        return myDocument;
    }
     public static void outputDocumentToFile(Document myDocument) {
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            //output to a file
            FileWriter writer = new FileWriter("carrental.xml");
            
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void nuevo(Document myDocument){
	Element root = myDocument.getRootElement();
	Element carRental = newElemento();
	root.addContent(carRental);
	outputDocumentToFile(myDocument);
		
	}
	public static Element newElemento(){
		Element carElement = new Element("rental");
		carElement.setAttribute(new Attribute("id", "25"));//Pendiente de hacer un id correcto
		System.out.print("Marca del vehiculo:");
		String input = System.console().readLine();
		Element make = new Element("make");
		make.addContent(input);
		carElement.addContent(make);
		/////////////////////////////////////
		System.out.print("Modelo:");
		input = System.console().readLine();
		Element modelo = new Element("model");
		modelo.addContent(input);
		carElement.addContent(modelo);
		/////////////////////////////////////
		System.out.print("Numero de dias:");
		input = System.console().readLine();
		Element nofdays = new Element("nofdays");
		nofdays.addContent(input);
		carElement.addContent(nofdays);
		/////////////////////////////////////
		System.out.print("Numero de vehiculos:");
		input = System.console().readLine();
		Element nofunits = new Element("nofunits");
		nofunits.addContent(input);
		carElement.addContent(nofunits);
		/////////////////////////////////////
		System.out.print("Descuentos:");
		input = System.console().readLine();
		Element des = new Element("discount");
		des.addContent(input);
		carElement.addContent(des);
		return carElement;
		
	}
	public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("carrental.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * the stdout.
     * @param myDocument a JDOM document.
     */
    public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method takes a JDOM document in memory, an XSLT file at example.xslt,
     * and outputs the results to stdout.
     * @param myDocument a JDOM document .
     */
    public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("carrental.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}

    
     /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usage: Example [option] \n where option is one of the following:");
        System.out.println("  showDocument - create a new document in memory and print it to the console");
        System.out.println("  accessChild - create a new document and show its child element");
        System.out.println("  removeChild - create a new document and remove its child element");
        System.out.println("  save   - create a new document and save it to myFile.xml");
        System.out.println("  load   - read and parse a document from example.xml");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in example.xslt");
    }
}
