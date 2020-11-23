package br.com.btsoftware.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.repository.ProductRepository;

@Service
public class PhotoProductCatalog {
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional
	public PhotoProduct save(PhotoProduct photo) {
		
		Long restaurantId = photo.restaurantId();
		Long productId = photo.getProduct().getId();
		
		Optional<PhotoProduct> photoExist = productRepository
				.findFotoById(restaurantId, productId);
		
		if (photoExist.isPresent()) {
			productRepository.delete(photoExist.get());
		}
		
		return productRepository.save(photo);
	}
}
