package dwsc.proyecto.UI.gestor.dao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dwsc.proyecto.UI.gestor.domain.Movie;

@FeignClient("BUSCAR-PELICULA-APP")
public interface BuscarPeliculaClient {
	
	@GetMapping("/findAll")
	ResponseEntity<List<Movie>>getAllMovies();
	
	@GetMapping("/findById/{id}")
	ResponseEntity<Movie> findById(@PathVariable("id") long id);
	
}
