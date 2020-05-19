package dwsc.proyecto.UI.gestor.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		ResponseEntity<List<Movie>> response = buscarPelicula.getAllMovies();
		if(response.getStatusCodeValue()==200) {
			model.put("result",response.getBody());
		}else {
			// no se cargan resultados
		}
		
		return "gestorUI";
	}
	
	@DeleteMapping("deleteComment/{id}")
	public String deleteComment(Map<String, Object> model, @PathVariable("id") Long id){
		
		ResponseEntity<?> response = eliminarComentario.deleteComment(id);
		
		if(response.getStatusCodeValue()==200) {
			model.put("response",true);
			model.put("textResponse", "Comentario eliminado con éxito");
		}else {
			model.put("response",false);
			model.put("textResponse", "No se ha podido eliminar el comentario");
		}
		
		return "response :: responseFragment";
	}
	
	@PostMapping("/insertMovie")
	public String insertMovie(Map<String,Object> model, @RequestBody Movie movie){

		movie.setComments(new HashSet<Comment>());
		movie.setRating(0);
		movie.setTotalRating(0);
		movie.setImageURL("");
		
		try {
			ResponseEntity<Movie> response = gestionarPelicula.insertMovie(movie);
			model.put("response",true);
			model.put("textResponse", "Película "+movie.getName()+" insertada con éxito");
		}catch(Exception e) {
			//404
			model.put("response",false);
			model.put("textResponse", "No se ha podido insertar la película.");
		}
		
		return "response :: responseFragment";
	}
	
	@DeleteMapping("/deleteMovie/{id}")
	public String deleteMovie(Map<String,Object> model, @PathVariable("id") long id) {
		
		try {
			ResponseEntity<?> response = gestionarPelicula.deleteMovie(id);
			model.put("response",true);
			model.put("textResponse", "Película eliminada con éxito");
		}catch(Exception e){
			model.put("response",true);
			model.put("textResponse", "No se ha podido eliminar la película");
		}
		return "response :: responseFragment";
	}
	
	@PutMapping("/updateMovie/{id}")
	public String updateMovie(Map<String,Object> model, @PathVariable("id") long id, @RequestBody Movie movie) {
		
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
			if(response.getStatusCodeValue()== 200) {
				model.put("response",true);
				model.put("textResponse", "Película "+id+" actualizada con éxito");
			}else {
				model.put("response",false);
				model.put("textResponse", "No se ha podido actualizar la película");
			}
		}catch(Exception e){
			model.put("response",false);
			model.put("textResponse", "No se ha podido actualizar la película");
		}
		
		return "response :: responseFragment";
	}
	
	@GetMapping("/createMovie")
	public String createMovie() {
		//Acceder a la ventana con opciones
		//para crear una película.
		return "createMovie";
	}
	
	@GetMapping("/gestion/{id}")
	public String manageMovie(Map<String,Object> model, @PathVariable("id") long id){
		System.out.println("id manageMovie = "+id);
		try {
			ResponseEntity<Movie> response = buscarPelicula.findById(1);
			if(response.getStatusCodeValue()==200) {
				model.put("movie",response.getBody());
				return "manageMovie";
			}else {
				model.put("response",false);
				model.put("textResponse", "No se ha podido encontrar la película");
				return "response :: responseFragment";
			}
			
		}catch(Exception e){
			System.out.println("exception = "+e.getMessage());
			model.put("response",false);
			model.put("textResponse", "Hubo un error al buscar la película");
			return "response :: responseFragment";
		}
		
	}
	
}
