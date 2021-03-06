package br.com.btsoftware.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.exception.PhotoProductEntityNotExistException;
import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.repository.ProductRepository;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService.NewPhoto;

@Service
public class PhotoProductCatalogService {
	@Autowired
	private ProductRepository productRepository;
		
	@Autowired
	private PhotoStorageService  photoStorageService;
	
	@Transactional
	public PhotoProduct save(PhotoProduct photo, InputStream fileData) {
		
		Long restaurantId = photo.restaurantId();
		Long productId = photo.getProduct().getId();
		String newFileName = photoStorageService.generateFileName(photo.getFileName());
		String nameOldFile = null;
		
		Optional<PhotoProduct> photoExist = productRepository
				.findFotoById(restaurantId, productId);
		
		if (photoExist.isPresent()) {
			nameOldFile = photoExist.get().getFileName();
			productRepository.delete(photoExist.get());
		}
		
		photo.setFileName(newFileName);
		
		photo = productRepository.save(photo);
		productRepository.flush();
		
		
		NewPhoto newPhoto = NewPhoto.builder()
				.fileName(photo.getFileName())
				.contentType(photo.getContentType())
				.size(photo.getSize())
				.inputStream(fileData)
				.build();
				
		photoStorageService.replaceFile(nameOldFile, newPhoto);
		
		return photo;
	}
	
	public PhotoProduct findOrFail(Long restaurantId, Long productId) {
		return productRepository.findFotoById(restaurantId, productId)
				.orElseThrow(() -> new PhotoProductEntityNotExistException(restaurantId, productId));
	}
	
	@Transactional
	public void remove(Long restaurantId, Long productId) {
	    PhotoProduct photo = findOrFail(restaurantId, productId);
	    
	    productRepository.delete(photo);
	    productRepository.flush();

	    photoStorageService.remove(photo.getFileName());
	}
}
