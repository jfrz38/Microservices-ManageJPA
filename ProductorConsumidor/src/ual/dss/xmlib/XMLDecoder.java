package ual.dss.xmlib;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import ual.dss.core.Noticia;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLDecoder.
 */
public class XMLDecoder {

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
	                    if(eElement.getElementsByTagName("url").getLength() == 0) throw new Exception("Falta el elemento url");
	                    
	                    Noticia tempMensaje = new Noticia(eElement.getElementsByTagName("shortDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("largeDescription").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("date").item(0).getTextContent(),
	                    		eElement.getElementsByTagName("url").item(0).getTextContent());
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
