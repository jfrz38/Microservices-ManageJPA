package ual.dss.core;

// TODO: Auto-generated Javadoc
/**
 * The Class Mensaje.
 */
public class Mensaje {

	/** The email. */
	private String email;
	
	/** The elemento. */
	private String elemento;
	
	/**
	 * Instantiates a new mensaje.
	 */
	public Mensaje(){
		email="";
		elemento="";
	}
	
	/**
	 * Instantiates a new mensaje.
	 *
	 * @param email the email
	 * @param valor the valor
	 */
	public Mensaje(String email, String valor){
		this.email=email;
		this.elemento=valor;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the elemento.
	 *
	 * @return the elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * Sets the elemento.
	 *
	 * @param elemento the new elemento
	 */
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Mensaje [email=" + email + ", elemento=" + elemento + "]";
	}
	
	
}
