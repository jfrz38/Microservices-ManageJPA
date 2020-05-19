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

@RestController
public class FindMovieController {

	@Autowired
	MovieRepository movieRepo;
	
	@GetMapping(value="/findById/{id}")
	public ResponseEntity<Movie> findMovieById(@PathVariable("id") long id){
		Optional<Movie> movie = movieRepo.findById(id);
		if(movie.isPresent()) {
			return ResponseEntity.ok(movie.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value="/findOne/{name}")
	public ResponseEntity<Movie> findMovie(@PathVariable("name") String name){
		Optional<Movie> movie = movieRepo.findByName(name);
		if(movie.isPresent()) {
			return ResponseEntity.ok(movie.get());
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping(value="/findAllByName/{name}")
	public ResponseEntity<List<Movie>> findAllByName(@PathVariable("name") String name){
		return ResponseEntity.ok(movieRepo.findAllByName(name));
	}
	
	@GetMapping(value="/findAllByYear/{year}")
	public ResponseEntity<List<Movie>> findAllByYear(@PathVariable("year") int year){
		return ResponseEntity.ok(movieRepo.findAllByYear(year));
	}
	
	@GetMapping(value="/findAll")
	public ResponseEntity<Iterable<Movie>> findAll(){
		return ResponseEntity.ok(movieRepo.findAll());
	}
	
	@GetMapping(value="/find/{name}")
	public ResponseEntity<List<Movie>> findNameContains(@PathVariable("name")String name){
		return ResponseEntity.ok(movieRepo.nameContains(name));
	}
	
	@GetMapping(value="/highRate/{rating}")
	public ResponseEntity<List<Movie>> highRating(@PathVariable("rating")double rating){
		return ResponseEntity.ok(movieRepo.queryByRatingHigh(rating));
	}
	
	@GetMapping(value="/lowRate/{rating}")
	public ResponseEntity<List<Movie>> lowRating(@PathVariable("rating")double rating){
		return ResponseEntity.ok(movieRepo.queryByRatingLow(rating));
	}
	
	@GetMapping(value="/bestMovies/{limit}")
	public ResponseEntity<List<Movie>> bestMovies(@PathVariable("limit")int limit){
		return ResponseEntity.ok(movieRepo.queryBestMovies(limit));
	}
	
	@GetMapping(value="/lastMovies/{limit}")
	public ResponseEntity<List<Movie>> lastMovies(@PathVariable("limit")int limit){
		return ResponseEntity.ok(movieRepo.queryLastMovies(limit));
	}
}
