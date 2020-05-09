package ual.dss.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Noticia {

	private String date;
	private String shortDescription;
	private String largeDescription;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getLargeDescription() {
		return largeDescription;
	}
	public void setLargeDescription(String largeDescription) {
		this.largeDescription = largeDescription;
	}
	
	public Noticia(String shortDescription, String largeDescription) {
		this.shortDescription=shortDescription;
		this.largeDescription=largeDescription;
		this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	public Noticia(String shortDescription, String largeDescription, String date ) {
		this.shortDescription=shortDescription;
		this.largeDescription=largeDescription;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Noticia [fecha = "+date+", descripción breve = " + shortDescription + ", descripción larga = " + largeDescription + "]";
	}
	
}
