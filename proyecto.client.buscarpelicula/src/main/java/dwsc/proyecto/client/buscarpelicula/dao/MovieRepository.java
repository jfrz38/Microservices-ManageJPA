package dwsc.proyecto.client.buscarpelicula.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dwsc.proyecto.client.buscarpelicula.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>{

	Optional<Movie> findByName(String name);
}
