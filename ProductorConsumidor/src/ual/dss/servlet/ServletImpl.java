package ual.dss.servlet;

import java.io.IOException;
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
import ual.dss.core.Noticia;
import ual.dss.xmlib.XMLCoder;
import ual.dss.xmlib.XMLDecoder;

// TODO: Auto-generated Javadoc
/**
 * The Class ServletImpl.
 */

public class ServletImpl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The buffer impl. */
	static Buffer bufferImpl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/*************
		 * CONSUMIDOR *
		 *************/		
		try {
			getreference();
			StringHolder aux = new StringHolder();
			List<Noticia> noticias = new ArrayList<Noticia>();
			while(bufferImpl.get(aux)) {
				Noticia noticia = XMLDecoder.decodeSingleXML(aux.value);
				noticias.add(noticia);
			}
			
			request.setAttribute("noticias", noticias);
			request.getRequestDispatcher("noticias.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("noticias", new ArrayList<Noticia>());
			request.getRequestDispatcher("noticias.jsp").forward(request, response);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {

		/*************
		 * PRODUCTOR *
		 *************/
		
		if (req.getParameter("action").compareTo("Crear noticia") != 0) {
			// Aquí no debería entrar nunca; pero si llega el post por otra acción
			// que no sea el botón del formulario no se permite insertar el objeto
			req.setAttribute("resultError", "No se ha podido enviar la noticia");
			req.setAttribute("resultOK", "");
			req.getRequestDispatcher("/index.jsp").forward(req, response);
			return;
		}

		Enumeration<?> elements = req.getParameterNames();
		String title = req.getParameter((String) elements.nextElement());
		String description = req.getParameter((String) elements.nextElement());
		String url = req.getParameter((String) elements.nextElement());
		
		try {
			getreference();

			if (title.isEmpty() || description.isEmpty() || url.isEmpty()) {
				req.setAttribute("resultError", "Introduce todos los parámetros");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}

			if (title.replace(" ", "").length() > 30) {
				req.setAttribute("resultError", "La descripción corta es demasiado larga");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}
			if (title.replace(" ", "").length() < 5) {
				req.setAttribute("resultError", "La descripción corta es demasiado corta");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}

			if (description.replace(" ", "").length() > 500) {
				req.setAttribute("resultError", "La descripción larga es demasiado larga");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}
			if (description.replace(" ", "").length() < 20) {
				req.setAttribute("resultError", "La descripción larga es demasiado corta");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}
			
			if(url.isEmpty()) {
				req.setAttribute("resultError", "URL vacía");
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				return;
			}

			Noticia noticia = new Noticia(title, description, url);
			List<Noticia> noticias = new ArrayList<Noticia>();
			noticias.add(noticia);
			String mensajeXML = "";
			
			try {
				XMLCoder.codeXML("documentoProductorConsumidor", noticias);
				mensajeXML = XMLCoder.codeXML(noticias);

			} catch (Exception e) {
				req.setAttribute("resultError", "Error: " + e.getMessage());
				req.setAttribute("resultOK", "");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
			}

			if (!bufferImpl.put(mensajeXML))
				throw new Exception("No se ha podido insertar el elemento en el buffer");

			req.setAttribute("resultOK", "El elemento ha sido insertado correctamente");
			req.setAttribute("resultError", "");
			req.getRequestDispatcher("/index.jsp").forward(req, response);

		} catch (Exception e) {
			req.setAttribute("resultError", "Error: " + e.getMessage());
			req.setAttribute("resultOK", "");
			req.getRequestDispatcher("/index.jsp").forward(req, response);
		}

	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 * @throws InvalidName   the invalid name
	 * @throws InvalidName   the invalid name
	 * @throws NotFound      the not found
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

}
