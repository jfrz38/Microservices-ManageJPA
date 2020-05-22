package dwsc.proyecto.client.gestionarpelicula.feign.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Movie> insertMovie(@RequestBody Movie movie) throws Exception {
		try {
			ResponseEntity<String> response = verificarDatos.getData(movie.getName(),movie.getYear());//.getData(movie);//(movie.getName());
			if (response.getStatusCodeValue() == 200) {
				if(movie.getImageURL().equals(""))movie.setImageURL(response.getBody());
				return insertIntoDB(movie);
			} else {
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}

	public ResponseEntity<Movie> insertIntoDB(Movie movie) {
		movie.setRating(0.0);
		movie.setTotalRating(0.0);
		Movie insertMovie = movieRepo.save(movie);
		if (!insertMovie.equals(null)) {
			return ResponseEntity.ok(insertMovie);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id){
		if(!movieRepo.findById(id).isPresent()) return ResponseEntity.notFound().build();
		movieRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie updateMovie, @PathVariable("id") Long id){
		Optional<Movie> optionMovie = movieRepo.findById(id);
		if(optionMovie.isPresent()) {
			Movie movie = optionMovie.get();
			/*if(!updateMovie.getName().equals(""))movie.setName(updateMovie.getName());
			if(!updateMovie.getDescription().equals(""))movie.setDescription(updateMovie.getDescription());
			if(!updateMovie.getImageURL().equals(""))movie.setImageURL(updateMovie.getImageURL());
			if(updateMovie.getYear()!=0)movie.setYear(updateMovie.getYear());*/
			movie.setName(updateMovie.getName());
			movie.setDescription(updateMovie.getDescription());
			movie.setImageURL(updateMovie.getImageURL());
			movie.setYear(updateMovie.getYear());
			movieRepo.save(movie);
			return ResponseEntity.ok(movie);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
}
