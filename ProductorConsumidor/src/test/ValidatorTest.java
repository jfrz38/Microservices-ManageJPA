package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ual.dss.xmlib.Validator;

public class ValidatorTest {

	private String xsd;
	private String xmlOk;
	private String xmlRoto;
	
	@Before
	public void setUp() throws Exception {
		this.xsd="documentoProductorConsumidor.xsd";
		this.xmlOk="documentoProductorConsumidor.xml";
		this.xmlRoto="documentoProductorConsumidorRoto.xml";
	}

	@Test
	public void test() {
		assertTrue(Validator.validate(xmlOk, xsd));
	}
	@Test
	public void testRoto() {
		assertTrue(!Validator.validate(xmlRoto, xsd));
	}

}
