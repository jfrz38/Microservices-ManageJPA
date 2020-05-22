package dwsc.proyecto.client.eliminarcomentario.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dwsc.proyecto.client.eliminarcomentario.dao.CommentRepository;
import dwsc.proyecto.client.eliminarcomentario.dao.MovieRepository;
import dwsc.proyecto.client.eliminarcomentario.domain.Comment;
import dwsc.proyecto.client.eliminarcomentario.domain.Movie;

@RestController
public class CommentController {

	@Autowired
	CommentRepository commentRepo;
	@Autowired
	MovieRepository movieRepo;
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
		Optional<Comment> comment = commentRepo.findById(id);
		if(!comment.isPresent()) return ResponseEntity.notFound().build();
		Movie movie = comment.get().getMovie();
		commentRepo.deleteById(id);
		movie.addRating(comment.get().getRating()*-1);
		System.out.println("movie comment = "+movie.getRating());
		movieRepo.save(movie);
		return ResponseEntity.ok().build();
	}
	
}
