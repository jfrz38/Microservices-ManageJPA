package ual.dss.xmlib;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.util.List;
import java.io.*;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Validator.
 */
public class Validator {

	/**
	 * Valida el xml.
	 *
	 * @param xml the xml
	 * @param xsd the xsd
	 * @return true, if successful
	 */
	public static boolean validate(String xml, String xsd) {

		try {
			// XML a validar
			Source xmlFile = new StreamSource(new File(xml));
			// Esquema con el que comparar
			Source schemaFile = new StreamSource(new File(xsd));
			// Preparación del esquema
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(schemaFile);
			// Creación del validador
			javax.xml.validation.Validator validator = schema.newValidator();
			// Definición del manejador de excepciones del validador
			final List exceptions = new LinkedList();
			validator.setErrorHandler(new ErrorHandler() {
				public void warning(SAXParseException exception) throws SAXException {
					exceptions.add(exception);
				}

				public void fatalError(SAXParseException exception) throws SAXException {
					exceptions.add(exception);
				}

				public void error(SAXParseException exception) throws SAXException {
					exceptions.add(exception);
				}
			});
			// Validación del XML
			validator.validate(xmlFile);
			if (exceptions.size() == 0) {
				System.out.println("FILE " + xmlFile.getSystemId() + " IS VALID");
			} else {
				System.out.println("FILE " + xmlFile.getSystemId() + " IS INVALID");
				return false;
			}
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
