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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@RestController
public class VerificarDatosController {

	@Value("${apiURL}")
	private String apiURL;

	@Autowired
	RestTemplate template;

	@GetMapping("/movie/{movieName}/{year}")
	public ResponseEntity<String> getMovie(@PathVariable("movieName") String movieName,
			@PathVariable("year")int year) 
			throws JsonProcessingException {
		String body = template.getForObject(apiURL + "&s=" + movieName, String.class);
		String response = new ObjectMapper().readTree(body).get("Response").asText();
		if (response.equalsIgnoreCase("True")) {
			//Comprobar año y título
			ArrayNode results = (ArrayNode) new ObjectMapper().readTree(body).get("Search");
			for(JsonNode node : results) {
				if((node.get("Title").asText().equalsIgnoreCase(movieName)) &&
						(node.get("Year").asInt()==year)) {
					return ResponseEntity.ok().body(node.get("Poster").asText());
				}
			}
		}
		
		return ResponseEntity.noContent().build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
