package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ual.dss.core.*;
import ual.dss.xmlib.XMLCoder;
import ual.dss.xmlib.XMLDecoder;

public class XMLibTest {

	List<Noticia> noticias;
	String resultado;
	@Before
	public void setUp() throws Exception {
		Noticia noticia = new Noticia("short description","large description","url");
		noticias = new ArrayList<Noticia>();
		noticias.add(noticia);
		resultado = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><documentoProductorConsumidor><noticia><shortDescription>short description</shortDescription><largeDescription>large description</largeDescription><date>"+new SimpleDateFormat("dd/MM/yyyy").format(new Date())+"</date><url>url</url></noticia></documentoProductorConsumidor>";
	}

	@Test
	public void testCoder() {
		try {
			assertEquals(resultado,XMLCoder.codeXML(noticias));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDecoder() {
		try {
			assertEquals(noticias.get(0).toString(),XMLDecoder.decodeSingleXML(resultado).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
