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

@RestController
public class GestionarPeliculaController {

	@Autowired
	private VerificarDatosClient verificarDatos;

	@Autowired
	private MovieRepository movieRepo;

	@PostMapping("/insert")
	public ResponseEntity<String> insertMovie(@RequestBody Movie movie) throws Exception {
		try {
			ResponseEntity<String> response = verificarDatos.getData(movie.getName(),movie.getYear());//.getData(movie);//(movie.getName());
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

	public ResponseEntity<String> insertIntoDB(Movie movie) {
		movie.setRating(0.0);
		movie.setTotalRating(0.0);
		Movie insertMovie = movieRepo.save(movie);
		if (!insertMovie.equals(null)) {
			try {
				return ResponseEntity.created(new URI("/"+movie.getId())).body( "Película "+movie.getName()+" insertada con éxito");
			} catch (URISyntaxException e) {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id){
		if(!movieRepo.findById(id).isPresent()) return ResponseEntity.notFound().build();
		movieRepo.deleteById(id);
		//return ResponseEntity.noContent().build();
		return ResponseEntity.ok("Película "+id+" eliminada correctamente");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateMovie(@RequestBody Movie updateMovie, @PathVariable("id") Long id){
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
}
