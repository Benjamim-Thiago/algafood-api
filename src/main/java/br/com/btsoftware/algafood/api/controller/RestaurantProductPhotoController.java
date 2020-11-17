package br.com.btsoftware.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.model.input.PhotoProductInput;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid PhotoProductInput photoProductInput) {
		
		var nameFile = UUID.randomUUID().toString() 
				+ "_" + photoProductInput.getFile().getOriginalFilename();
		
		var filePhoto = Path.of("C:\\Users\\BEN\\Documents\\estudo\\java\\algafood\\teste_foto", nameFile);
		
		System.out.println(photoProductInput.getDescription());
		System.out.println(filePhoto);
		System.out.println(photoProductInput.getFile().getContentType());
		
		try {
			photoProductInput.getFile().transferTo(filePhoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
