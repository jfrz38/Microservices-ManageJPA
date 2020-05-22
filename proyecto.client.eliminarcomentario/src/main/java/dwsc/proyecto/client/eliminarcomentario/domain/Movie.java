package dwsc.proyecto.client.eliminarcomentario.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		// double totalRating = 0.0;
		this.comments = comments;
		for (Comment comment : comments) {
			comment.setMovie(this);
			// totalRating+=comment.getRating();
		}
		// this.setRating(totalRating/comments.size());
	}

	public double getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}

	public void addRating(double value) {
		totalRating += value;
		try {
			if(comments.size()==0) {
				rating = 0;
			}else {
				rating = totalRating / comments.size();
			}
			
		} catch (Exception e) {
			rating = 0;
		}
	}
}
