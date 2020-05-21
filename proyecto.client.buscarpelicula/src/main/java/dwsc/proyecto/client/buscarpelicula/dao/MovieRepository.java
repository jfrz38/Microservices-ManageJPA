package dwsc.proyecto.client.buscarpelicula.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dwsc.proyecto.client.buscarpelicula.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>{

	Optional<Movie> findByName(String name);
	
	@Query(value = "SELECT * FROM movie WHERE year = :year", nativeQuery=true)
	List<Movie> findAllByYear(@Param("year")int year);
	
	List<Movie> findAllByName(String name);
	
	@Query(value = "SELECT * FROM movie WHERE name LIKE %:name%", nativeQuery=true)
	List<Movie> nameContains(@Param("name") String name);
	
	@Query(value = "SELECT * FROM movie WHERE rating >= :rating", nativeQuery=true)
	List<Movie> queryByRatingHigh(@Param("rating") double rating);
	
	@Query(value = "SELECT * FROM movie WHERE rating <= :rating", nativeQuery=true)
	List<Movie> queryByRatingLow(@Param("rating") double rating);
	
	@Query(value="SELECT * FROM movie ORDER BY rating DESC LIMIT :limit", nativeQuery=true)
	List<Movie> queryBestMovies(@Param("limit") int limit);
	
	@Query(value="SELECT * FROM movie ORDER BY year DESC LIMIT :limit", nativeQuery=true)
	List<Movie> queryLastMovies(@Param("limit") int limit);
	
	@Query(value = "SELECT * FROM movie WHERE year >= :year", nativeQuery=true)
	List<Movie> queryByYearGreat(@Param("year") int year);
	
	@Query(value = "SELECT * FROM movie WHERE year <= :year", nativeQuery=true)
	List<Movie> queryByYearLow(@Param("year") int year);
}
