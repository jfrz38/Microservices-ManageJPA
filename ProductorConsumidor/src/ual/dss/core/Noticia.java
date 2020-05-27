package ual.dss.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Noticia {

	private String date;
	private String shortDescription;
	private String largeDescription;
	private String url;
	
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
	
	public Noticia(String shortDescription, String largeDescription, String url) {
		this.shortDescription=shortDescription;
		this.largeDescription=largeDescription;
		this.url = url;
		this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	public Noticia(String shortDescription, String largeDescription, String date, String url ) {
		this.shortDescription=shortDescription;
		this.largeDescription=largeDescription;
		this.date = date;
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Noticia [fecha = "+date+", descripción breve = " + shortDescription + ", descripción larga = " + largeDescription + "]";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
