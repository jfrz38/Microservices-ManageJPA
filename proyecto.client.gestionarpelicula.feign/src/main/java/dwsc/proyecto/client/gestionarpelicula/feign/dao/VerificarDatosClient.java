package dwsc.proyecto.client.gestionarpelicula.feign.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLIENT-VERIFICAR-DATOS")
public interface VerificarDatosClient {

	@GetMapping("/movie/{movieName}/{year}")
	ResponseEntity<String> getData(
			@PathVariable("movieName") String movieName,
			@PathVariable("year") int year);
}
