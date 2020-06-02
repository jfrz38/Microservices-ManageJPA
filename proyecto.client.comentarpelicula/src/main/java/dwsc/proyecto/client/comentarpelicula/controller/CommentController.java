package dwsc.proyecto.client.comentarpelicula.controller;

import java.net.URI;
import java.net.URISyntaxException;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CommentController {

	@Autowired
	CommentRepository commentRepo;
	@Autowired
	MovieRepository movieRepo;

	@Operation(summary = "Insertar un comentario", description = "Operación para insertar un comentario en una película en concreto")
	@ApiResponses({@ApiResponse(responseCode="200", description="Comentario añadido con éxito"),
		@ApiResponse(responseCode="404", description="Película a la que añadir el comentario no encontrada")})
	@PostMapping("/insert/{movieID}")
	@JsonInclude(content = Include.NON_NULL)
	public ResponseEntity<String> insertComment(@Parameter(description="Comnetario a insertar. No es necesario crear la película en el objeto a insertar.") @RequestBody Comment comment, 
			@Parameter(description="ID de la película en la que añadir el comentario") @PathVariable("movieID") Long id)
			throws URISyntaxException {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			comment.setMovie(movie.get());
			commentRepo.save(comment);
			return ResponseEntity.created(new URI("/" + comment.getId())).body("Comentario añadido con éxito");
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}