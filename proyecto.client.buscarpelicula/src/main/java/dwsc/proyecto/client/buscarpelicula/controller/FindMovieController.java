package dwsc.proyecto.client.buscarpelicula.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dwsc.proyecto.client.buscarpelicula.dao.MovieRepository;
import dwsc.proyecto.client.buscarpelicula.domain.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Buscar película", description = "Devuelve las películas que cumpla las condiciones de búsqueda.")
public class FindMovieController {

	@Autowired
	MovieRepository movieRepo;
	
	@Operation(summary = "Busca una película por ID", description = "Operación para buscar y devolver una película según el ID de la misma")
	@ApiResponses({@ApiResponse(responseCode="200", description="Película encontrada"),
		@ApiResponse(responseCode="404", description="Película no encontrada")})
	@GetMapping(value="/findById/{id}")
	public ResponseEntity<Movie> findMovieById(@Parameter(description="ID de la película a buscar") @PathVariable("id") long id){
		Optional<Movie> movie = movieRepo.findById(id);
		if(movie.isPresent()) {
			return ResponseEntity.ok(movie.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Operation(summary = "Busca una película por nombre", description = "Operación para buscar y devolver una película según el nombre de la misma")
	@ApiResponses({@ApiResponse(responseCode="200", description="Película encontrada"),
		@ApiResponse(responseCode="404", description="Película no encontrada")})
	@GetMapping(value="/findOne/{name}")
	public ResponseEntity<Movie> findMovie(@Parameter(description="Nombre de la película a buscar") @PathVariable("name") String name){
		Optional<Movie> movie = movieRepo.findByName(name);
		if(movie.isPresent()) {
			return ResponseEntity.ok(movie.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Operation(summary = "Busca todas las películas por nombre", description = "Operación para buscar y devolver todas las películas con un nombre concreto")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/findAllByName/{name}")
	public ResponseEntity<List<Movie>> findAllByName(@Parameter(description="Nombre de la película a buscar") @PathVariable("name") String name){
		return ResponseEntity.ok(movieRepo.findAllByName(name));
	}
	
	@Operation(summary = "Busca todas las películas por año", description = "Operación para buscar y devolver todas las películas con un año concreto")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/findAllByYear/{year}")
	public ResponseEntity<List<Movie>> findAllByYear(@Parameter(description="Año de la película a buscar") @PathVariable("year") int year){
		return ResponseEntity.ok(movieRepo.findAllByYear(year));
	}
	
	@Operation(summary = "Busca todas las películas", description = "Operación para buscar y devolver todas las películas que existen en la base de datos")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/findAll")
	public ResponseEntity<Iterable<Movie>> findAll(){
		return ResponseEntity.ok(movieRepo.findAll());
	}
	
	@Operation(summary = "Busca todas las películas que contengan el nombre", description = "Operación para buscar y devolver todas las películas cuyo título contengan un texto en concreto")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/find/{name}")
	public ResponseEntity<List<Movie>> findNameContains(@Parameter(description="Texto existente en el título de la película a buscar") @PathVariable("name")String name){
		return ResponseEntity.ok(movieRepo.nameContains(name));
	}
	
	@Operation(summary = "Busca todas las películas según el rating mayor", description = "Operación para buscar y devolver todas las películas cuyo rating sea mayor o igual a uno determinado")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/highRate/{rating}")
	public ResponseEntity<List<Movie>> highRating(@Parameter(description="Rating mínimo de la película a buscar") @PathVariable("rating")double rating){
		return ResponseEntity.ok(movieRepo.queryByRatingHigh(rating));
	}
	
	@Operation(summary = "Busca todas las películas según el rating menor", description = "Operación para buscar y devolver todas las películas cuyo rating sea menor o igual a uno determinado")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/lowRate/{rating}")
	public ResponseEntity<List<Movie>> lowRating(@Parameter(description="Rating máximo de la película a buscar") @PathVariable("rating")double rating){
		return ResponseEntity.ok(movieRepo.queryByRatingLow(rating));
	}
	
	@Operation(summary = "Busca un número concreto de películas según el mejor rating", description = "Operación para buscar y devolver un determinado número de las mejores películas ordenadas por rating descendente")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/bestMovies/{limit}")
	public ResponseEntity<List<Movie>> bestMovies(@Parameter(description="Número máximo de resultados a devolver") @PathVariable("limit")int limit){
		return ResponseEntity.ok(movieRepo.queryBestMovies(limit));
	}
	
	@Operation(summary = "Busca un número concreto de películas según la fecha", description = "Operación para buscar y devolver un determinado número de las últimas películas ordenadas por fecha de publicación descendente")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/lastMovies/{limit}")
	public ResponseEntity<List<Movie>> lastMovies(@Parameter(description="Número máximo de resultados a devolver") @PathVariable("limit")int limit){
		return ResponseEntity.ok(movieRepo.queryLastMovies(limit));
	}
	
	@Operation(summary = "Busca todas las películas según el año mayor", description = "Operación para buscar y devolver todas las películas cuyo año sea mayor o igual a uno determinado")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/yearGreat/{year}")
	public ResponseEntity<List<Movie>> greatYear(@Parameter(description="Año máximo de la película a buscar") @PathVariable("year") int year){
		return ResponseEntity.ok(movieRepo.queryByYearGreat(year));
	}
	
	@Operation(summary = "Busca todas las películas según el año menor", description = "Operación para buscar y devolver todas las películas cuyo año sea menor o igual a uno determinado")
	@ApiResponse(responseCode="200", description="Búsqueda correcta")
	@GetMapping(value="/yearLow/{year}")
	public ResponseEntity<List<Movie>> lowYear(@Parameter(description="Año mínimo de la película a buscar") @PathVariable("year") int year){
		return ResponseEntity.ok(movieRepo.queryByYearLow(year));
	}
}
