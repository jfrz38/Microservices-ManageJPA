package dwsc.proyecto.UI.usuario.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import dwsc.proyecto.UI.usuario.dao.BuscarPeliculaClient;
import dwsc.proyecto.UI.usuario.dao.ComentarPeliculaClient;
import dwsc.proyecto.UI.usuario.domain.Movie;
import dwsc.proyecto.UI.usuario.domain.Comment;

@Controller
public class UsuarioUiController {

	final static String gestionarPeliculasURL = "http://localhost:51334";
	final static String buscarPeliculasURL = "http://localhost:51290";

	@Autowired
	private BuscarPeliculaClient buscarPelicula;

	@Autowired
	private ComentarPeliculaClient comentarPelicula;

	@GetMapping("/")
	public String init(Map<String, List<Movie>> model) {
		// Best
		ResponseEntity<List<Movie>> responseBest = getBestMovies();
		if (responseBest.getStatusCodeValue() == 200) {
			model.put("bestMovies", responseBest.getBody());
		} else {
			// error
		}

		// Last
		ResponseEntity<List<Movie>> responseLast = getLastMovies();
		if (responseLast.getStatusCodeValue() == 200) {
			model.put("lastMovies", responseLast.getBody());

		} else {
			// error
		}

		return "usuarioUI";
	}

	@GetMapping("/moviesByName/{name}")
	public String searchMoviesByName(Map<String, Object> model, @PathVariable String name) {
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllByName(name);
			if (response.getStatusCodeValue() == 200) {
				model.put("result", response.getBody());
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha encontrado ninguna película");
				return "response :: responseFragment";
			}
		} catch (Exception e) {
			model.put("response", false);
			model.put("textResponse", "Ha ocurrido un error");
			return "response :: responseFragment";
		}

		return "results :: searchResult";
	}

	@GetMapping("/nameContains/{name}")
	public String searchMoviesByNameContains(Map<String, List<Movie>> model, @PathVariable String name) {
		ResponseEntity<List<Movie>> response = buscarPelicula.getByNameContains(name);
		model.put("result", response.getBody());
		return "results :: searchResult";
	}

	@GetMapping("/moviesByYear/{option}/{year}")
	public String searchMoviesByYear(Map<String, Object> model, @PathVariable("option") String option,
			@PathVariable int year) {
		try {
			ResponseEntity<List<Movie>> response = null;
			if (option.equals("high")) {
				response = buscarPelicula.greatYear(year);
			} else if (option.equals("low")) {
				response = buscarPelicula.lowYear(year);
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido realizar la búsqueda");
				return "response :: responseFragment";
			}

			if (response.getStatusCodeValue() == 200) {
				model.put("result", response.getBody());
				return "results :: searchResult";
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido realizar la búsqueda");
				return "response :: responseFragment";
			}
		} catch (Exception e) {
			model.put("response", false);
			model.put("textResponse", "No se ha podido realizar la búsqueda");
			return "response :: responseFragment";
		}

	}

	@GetMapping("/moviesByRate/{option}/{rating}")
	public String searchMoviesByRate(Map<String, Object> model, @PathVariable("option") String option,
			@PathVariable("rating") double rating) {
		
		try {
			ResponseEntity<List<Movie>> response = null;
			if (option.equals("high")) {
				response = buscarPelicula.getByHigherRating(rating);
			} else if (option.equals("low")) {
				response = buscarPelicula.getByLowerRating(rating);
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido realizar la búsqueda");
				return "response :: responseFragment";
			}
			
			if (response.getStatusCodeValue() == 200) {
				model.put("result", response.getBody());
				return "results :: searchResult";
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido realizar la búsqueda");
				return "response :: responseFragment";
			}
			
		}catch(Exception e) {
			model.put("response", false);
			model.put("textResponse", "No se ha podido realizar la búsqueda");
			return "response :: responseFragment";
		}
	}

	@GetMapping("/news")
	public String goToNews() {
		return "redirect:" + "http://localhost:8080/ProductorConsumidor/news";
	}

	public ResponseEntity<List<Movie>> getBestMovies() {
		return buscarPelicula.getBestMovies(5);
	}

	public ResponseEntity<List<Movie>> getLastMovies() {
		return buscarPelicula.getLastMovies(4);
	}

	@PostMapping("/comment/{id}")
	@JsonInclude(content=Include.NON_NULL)
	public String commentMovie(Map<String, Object> model, @RequestBody Comment comment, @PathVariable("id") long id) {
		
		comment.setDate(new Date());
		try {
			ResponseEntity<String> response = comentarPelicula.commentMovie(comment, id);

			if(response.getStatusCodeValue()==201) {
				model.put("response", true);
				model.put("textResponse", response.getBody());
				return "response :: responseFragment";
			}else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido añadir el comentario");
				return "response :: responseFragment";
			}
		} catch (Exception e) {
			model.put("response", false);
			model.put("textResponse", "No se ha podido añadir el comentario");
			return "response :: responseFragment";
		}

		
	}

	@GetMapping("/datosPelicula/{id}")
	public String dataMovie(Map<String, Object> model, @PathVariable("id") long id) {
		ResponseEntity<Movie> response = buscarPelicula.findById(id);
		try {
			if (response.getStatusCodeValue() == 200) {
				model.put("movie", response.getBody());
				return "consultarPelicula";
			} else {
				model.put("response", false);
				model.put("textResponse", "No se ha podido acceder a la película");
				return "response :: responseFragment";
			}

		} catch (Exception e) {
			model.put("response", false);
			model.put("textResponse", "No se ha podido acceder a la película");
			return "response :: responseFragment";
		}

	}

	@GetMapping("/error")
	public String setError(Map<String, Object> model) {
		model.put("response", false);
		model.put("textResponse", "Ha ocurrido un error");
		return "response :: responseFragment";
	}

	@GetMapping("/error/{error}")
	public String setSpecifiedError(Map<String, Object> model, @PathVariable("error") String error) {
		model.put("response", false);
		model.put("textResponse", "Ha ocurrido un error: " + error);
		return "response :: responseFragment";
	}

}
