package dwsc.proyecto.client.gestionarpelicula.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dwsc.proyecto.client.gestionarpelicula.feign.dao.BuscarPeliculaClient;

@RestController
public class GestionarPeliculaController {
	@Autowired
	private BuscarPeliculaClient bp;

	@GetMapping("/movie/{name}")
	public String checkMovie(@PathVariable String name) throws Exception {

		ResponseEntity<String> response = bp.getData(name);
		if (!response.getHeaders().containsKey("Microservice"))
			throw new Exception("Respuesta sin cabecera del microservicio");
		String microservice = response.getHeaders().get("Microservice").get(0);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return "El microservicio " + microservice + " ha encontrado "
					+ "al menos una película: \n" + response.getBody();
		} else if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)){
			return "El microservicio " + microservice + " NO ha encontrado "
					+ "ninguna película para '" + name + "'.";
		}else {
			return "Código "+response.getStatusCodeValue()+": Error desconocido";
		}
	}
}
