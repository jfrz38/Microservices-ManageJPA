package dwsc.proyecto.client.comentarpelicula.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dwsc.proyecto.client.comentarpelicula.dao.CommentRepository;
import dwsc.proyecto.client.comentarpelicula.dao.MovieRepository;
import dwsc.proyecto.client.comentarpelicula.domain.Comment;
import dwsc.proyecto.client.comentarpelicula.domain.Movie;

@RestController
public class CommentController {

	@Autowired
	CommentRepository commentRepo;
	@Autowired
	MovieRepository movieRepo;
	
	@PostMapping("/insert/{movieID}")
	@JsonInclude(content=Include.NON_NULL)
	public ResponseEntity<?> insertComment(@RequestBody Comment comment, @PathVariable("movieID") Long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if(movie.isPresent()) {
			comment.setMovie(movie.get());
			commentRepo.save(comment);
			return ResponseEntity.ok(comment);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
