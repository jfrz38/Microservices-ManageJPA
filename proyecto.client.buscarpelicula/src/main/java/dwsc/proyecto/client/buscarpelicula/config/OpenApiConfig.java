package dwsc.proyecto.client.buscarpelicula.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Buscar películas API").description("API para buscar una películas según diferentes parámetros")
						.contact(new Contact().name("José F.").url("https://github.com/jfrz38").email("jrz899@inlumine.ual.es"))
						.version("0.0.1"));
	}
}