package dwsc.proyecto.client.comentarpelicula.dao;

import org.springframework.data.repository.CrudRepository;

import dwsc.proyecto.client.comentarpelicula.domain.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long>{

}
