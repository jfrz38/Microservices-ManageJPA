package dwsc.proyecto.client.buscarpelicula.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dwsc.proyecto.client.buscarpelicula.dao.MovieRepository;
import dwsc.proyecto.client.buscarpelicula.domain.Movie;

@RestController
public class MovieController {

	@Autowired
	MovieRepository movieRepo;
	
	@RequestMapping(value="/find/{name}", produces= {MediaType.APPLICATION_JSON_VALUE}, method= RequestMethod.GET)
	public ResponseEntity<Movie> findMovie(@PathVariable String name){
		Optional<Movie> movie = movieRepo.findByName(name);
		if(movie.isPresent()) {
			return ResponseEntity.ok(movie.get());
		}else {
			return ResponseEntity.noContent().build();
		}
	}
}
