package br.com.btsoftware.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.ProductEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product save(Product product) {

		return productRepository.save(product);
	}

	public Product findOrFail(Long restaurantId, Long productId) {
		return productRepository.findByRestaurantAndProductId(restaurantId, productId)
				.orElseThrow(() -> new ProductEntityNotExistException(restaurantId, productId));
	}
	
	@Transactional
	public void activate(Long restaurantId , Long productId) {
		Product product = findOrFail(restaurantId, productId);
		
		product.activate();
	}
	
	@Transactional
	public void inactivate(Long restaurantId , Long productId) {
		Product product = findOrFail(restaurantId, productId);
		
		product.inactivate();
	}

}
