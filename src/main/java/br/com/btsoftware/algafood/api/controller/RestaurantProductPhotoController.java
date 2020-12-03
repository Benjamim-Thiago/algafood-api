package br.com.btsoftware.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.btsoftware.algafood.api.assembler.PhotoProductModelAssembler;
import br.com.btsoftware.algafood.api.model.PhotoProductModel;
import br.com.btsoftware.algafood.api.model.input.PhotoProductInput;
import br.com.btsoftware.algafood.domain.exception.EntityNotExistException;
import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.service.PhotoProductCatalogService;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService;
import br.com.btsoftware.algafood.domain.service.PhotoStorageService.ToRecoverPhoto;
import br.com.btsoftware.algafood.domain.service.ProductService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private PhotoStorageService photoStorageService;
	
	@Autowired
	private PhotoProductCatalogService photoProductCatalogService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhotoProductModelAssembler photoProductModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public PhotoProductModel sendFoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid PhotoProductInput photoProductInput) throws IOException {

		Product product = productService.findOrFail(restaurantId, productId);

		MultipartFile file = photoProductInput.getFile();

		PhotoProduct photo = new PhotoProduct();
		photo.setProduct(product);
		photo.setDescription(photoProductInput.getDescription());
		photo.setContentType(file.getContentType());
		photo.setSize(file.getSize());
		photo.setFileName(file.getOriginalFilename());

		PhotoProduct photoSaved = photoProductCatalogService.save(photo, file.getInputStream());

		return photoProductModelAssembler.toModel(photoSaved);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PhotoProductModel find(@PathVariable Long restaurantId, @PathVariable Long productId) {
		PhotoProduct photo = photoProductCatalogService.findOrFail(restaurantId, productId);
		
		return photoProductModelAssembler.toModel(photo);
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> findToPhoto(@PathVariable Long restaurantId, 
			@PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
			
			PhotoProduct photo = photoProductCatalogService.findOrFail(restaurantId, productId);
			
			MediaType mediaTypePhoto = MediaType.parseMediaType(photo.getContentType());
			List<MediaType> mediaTypesAccepts = MediaType.parseMediaTypes(acceptHeader);
			
			verifyCompatibleMediaType(mediaTypePhoto, mediaTypesAccepts);
			
			ToRecoverPhoto toRecoverPhoto = photoStorageService.recover(photo.getFileName());
			
			if (toRecoverPhoto.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, toRecoverPhoto.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(mediaTypePhoto)
						.body(new InputStreamResource(toRecoverPhoto.getInputStream()));
			}
			
		} catch (EntityNotExistException e) {
			return ResponseEntity.notFound().build();
		} 
		
	}
	
	private void verifyCompatibleMediaType(MediaType mediaTypePhoto, 
			List<MediaType> mediaTypesAccepts) throws HttpMediaTypeNotAcceptableException {
		
		boolean compatible = mediaTypesAccepts.stream()
				.anyMatch(mediaTypeAccept -> mediaTypeAccept.isCompatibleWith(mediaTypePhoto));
		
		if (!compatible) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepts);
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long restaurantId, @PathVariable Long productId) {
		photoProductCatalogService.remove(restaurantId, productId);			
	}
	
	
}
