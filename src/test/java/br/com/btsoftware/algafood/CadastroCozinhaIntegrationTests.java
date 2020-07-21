package br.com.btsoftware.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.btsoftware.algafood.domain.exception.EntityInUseException;
import br.com.btsoftware.algafood.domain.exception.KitchenEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.service.KitchenService;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private KitchenService kitchenService;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		// cenário
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName("Chinesa");

		// ação
		newKitchen = kitchenService.save(newKitchen);

		// validação
		assertThat(newKitchen).isNotNull();
		assertThat(newKitchen.getId()).isNotNull();
	}

	@Test()
	public void testarCadastroCozinhaSemNome() {
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName(null);

		assertThrows(ConstraintViolationException.class, () -> kitchenService.save(newKitchen));
	}
	
	@Test()
	public void deletarCozinhaEmUso() {
		
		assertThrows(EntityInUseException.class, () -> kitchenService.remove(1L));
	}
	
	@Test()
	public void deletarCozinhaInexistente() {
		
		assertThrows(KitchenEntityNotExistException.class, () -> kitchenService.remove(100L));
	}

}
