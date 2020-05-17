package dwsc.proyecto.UI.gestor.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dwsc.proyecto.UI.gestor.domain.Movie;



@FeignClient("CLIENT-GESTIONARPELICULAS-FEIGN")
public interface GestionarPeliculaClient {

	@PostMapping("/insert")
	public ResponseEntity<Movie> insertMovie(@RequestBody Movie movie);
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id);
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie updateMovie, @PathVariable("id") Long id);
}
