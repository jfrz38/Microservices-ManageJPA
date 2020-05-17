package dwsc.proyecto.client.gestionarpelicula.feign.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dwsc.proyecto.client.gestionarpelicula.feign.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie,Long>{

	Optional<Movie> findByName(String name);
}
