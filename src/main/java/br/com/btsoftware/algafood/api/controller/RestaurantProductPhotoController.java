package br.com.btsoftware.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.btsoftware.algafood.api.assembler.PhotoProductModelAssembler;
import br.com.btsoftware.algafood.api.model.PhotoProductModel;
import br.com.btsoftware.algafood.api.model.input.PhotoProductInput;
import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.service.PhotoProductCatalog;
import br.com.btsoftware.algafood.domain.service.ProductService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {
	
	@Autowired
	private PhotoProductCatalog photoProductCatalog;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhotoProductModelAssembler photoProductModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public PhotoProductModel atualizarFoto(@PathVariable Long restaurantId, @PathVariable Long productId,
			@Valid PhotoProductInput photoProductInput) {

		Product product = productService.findOrFail(restaurantId, productId);

		MultipartFile file = photoProductInput.getFile();

		PhotoProduct photo = new PhotoProduct();
		photo.setProduct(product);
		photo.setDescription(photoProductInput.getDescription());
		photo.setContentType(file.getContentType());
		photo.setSize(file.getSize());
		photo.setFileName(file.getOriginalFilename());

		PhotoProduct photoSaved = photoProductCatalog.save(photo);

		return photoProductModelAssembler.toModel(photoSaved);
	}
}
