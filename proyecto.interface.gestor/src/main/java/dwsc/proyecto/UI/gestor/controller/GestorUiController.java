package dwsc.proyecto.UI.gestor.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import dwsc.proyecto.UI.gestor.dao.BuscarPeliculaClient;
import dwsc.proyecto.UI.gestor.domain.Comment;
import dwsc.proyecto.UI.gestor.domain.Movie;

@Controller
public class GestorUiController {

	@Autowired
	private EliminarComentarioClient eliminarComentario;
	
	@Autowired
	private GestionarPeliculaClient gestionarPelicula;
	
	@Autowired
	private BuscarPeliculaClient buscarPelicula;
	
	@GetMapping("/")
	public String init(Map<String, List<Movie>> model) {
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllMovies();
			if(response.getStatusCodeValue()==200) {
				model.put("result",response.getBody());
			}else {
				model.put("result",new ArrayList<Movie>());
			}
		}catch(Exception e) {
			model.put("result",new ArrayList<Movie>());
		}
		
		return "gestorUI";
	}
	
	@DeleteMapping("deleteComment/{id}")
	public String deleteComment(Map<String, Object> model, @PathVariable("id") Long id){
		try {
			ResponseEntity<String> response = eliminarComentario.deleteComment(id);
			if(response.getStatusCodeValue()==200) {
				model.put("textResponse", response.getBody()); //"Comentario eliminado con éxito");
			}else {
				model.put("textResponse", "No se ha podido eliminar el comentario");
			}
		}catch(Exception e) {
			model.put("textResponse", "No se ha podido eliminar el comentario");
		}
		
		
		return "response :: responseFragment";
	}
	
	@PostMapping("/insertMovie")
	public String insertMovie(Map<String,Object> model, @RequestBody Movie movie){

		movie.setComments(new HashSet<Comment>());
		movie.setRating(0);
		movie.setTotalRating(0);
		try {
			ResponseEntity<String> response = gestionarPelicula.insertMovie(movie);
			if(response.getStatusCodeValue()==201) {
				model.put("textResponse", response.getBody());
			}else {
				model.put("textResponse", "No se han podido verificar los datos de la película.");
			}
		}catch(Exception e) {
			model.put("textResponse", "No se ha podido añadir la película.");
		}
		return "response :: responseFragment";
	}
	
	@DeleteMapping("/deleteMovie/{id}")
	public String deleteMovie(Map<String,Object> model, @PathVariable("id") long id) {
		
		try {
			ResponseEntity<String> response = gestionarPelicula.deleteMovie(id);
			if(response.getStatusCodeValue()==200) {
				model.put("textResponse", response.getBody()); //"Película eliminada con éxito");
			}else {
				model.put("textResponse", "No se ha podido eliminar la película");
			}
			
		}catch(Exception e){
			model.put("textResponse", "No se ha podido eliminar la película");
		}
		return "response :: responseFragment";
	}
	
	@PutMapping("/updateMovie/{id}")
	public String updateMovie(Map<String,Object> model, @PathVariable("id") long id, @RequestBody Movie movie) {
		
		try {
			//Valores que no se actualizan manualmente
			movie.setComments(new HashSet<Comment>());
			movie.setRating(0);
			movie.setTotalRating(0);
			//Respuesta
			ResponseEntity<String> response = gestionarPelicula.updateMovie(movie, id);
			if(response.getStatusCodeValue()== 200) {
				model.put("textResponse", response.getBody());
			}else {
				model.put("textResponse", "No se ha podido actualizar la película");
			}
		}catch(Exception e){
			model.put("textResponse", "No se ha podido actualizar la película");
		}
		
		return "response :: responseFragment";
	}
	
	@GetMapping("/gestion/{id}")
	public String manageMovie(Map<String,Object> model, @PathVariable("id") long id){
		try {
			ResponseEntity<Movie> response = buscarPelicula.findById(id);
			if(response.getStatusCodeValue()==200) {
				model.put("movie",response.getBody());
				return "manageMovie";
			}else {
				model.put("textResponse", "No se ha podido encontrar la película");
				return "response :: responseFragment";
			}
			
		}catch(Exception e){
			model.put("textResponse", "Hubo un error al buscar la película");
			return "response :: responseFragment";
		}
		
	}
	
	@GetMapping("/createMovie")
	public String createMovie() {
		return "createMovie";
	}
	
	@GetMapping("/error")
	public String setError(Map<String, Object> model) {
		model.put("textResponse", "Ha ocurrido un error");
		return "response :: responseFragment";
	}

	@GetMapping("/error/{error}")
	public String setSpecifiedError(Map<String, Object> model, @PathVariable("error") String error) {
		model.put("textResponse", "Ha ocurrido un error: " + error);
		return "response :: responseFragment";
	}
	
}
