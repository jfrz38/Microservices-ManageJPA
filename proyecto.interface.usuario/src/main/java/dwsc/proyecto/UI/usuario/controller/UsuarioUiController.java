package dwsc.proyecto.UI.usuario.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

	/*@GetMapping("/movies")
	public String getAllMovies(Map<String, List<Movie>> model) {
		List<Movie> movies = new ArrayList<Movie>();

		try {
			movies.addAll(buscarPelicula.getAllMovies().getBody());
		} catch (Exception e) {
			System.err.println("ERROR : " + e.getMessage());
		}
		System.out.println("id 1 = " + movies.get(0).getId());
		for (Movie m : movies) {
			System.out.println("movie = " + m.getName());
		}
		// TODO
		return "usuarioUI";
	}*/

	/*@GetMapping("/movieByName/{name}")
	public String searchMovieByName(Map<String, List<Movie>> model, @PathVariable String name) {
		// Movie movie = new Movie();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getByName(name);
			if (response.getStatusCodeValue() == 404) {
				// movie = null;
			} else {
				// movie = response.getBody();
				// System.out.println("Movie = "+movie.getName());
			}
		} catch (Exception e) {
			System.err.println("ERROR : " + e.getMessage());
		}

		return "usuarioUI";
	}*/

	@GetMapping("/moviesByName/{name}")
	public String searchMoviesByName(Map<String, List<Movie>> model, @PathVariable String name) {
		//List<Movie> movies = new ArrayList<Movie>();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllByName(name);
			if (response.getStatusCodeValue() == 404) {
				// movies = new ArrayList<Movie>();;
			} else {
				model.put("result",response.getBody());
			}
		} catch (Exception e) {
			System.err.println("ERROR : " + e.getMessage());
		}

		return "results :: searchResult";
	}

	@GetMapping("/nameContains/{name}")
	public String searchMoviesByNameContains(Map<String, List<Movie>> model, @PathVariable String name) {
		ResponseEntity<List<Movie>> response = buscarPelicula.getByNameContains(name);
		model.put("result",response.getBody());
		return "results :: searchResult";
	}

	@GetMapping("/moviesByYear/{year}")
	public String searchMoviesByYear(Map<String, List<Movie>> model, @PathVariable int year) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllByYear(year);
			if (response.getStatusCodeValue() == 404) {
				// movies = new ArrayList<Movie>();;
			} else {
				movies = response.getBody();
				for (Movie m : movies) {
					System.out.println("movie = " + m.getName());
				}
			}
			
			model.put("result",response.getBody());
		} catch (Exception e) {
			System.err.println("ERROR : " + e.getMessage());
		}

		
		return "results :: searchResult";
	}

	@GetMapping("/moviesByRate/{option}/{rating}")
	public String searchMoviesByRate(Map<String, List<Movie>> model, @PathVariable("option") String option,
			@PathVariable("rating") double rating) {
		ResponseEntity<List<Movie>> response = null;
		if (option == "high") {
			response = buscarPelicula.getByHigherRating(rating);
		} else if (option == "low") {
			response = buscarPelicula.getByLowerRating(rating);
		} else {
			// error
		}
		
		model.put("result",response.getBody());
		return "results :: searchResult";
	}

	@GetMapping("/news")
	public String goToNews() {

		// return "news";
		return "redirect:" + "http://localhost:8080/ProductorConsumidor/news";
	}

	public ResponseEntity<List<Movie>> getBestMovies() {
		return buscarPelicula.getBestMovies(5);
	}

	public ResponseEntity<List<Movie>> getLastMovies() {
		return buscarPelicula.getLastMovies(4);
	}


	@GetMapping("/guests")
	public String showGuestList(Map<String, List<Movie>> model) {
		System.out.println("Entra guests");
		
		//Importante: La key del mapa es result
	    model.put("result", buscarPelicula.getBestMovies(5).getBody());
	    System.out.println("Model = "+model.toString());
	    return "results :: searchResult";
	}
	
	@PostMapping("/comment/{id}")
	public String commentMovie(Map<String, Comment> model, @RequestBody Comment comment, @PathVariable("id") long id) {
		Comment a = model.get("comment");
		ResponseEntity<?> response = comentarPelicula.commentMovie(comment, id);
		
		return "usuarioUI";
	}
	
}
