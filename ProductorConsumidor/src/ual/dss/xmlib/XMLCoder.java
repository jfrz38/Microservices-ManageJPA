package ual.dss.xmlib;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXParseException;

import ual.dss.core.Mensaje;
import ual.dss.core.Noticia;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLCoder.
 */
public class XMLCoder {

	/**
	 * Codifica la lista de mensajes en el archivo que entra por parametro en formato XML.
	 *
	 * @param fileName the file name
	 * @param entrada the entrada
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean codeXML(String fileName, List<Noticia> entrada) throws Exception {
		
		if (entrada.isEmpty()) {
			System.out.println("ERROR empty List");
			return false;
		} else {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, fileName, null);
			document.setXmlVersion("1.0");
			// Main Node
			Element raiz = document.getDocumentElement();
			
			// Por cada correo creamos un registro con el valor
			for(int i=0; i<entrada.size();i++){
			
				Element noticiaNode = document.createElement("noticia");
				Element shortDescNode = document.createElement("shortDescription");
				Element largeDescNode = document.createElement("largeDescription");
				Element dateNode = document.createElement("date");
	
				Text nodeNomValue = document.createTextNode(entrada.get(i).getShortDescription());
				Text nodeValueValue = document.createTextNode(entrada.get(i).getLargeDescription());
				Text nodeDateValue = document.createTextNode(entrada.get(i).getDate());
				
				largeDescNode.appendChild(nodeValueValue);
				shortDescNode.appendChild(nodeNomValue);
				dateNode.appendChild(nodeDateValue);
	
				
				raiz.appendChild(noticiaNode);
				noticiaNode.appendChild(shortDescNode);
				noticiaNode.appendChild(largeDescNode);
				noticiaNode.appendChild(dateNode);
				
			}
			// Generate XML
			Source source = new DOMSource(document);
			// Indicamos donde lo queremos almacenar
			Result result = new StreamResult(new java.io.File(fileName + ".xml"));
																				
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			return true;
		}
	}
	
	/**
	 * Codifica la lista de mensajes en XML en forma de texto
	 *
	 * @param entrada the entrada
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String codeXML(List<Noticia> entrada) throws Exception {
		
		if (entrada.isEmpty()) {
			return "ERROR empty List";
		} else {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "documentoProductorConsumidor", null);
			document.setXmlVersion("1.0");
			// Main Node
			Element raiz = document.getDocumentElement();
			
			// Por cada correo creamos un registro con el valor
			for(int i=0; i<entrada.size();i++){
			
				Element noticiaNode = document.createElement("noticia");
				Element shortDescNode = document.createElement("shortDescription");
				Element largeDescNode = document.createElement("largeDescription");
				Element dateNode = document.createElement("date");
	
				Text nodeNomValue = document.createTextNode(entrada.get(i).getShortDescription());
				Text nodeValueValue = document.createTextNode(entrada.get(i).getLargeDescription());
				Text nodeDateValue = document.createTextNode(entrada.get(i).getDate());
	
				largeDescNode.appendChild(nodeValueValue);
				shortDescNode.appendChild(nodeNomValue);
				dateNode.appendChild(nodeDateValue);
	
				
				raiz.appendChild(noticiaNode);
				noticiaNode.appendChild(shortDescNode);
				noticiaNode.appendChild(largeDescNode);
				noticiaNode.appendChild(dateNode);
				
			}
			// Generate XML
			Source source = new DOMSource(document);
			// Indicamos donde lo queremos almacenar

			StringWriter writer = new StringWriter();
		    StreamResult result = new StreamResult(writer);
		    TransformerFactory tFactory = TransformerFactory.newInstance();
		    Transformer transformer = tFactory.newTransformer();
		    transformer.transform(source,result);
		    String strResult = writer.toString();
			System.out.println(strResult);
			return strResult;
		}
	}

	public static boolean validate(String filename){
	       try {
	          File inputFile = new File(filename);
	          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	          Document doc = dBuilder.parse(inputFile);
	          doc.getDocumentElement().normalize();

	          NodeList nList = doc.getElementsByTagName("noticia");

	          for (int temp = 0; temp < nList.getLength(); temp++) 
	          {
	            Node nNode = nList.item(temp);

	            if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	            {
	                    Element eElement = (Element) nNode;

	                    eElement.getElementsByTagName("date").item(0).getTextContent();
	                    eElement.getElementsByTagName("shortDescription").item(0).getTextContent();
	                    eElement.getElementsByTagName("longDescription").item(0).getTextContent();
	            }
	         }
	       } catch (Exception e) 
	       {
		       System.out.println("Error encontrado, " + e.getMessage());
	           return false;
	       }
	       
	       return true;
	    }
}
