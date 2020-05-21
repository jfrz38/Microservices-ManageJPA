package ual.dss.xmlib;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import ual.dss.core.Mensaje;
import ual.dss.core.Noticia;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLDecoder.
 */
public class XMLDecoder {

	/**
	 * Decodifica el XML a partir de un archivo
	 *
	 * @param fileName the file name
	 * @return La lista de mensajes decodificados
	 */
	public static List<Noticia> decodeXML(String fileName){
		try {
	          File inputFile = new File(fileName+".xml");
	          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	          Document doc = dBuilder.parse(inputFile);
	          doc.getDocumentElement().normalize();
	          //System.out.println("Elemento raiz :" + doc.getDocumentElement().getNodeName());
	          NodeList nList = doc.getElementsByTagName("noticia");
	          List<Noticia> salida = new ArrayList<Noticia>();
	         //System.out.println("----------------------------");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) nNode;
	                    
	                    if(eElement.getElementsByTagName("shortDescription").getLength() == 0) throw new Exception("Falta el elemento shortDescription");
	                    if(eElement.getElementsByTagName("largeDescription").getLength() == 0) throw new Exception("Falta el elemento largeDescription");
	                    if(eElement.getElementsByTagName("date").getLength() == 0) throw new Exception("Falta el elemento date");
	                    
	                    Noticia tempMensaje = new Noticia(eElement.getElementsByTagName("shortDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("largeDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("date").item(0).getTextContent());
	                    salida.add(tempMensaje);
	             }
	         }
	         return salida;
	       } catch (SAXParseException e) {
	    	  
	    	   System.out.println("\nERROR!!!!: Tag mal formado");
	    	   		
	       }  catch (Exception e) {
	    	   
	    	   System.out.println("\nERROR!!!!: "+e.getMessage());
	       }
		return new ArrayList<Noticia>();   
	}
	
	/**
	 * Decodifica el XML a partir de un string
	 *
	 * @param xml El xml a decodificar
	 * @param flag the flag
	 * @return La lista de mensajes decodificados
	 */
	public static List<Noticia> decodeXML(String xml, int flag){
		try {
	          
	         
	          DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	          InputSource is = new InputSource();
	          is.setCharacterStream(new StringReader(xml));

	          Document doc = db.parse(is);

	          
	          doc.getDocumentElement().normalize();
	          //System.out.println("Elemento raiz :" + doc.getDocumentElement().getNodeName());
	          NodeList nList = doc.getElementsByTagName("noticia");
	          List<Noticia> salida = new ArrayList<Noticia>();
	         //System.out.println("----------------------------");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) nNode;
	                    
	                    if(eElement.getElementsByTagName("shortDescription").getLength() == 0) throw new Exception("Falta el elemento shortDescription");
	                    if(eElement.getElementsByTagName("largeDescription").getLength() == 0) throw new Exception("Falta el elemento largeDescription");
	                    if(eElement.getElementsByTagName("date").getLength() == 0) throw new Exception("Falta el elemento date");
	                    
	                    Noticia tempMensaje = new Noticia(eElement.getElementsByTagName("shortDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("largeDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("date").item(0).getTextContent());
	                    salida.add(tempMensaje);
	             }
	         }
	         return salida;
	       } catch (SAXParseException e) {
	    	  
	    	   System.out.println("\nERROR!!!!: Tag mal formado");
	    	   		
	       }  catch (Exception e) {
	    	   
	    	   System.out.println("\nERROR!!!!: "+e.getMessage());
	       }
		return new ArrayList<Noticia>();   
	}
	
	public static Noticia decodeSingleXML(String xml) {
		try {
	          
	          DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	          InputSource is = new InputSource();
	          is.setCharacterStream(new StringReader(xml));
	          Document doc = db.parse(is);
	          doc.getDocumentElement().normalize();
	          NodeList nList = doc.getElementsByTagName("noticia");
	          Noticia salida = new Noticia("","","");
	            Node nNode = nList.item(0);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) nNode;
	                    
	                    if(eElement.getElementsByTagName("shortDescription").getLength() == 0) throw new Exception("Falta el elemento shortDescription");
	                    if(eElement.getElementsByTagName("largeDescription").getLength() == 0) throw new Exception("Falta el elemento largeDescription");
	                    if(eElement.getElementsByTagName("date").getLength() == 0) throw new Exception("Falta el elemento date");
	                    
	                    Noticia tempMensaje = new Noticia(eElement.getElementsByTagName("shortDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("largeDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("date").item(0).getTextContent());
	                    salida = tempMensaje;
	             }
	         
	         return salida;
	       } catch (SAXParseException e) {
	    	  
	    	   System.out.println("\nERROR!!!!: Tag mal formado");
	    	   		
	       }  catch (Exception e) {
	    	   
	    	   System.out.println("\nERROR!!!!: "+e.getMessage());
	       }
		return new Noticia("","","");
	}
}
