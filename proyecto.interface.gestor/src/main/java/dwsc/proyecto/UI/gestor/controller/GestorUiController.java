package dwsc.proyecto.UI.gestor.controller;

import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dwsc.proyecto.UI.gestor.dao.EliminarComentarioClient;
import dwsc.proyecto.UI.gestor.dao.GestionarPeliculaClient;
import dwsc.proyecto.UI.gestor.domain.Comment;
import dwsc.proyecto.UI.gestor.domain.Movie;


@Controller
public class GestorUiController {

	@Autowired
	private EliminarComentarioClient eliminarComentario;
	
	@Autowired
	private GestionarPeliculaClient gestionarPelicula;
	
	@GetMapping("/")
	public String init(Map<String, String> model) {
		System.out.println("model = "+model);
		model.put("name", "ey");
		return "gestorUI";
	}
	
	@DeleteMapping("deleteComment/{id}")
	public String deleteComment(Map<String, String> model, @PathVariable("id") Long id){
		
		ResponseEntity<?> response = eliminarComentario.deleteComment(id);
		
		if(response.getStatusCodeValue()==200) {
			
		}else {
			
		}
		
		return "gestorUI";
	}
	
	@PostMapping("/insertMovie")
	public String insertMovie(@RequestBody Movie movie){
		
		movie.setComments(new HashSet<Comment>());
		movie.setRating(0);
		movie.setTotalRating(0);
		movie.setImageURL("");
		
		try {
			ResponseEntity<Movie> response = gestionarPelicula.insertMovie(movie);
		}catch(Exception e) {
			//404
		}
		
				
		return "gestorUI";
	}
	
	@DeleteMapping("/deleteMovie/{id}")
	public String deleteMovie(@PathVariable("id") long id) {
		ResponseEntity<?> response = gestionarPelicula.deleteMovie(id);
		return "gestorUI";
	}
	
	@PutMapping("/updateMovie/{id}")
	public String updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
		
		//Valores no modificables: Se inician pero no se actualizan.
		try {
			//Valores que no se editan si no se han introducido en el cuerpo
			if(movie.getName() == null) movie.setName("");
			if(movie.getDescription() == null) movie.setDescription("");
			if(movie.getImageURL()==null) movie.setImageURL("");
			//Valores que no se actualizan nunca
			movie.setComments(new HashSet<Comment>());
			movie.setRating(0);
			movie.setTotalRating(0);
			//Respuesta
			ResponseEntity<Movie> response = gestionarPelicula.updateMovie(movie, id);
			
		}catch(Exception e){
			
		}
		
		return "gestorUI";
	}
	
}
