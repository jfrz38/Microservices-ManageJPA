package dwsc.proyecto.UI.administrador.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministradorUiController {

	@GetMapping("/")
	public String init(Map<String, String> model) {
		return "redirect:" + "http://localhost:8080/ProductorConsumidor";
	}
}
