package dwsc.proyecto.UI.gestor.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLIENT-ELIMINAR-COMENTARIO")
public interface EliminarComentarioClient {

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long id);
}
