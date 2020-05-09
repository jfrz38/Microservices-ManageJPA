package dwsc.proyecto.client.gestionarpelicula.feign.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLIENT-BUSCAR-PELICULAS")
public interface BuscarPeliculaClient {

	@GetMapping("/movie/{name}")
	ResponseEntity<String> getData(@PathVariable String name);
}
