package ual.dss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import BufferApp.Buffer;
import BufferApp.BufferHelper;
import ual.dss.core.Mensaje;
import ual.dss.core.Noticia;
import ual.dss.xmlib.Validator;
import ual.dss.xmlib.XMLCoder;
import ual.dss.xmlib.XMLDecoder;

// TODO: Auto-generated Javadoc
/**
 * The Class ServletImpl.
 */
public class ServletImpl extends HttpServlet {

	/** The buffer impl. */
	static Buffer bufferImpl;

	/**
	 * Realiza la accion de [Enviar]. Crea un documento XML y se lo pasa al
	 * objeto CORBA, para que lo almacene en el buffer.
	 *
	 * @param out            Indica donde se escribira la respuesta de la operacion.
	 * @param email            El destinatario del mensaje.
	 * @param elemento            El elemento del mensaje.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void actionEnviar(java.io.PrintWriter out, String shortDescription, String largeDescription) throws java.io.IOException {
		int nelementos = -1;
		try {
			getreference();
			if(shortDescription.isEmpty()||largeDescription.isEmpty()) {
				printResultado(out, "<font color='#DF0101'> Introduce todos los parametros");
				return;
			}
			
			if(shortDescription.replace(" ", "").length()>30) {
				printResultado(out, "<font color='#DF0101'> La descripción corta es demasiado larga");
				return;
			}
			if(shortDescription.replace(" ", "").length()<5) {
				printResultado(out, "<font color='#DF0101'> La descripción corta es demasiado corta");
				return;
			}
			
			if(largeDescription.replace(" ", "").length()>500) {
				printResultado(out, "<font color='#DF0101'> La descripción larga es demasiado larga");
				return;
			}
			if(largeDescription.replace(" ", "").length()<20) {
				printResultado(out, "<font color='#DF0101'> La descripción larga es demasiado corta");
				return;
			}

			Noticia noticia = new Noticia(shortDescription, largeDescription);
			List<Noticia> noticias = new ArrayList<Noticia>();
			noticias.add(noticia);
			// sid.xmlCoder xmlDoc=new sid.xmlCoder(mensaje);
			String mensajeXML = "";
			boolean estado = false;
			try {
				estado = XMLCoder.codeXML("documentoProductorConsumidor", noticias);
				mensajeXML = XMLCoder.codeXML(noticias);

			} catch (Exception e) {
				printResultado(out, "<font color='#DF0101'>"+e.getMessage());
			}

			if (!bufferImpl.put(mensajeXML))
				throw new Exception("No se ha podido insertar el elemento en el buffer");

			nelementos = bufferImpl.num_elementos();

			printResultado(out, "<font color='#2EFE64'> El elemento ha sido insertado correctamente");

		} catch (Exception e) {
			printResultado(out, "<font color='#DF0101'>"+e.getMessage());
		}

	}

	/**
	 * Realiza la accion de [Leer]. Recibe un documento XML del obejto CORBA y
	 * devuelve el valor de los elementos <email> y <elemento> del documento XML
	 * que obtenemos del objeto CORBA.
	 * 
	 * @param out
	 *            Donde se escribira la respuesta de la accion.
	 */
	protected void actionLeer(java.io.PrintWriter out) {
		String shortDesc = null;
		String largeDesc = null;
		String date = null;
		int nelementos = -1;

		try {
			getreference();

			StringHolder aux = new StringHolder();
			//System.out.println("aux = "+aux.toString());
			boolean estado = bufferImpl.read(aux);

			if (estado) {
				List<Noticia> mensajesLeidos = new ArrayList<Noticia>();
				mensajesLeidos = XMLDecoder.decodeXML(aux.value, 1);
				nelementos = bufferImpl.num_elementos();
				
				if (aux.value.compareTo("null") == 0)
					throw new Exception("No se ha podido leer el elemento del buffer.");

				/*for(Noticia noticia : mensajesLeidos){
					shortDesc = noticia.getShortDescription();
					largeDesc = noticia.getLargeDescription();
					date = noticia.getDate();
					printResultado(out, "<font color='#2EFE64'>short: " + shortDesc+";large:"+largeDesc+"; date = "+date);
				}*/
				shortDesc = mensajesLeidos.get(0).getShortDescription();
				largeDesc = mensajesLeidos.get(0).getLargeDescription();
				date = mensajesLeidos.get(0).getDate();
				printResultado(out, "<font color='#2EFE64'>short: " + shortDesc+";large:"+largeDesc+"; date = "+date);
			} else {
				printResultado(out, "<font color='#DF0101'>"+aux.value);
			}

		} catch (Exception e) {
			printResultado(out, "<font color='#DF0101'>"+e.getMessage());
		}

	}

	/**
	 * Realiza la accion de [Recibir]. Recibe un documento XML del obejto CORBA
	 * y devuelve el valor de los elementos <email> y <elemento> del documento
	 * XML que obtenemos del objeto CORBA.
	 * 
	 * @param out
	 *            Donde se escribira la respuesta de la accion.
	 */
	protected void actionRecibir(java.io.PrintWriter out) {
		String shortDesc = null;
		String largeDesc = null;
		String date = null;
		int nelementos = -1;
		try {
			getreference();
			StringHolder aux = new StringHolder();
			boolean estado = bufferImpl.get(aux);
			if (estado) {
				List<Noticia> mensajesLeidos = new ArrayList<Noticia>();
				mensajesLeidos = XMLDecoder.decodeXML(aux.value, 1);
				nelementos = bufferImpl.num_elementos();

				if (aux.value.compareTo("null") == 0)
					throw new Exception("No se ha podido sacar el elemento del buffer.");

				nelementos = bufferImpl.num_elementos();
				shortDesc = mensajesLeidos.get(0).getShortDescription();
				largeDesc = mensajesLeidos.get(0).getLargeDescription();
				date = mensajesLeidos.get(0).getDate();
				printResultado(out, "<font color='#2EFE64'>short: " + shortDesc+";large:"+largeDesc+"; date = "+date);
			} else {
				printResultado(out, "<font color='#DF0101'>"+aux.value);
			}
		} catch (Exception e) {
			printResultado(out, "<font color='#DF0101'>"+e.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
		      response.setContentType("text/html");

		      java.io.PrintWriter out=response.getWriter();


		      out.println("<HTML>");
		      out.println("<HEAD>");
		      out.println("</HEAD>");
		      out.println("<BODY>");
		      out.println("Error! Los parametros no han sido enviados por el metodo POST");
		      out.println("</BODY>");
		      out.println("</HTML>");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {

		Enumeration elements = req.getParameterNames();
		String name1 = (String) elements.nextElement(); /** Email */
		String email = req.getParameter(name1);
		String name2 = (String) elements.nextElement(); /** Valor de elemento */
		String elemento = req.getParameter(name2);

		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		mensajes.add(new Mensaje(email, elemento));

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (req.getParameter("action").compareTo("Enviar") == 0)
			actionEnviar(out, email, elemento);
		else if (req.getParameter("action").compareTo("Recibir") == 0)
			actionRecibir(out);
		else if (req.getParameter("action").compareTo("Leer") == 0)
			actionLeer(out);
		else {
			printForm(out, null, null, bufferImpl.num_elementos());
			out.println("Accion '" + req.getParameter("action") + "' no reconocida. Debe ser 'Enviar' o 'Recibir'.");
		}

	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 * @throws InvalidName the invalid name
	 * @throws InvalidName the invalid name
	 * @throws NotFound the not found
	 * @throws CannotProceed the cannot proceed
	 */
	private void getreference()
			throws org.omg.CORBA.ORBPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.NotFound,
			org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		// create and initialize the ORB
		String args[] = new String[4];
		args[0] = "-ORBInitialPort";
		args[1] = "900";
		args[2] = "-ORBInitialHost";
		args[3] = "localhost";
		ORB orb = ORB.init(args, null);

		// get the root naming context
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		// Use NamingContextExt instead of NamingContext. This is
		// part of the Interoperable naming Service.
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		// resolve the Object Reference in Naming
		String name = "Buffer";
		bufferImpl = BufferHelper.narrow(ncRef.resolve_str(name));
	}

	/**
	 * Escribe en la salida indicada las posibles acciones del formulario.
	 * 
	 * @param out
	 *            Indica donde se escribe la respuesta.
	 */
	private void printActions(PrintWriter out) {

		out.println(
				"<p><input value='Enviar' alt='Press button to export'type='submit' name='action'><input value='Recibir' alt='Press button to export'type='submit' name='action'><input value='Leer' alt='Press button to export' type='submit' name='action'> <input value=' Reset ' type='reset' name='action'></p></center></form></td></tr>");

	}

	/**
	 * Imprime el formulario a traves del cual el usuario podra enviar datos y
	 * por el cual se le mostrara la informacion al usuario.
	 *
	 * @param out            Indica donde se escribira el formulario.
	 * @param email the email
	 * @param elemento the elemento
	 * @param nelementos            Numero de elementos que hay en el buffer. Se usa como
	 *            indicador para que el usuario vea el estado del buffer.
	 */
	private void printForm(PrintWriter out, String email, String elemento, int nelementos) {

		out.println("<form action='http://localhost:8080/ProductorConsumidor/servlet' method='post'>");
		out.println("<center><font face='Arial,Helvetica'><font size='-1'>Descripción corta:</font></font>");
		out.println("<input name='email' size='40'>" + email + "</input>");
		out.println("<p><textarea name='XMLtemplate' rows='25' cols='50'>" + elemento + "</textarea></p>");
		out.println("<p><b>Numero de elementos en el Buffer: </b>" + nelementos);

	}

	/**
	 * Escribe en la salida especificada la cabecera de respuesta en formato
	 * HTML.
	 * 
	 * @param out
	 *            Indica donde se va a escribir la respuesta.
	 */
	private void printHeader(java.io.PrintWriter out) {

		out.println(
				"<html><head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'><meta name='Author' content='Luis Iribarne'><meta name='Description' content='University of Almeria (Spain)'><title>Prototipo HTML para el Productor-Consumidor</title></head>");
		out.println(
				"<body style='color: rgb(0, 0, 0); background-color: rgb(255, 255, 255);' alink='#990000' link='#043a66' vlink='#999900'><dl>");
		out.println(
				"<table nosave='' border='0' cellspacing='5' width='98%'><tbody><tr nosave='' valign='top'><td nosave='' width='100%'><blockquote> </blockquote><center> <b> <font face='Arial,Helvetica'><font color='#660000'><font size='+1'> Registrar Noticia: </font></font></font></b></center><br>");

	}

	/**
	 * Muestra un mensaje con el resultado de la operacion que se esta
	 * ejecutando.
	 * 
	 * @param out
	 *            Indica donde se escribe la respuesta.
	 * @param resultado
	 *            Cadena de caracteres con el mensaje del resultado de la
	 *            operacion.
	 */
	private void printResultado(PrintWriter out, String resultado) {

		out.print("");
		printHeader(out);
		printForm(out, "", "", bufferImpl.num_elementos());
		printActions(out);
		out.println(
				"<tr><td><br><center><font face='Arial,Helvetica'>" + resultado + "</font></font></center></td></tr>");

	}
}
