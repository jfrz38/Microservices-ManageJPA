package dwsc.proyecto.client.comentarpelicula.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dwsc.proyecto.client.comentarpelicula.domain.Movie;


public interface MovieRepository extends CrudRepository<Movie, Long>{

	Optional<Movie> findByName(String name);
	
	List<Movie> findAllByYear(String year);
	
	List<Movie> findAllByName(String name);
	
}
