package br.com.btsoftware.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.btsoftware.algafood.domain.model.Kitchen;

@Controller
public class MeuPrimeiroController {
	
	@GetMapping("hello")
	@ResponseBody
	public String hello() {
		Kitchen kitchen = new Kitchen();
		kitchen.setName("Tailandesa");
		return "olá";
	}
}
