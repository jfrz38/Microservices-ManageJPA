package dwsc.proyecto.client.verificardatos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VerificarDatosController {

	@Value("${apiURL}")
	private String apiURL;

	@Autowired
	RestTemplate template;

	@GetMapping("/movie/{movieName}")
	public ResponseEntity<String> getMovie(@PathVariable String movieName) 
			throws JsonProcessingException {

		String body = template.getForObject(apiURL + "&s=" + movieName, String.class);
		String response = new ObjectMapper().readTree(body).get("Response").asText();
		if (response.equalsIgnoreCase("True")) {
			return ResponseEntity.ok().header("Microservice", "A").body(body);
		} else {
			return ResponseEntity.noContent().header("Microservice", "A").build();
		}
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
