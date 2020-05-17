package dwsc.proyecto.client.eliminarcomentario.dao;

import org.springframework.data.repository.CrudRepository;

import dwsc.proyecto.client.eliminarcomentario.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

}
