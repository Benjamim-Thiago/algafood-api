package br.com.btsoftware.algafood.repository;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.ProductRepository;
import br.com.btsoftware.algafood.domain.repository.RestaurantRepository;

@SpringBootTest
public class ProductTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Test
	@Disabled
	public void saveTest() {
		Restaurant restaurant = restaurantRepository.findById(1L).get();

		Product product = new Product();
		product.setId(null);
		product.setName("Baião de Dois & file");
		product.setDescription("Baião de dois e filé alcatra");
		product.setPrice(new BigDecimal("32.49"));
		product.setActive(true);
		product.setRestaurant(restaurant);

		product = productRepository.save(product);

		if (product != null) {
			System.out.println(product.getId());
		}
	}
	
	@Test
	public void updateTest() {
		Restaurant restaurant = restaurantRepository.findById(2L).get();

		Product product = productRepository.findById(1L).get();
		
		product.setName("Baião de Dois & file mion");
		product.setDescription("Baião de dois e filé mion premium");
		product.setPrice(new BigDecimal("62.49"));
		product.setActive(true);
		product.setRestaurant(restaurant);

		product = productRepository.save(product);

		if (product != null) {
			System.out.println(product.getName());
		}
	}
}
