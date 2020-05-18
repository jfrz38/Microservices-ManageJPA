package dwsc.proyecto.UI.usuario.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import dwsc.proyecto.UI.usuario.domain.Comment;

@FeignClient("CLIENT-COMENTAR-PELICULA")
public interface ComentarPeliculaClient {

	@GetMapping("/insert/{movieID}")
	ResponseEntity<?> commentMovie(@RequestBody Comment comment, @PathVariable("movieID") Long id);
}
