package dwsc.proyecto.client.verificardatos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Verificar datos", description = "Verificación de una película según título y año.")
public class VerificarDatosController {

	@Value("${apiURL}")
	private String apiURL;

	@Autowired
	RestTemplate template;

	@Operation(summary = "Verifica la película", description = "Operación para verificar una película")
	@ApiResponses({@ApiResponse(responseCode="200", description="Búsqueda y verificación correcta"),
		@ApiResponse(responseCode="204", description="Búsqueda correcta pero verificación incorrecta"),
		@ApiResponse(responseCode="404", description="Error al intentar verificar")})
	@GetMapping("/movie/{movieName}/{year}")
	public ResponseEntity<String> verifyMovie(
			@Parameter(description="Nombre de la película a verificar") @PathVariable("movieName") String movieName,
			@Parameter(description="Año de la película a verificar") @PathVariable("year")int year) {
		try {
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
		}catch(Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}