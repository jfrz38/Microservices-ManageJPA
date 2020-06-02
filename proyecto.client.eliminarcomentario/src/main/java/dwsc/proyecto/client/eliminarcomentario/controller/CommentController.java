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
	
	@Operation(summary = "Elimina un comentario", description = "Operación para eliminar un comentario según el ID")
	@ApiResponses({@ApiResponse(responseCode="200", description="Comentario eliminado"),
		@ApiResponse(responseCode="404", description="Comentario no encontrado")})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteComment(@Parameter(description="ID del comentario a eliminar") @PathVariable("id") Long id) {
		Optional<Comment> comment = commentRepo.findById(id);
		if(!comment.isPresent()) return ResponseEntity.notFound().build();
		Movie movie = comment.get().getMovie();
		commentRepo.deleteById(id);
		movie.addRating(comment.get().getRating()*-1);
		movieRepo.save(movie);
		return ResponseEntity.ok().body("Comentario eliminado con éxito");
	}
	
}

























