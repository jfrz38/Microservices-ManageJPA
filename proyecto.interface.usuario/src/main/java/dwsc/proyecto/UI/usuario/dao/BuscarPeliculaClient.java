package dwsc.proyecto.UI.usuario.dao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dwsc.proyecto.UI.usuario.domain.Movie;

@FeignClient("BUSCAR-PELICULA-APP")
public interface BuscarPeliculaClient {
	
	@GetMapping("/findAll")
	ResponseEntity<List<Movie>>getAllMovies();
	
	@GetMapping("/findAllByName/{name}")
	ResponseEntity<List<Movie>>getByName(@PathVariable("name") String name);
	
	@GetMapping("/findAllByName/{name}")
	ResponseEntity<List<Movie>>getAllByName(@PathVariable("name") String name);
	
	@GetMapping("/findAllByYear/{year}")
	ResponseEntity<List<Movie>>getAllByYear(@PathVariable("year") int year);
	
	@GetMapping("/find/{name}")
	ResponseEntity<List<Movie>>getByNameContains(@PathVariable("name") String name);
	
	@GetMapping("/highRate/{rating}")
	ResponseEntity<List<Movie>>getByHigherRating(@PathVariable("rating") double rating);
	
	@GetMapping("/lowRate/{rating}")
	ResponseEntity<List<Movie>>getByLowerRating(@PathVariable("rating") double rating);
	
	@GetMapping("/bestMovies/{limit}")
	ResponseEntity<List<Movie>> getBestMovies(@PathVariable("limit") int limit);
	
	@GetMapping("/lastMovies/{limit}")
	ResponseEntity<List<Movie>> getLastMovies(@PathVariable("limit") int limit);
}
