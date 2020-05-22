package dwsc.proyecto.UI.gestor.domain;

import java.util.Set;

import javax.validation.constraints.NotNull;

public class Movie {
	private Long id;
	
	@NotNull
	private String name;
	@NotNull
	private int year;
	@NotNull
	private double rating;
	@NotNull
	private String description;
	@NotNull
	private String imageURL;
	
	private double totalRating;
	
	private Set<Comment> comments;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public Set<Comment> getComments(){
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public double getTotalRating() {
		return totalRating;
	}
	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
