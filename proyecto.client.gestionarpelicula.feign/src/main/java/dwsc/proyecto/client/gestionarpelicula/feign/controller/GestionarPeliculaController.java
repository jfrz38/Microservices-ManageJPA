package dwsc.proyecto.client.gestionarpelicula.feign.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dwsc.proyecto.client.gestionarpelicula.feign.dao.MovieRepository;
import dwsc.proyecto.client.gestionarpelicula.feign.dao.VerificarDatosClient;
import dwsc.proyecto.client.gestionarpelicula.feign.domain.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class GestionarPeliculaController {

	@Autowired
	private VerificarDatosClient verificarDatos;

	@Autowired
	private MovieRepository movieRepo;

	@Operation(summary = "Inserta una película", description = "Operación para insertar una película")
	@ApiResponses({@ApiResponse(responseCode="200", description="Película insertada correctamente"),
		@ApiResponse(responseCode="204", description="Película no verificada"),
		@ApiResponse(responseCode="204", description="Película no existente")})
	@PostMapping("/insert")
	public ResponseEntity<String> insertMovie(@Parameter(description="Película a insertar") @RequestBody Movie movie) throws Exception {
		try {
			ResponseEntity<String> response = verificarDatos.getData(movie.getName(),movie.getYear());
			if (response.getStatusCodeValue() == 200) {
				if(movie.getImageURL().equals(""))movie.setImageURL(response.getBody());
				return insertIntoDB(movie);
			} else {
				return ResponseEntity.noContent().build();
			}
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}

	@Operation(summary = "Elimina una película", description = "Operación para eliminar una película según el ID")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@Parameter(description="ID de la película a eliminar") @PathVariable("id") Long id){
		if(!movieRepo.findById(id).isPresent()) return ResponseEntity.notFound().build();
		movieRepo.deleteById(id);
		return ResponseEntity.ok("Película "+id+" eliminada correctamente");
	}
	
	@Operation(summary = "Actualiza una película", description = "Operación para actualizar una película")
	@ApiResponses({@ApiResponse(responseCode="200", description="Película actualizada correctamente"),
		@ApiResponse(responseCode="204", description="Película a actualizar no encontrada")})
	@PutMapping("/{id}")
	public ResponseEntity<String> updateMovie(@Parameter(description="Nuevos valores de la película a actualizar") @RequestBody Movie updateMovie,
			@Parameter(description="ID de la película a insertar") @PathVariable("id") Long id){
		Optional<Movie> optionMovie = movieRepo.findById(id);
		if(optionMovie.isPresent()) {
			Movie movie = optionMovie.get();
			movie.setName(updateMovie.getName());
			movie.setDescription(updateMovie.getDescription());
			movie.setImageURL(updateMovie.getImageURL());
			movie.setYear(updateMovie.getYear());
			movieRepo.save(movie);
			return ResponseEntity.ok().body("Película "+movie.getId()+" actualizada correctamente.");
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	public ResponseEntity<String> insertIntoDB(Movie movie) {
		movie.setRating(0.0);
		movie.setTotalRating(0.0);
		Movie insertMovie = movieRepo.save(movie);
		if (!insertMovie.equals(null)) {
			try {
				return ResponseEntity.created(new URI("/"+movie.getId()))
						.body( "Película "+movie.getName()+" insertada con éxito");
			} catch (URISyntaxException e) {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
}





















