package dwsc.proyecto.UI.usuario.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dwsc.proyecto.UI.usuario.dao.BuscarPeliculaClient;
import dwsc.proyecto.UI.usuario.dao.ComentarPeliculaClient;
import dwsc.proyecto.UI.usuario.domain.Movie;

@Controller
public class UsuarioUiController {

	final static String gestionarPeliculasURL = "http://localhost:51334";
	final static String buscarPeliculasURL = "http://localhost:51290";
	
	@Autowired
	private BuscarPeliculaClient buscarPelicula;
	
	@Autowired
	private ComentarPeliculaClient comentarPelicula;
	
	@GetMapping("/")
	public String init(Map<String, String> model) {
		//Insertar aquí las noticias de CORBA
		System.out.println("model = "+model);
		model.put("name", "ey");
		return "usuarioUI";
	}
	
	@GetMapping("/movies")
	public String getAllMovies(Map<String, String> model) {
		List<Movie> movies = new ArrayList<Movie>();
		
		try {
			movies.addAll(buscarPelicula.getAllMovies().getBody());
		}catch(Exception e) {
			System.err.println("ERROR : "+e.getMessage());
		}
		System.out.println("id 1 = "+movies.get(0).getId());
		for(Movie m : movies) {
			System.out.println("movie = "+m.getName());
		}
		//TODO
		return "usuarioUI";
	}
	
	@GetMapping("/movieByName/{name}")
	public String searchMovieByName(Map<String, String> model,@PathVariable String name) {
		//Movie movie = new Movie();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getByName(name);
			if(response.getStatusCodeValue()==404) {
				//movie = null;
			}else {
				//movie = response.getBody();
				//System.out.println("Movie = "+movie.getName());
			}
		}catch(Exception e) {
			System.err.println("ERROR : "+e.getMessage());
		}
		
		return "usuarioUI";
	}

	@GetMapping("/moviesByName/{name}")
	public String searchMoviesByName(Map<String, String> model,@PathVariable String name) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllByName(name);
			if(response.getStatusCodeValue()==404) {
				//movies = new ArrayList<Movie>();;
			}else {
				movies = response.getBody();
				for(Movie m : movies) {
					System.out.println("movie = "+m.getName());
				}
			}
		}catch(Exception e) {
			System.err.println("ERROR : "+e.getMessage());
		}
		
		return "usuarioUI";
	}
	
	@GetMapping("/nameContains/{name}")
	public String searchMoviesByNameContains(Map<String, String> model,@PathVariable String name) {
		ResponseEntity<List<Movie>> response = buscarPelicula.getByNameContains(name);
		return "usuarioUI";
	}

	@GetMapping("/moviesByYear/{year}")
	public String searchMoviesByYear(Map<String, String> model,@PathVariable int year) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			ResponseEntity<List<Movie>> response = buscarPelicula.getAllByYear(year);
			if(response.getStatusCodeValue()==404) {
				//movies = new ArrayList<Movie>();;
			}else {
				movies = response.getBody();
				for(Movie m : movies) {
					System.out.println("movie = "+m.getName());
				}
			}
		}catch(Exception e) {
			System.err.println("ERROR : "+e.getMessage());
		}
		
		return "usuarioUI";
	}
	
	@GetMapping("/moviesByRate/{option}/{rating}")
	public String searchMoviesByRate(Map<String, String> model,@PathVariable("option") String option, @PathVariable("rating") double rating) {
		
		if(option=="high") {
			buscarPelicula.getByHigherRating(rating);
		}else if (option == "low") {
			buscarPelicula.getByLowerRating(rating);
		}else {
			//error
		}
		
		return "usuarioUI";
	}
}
