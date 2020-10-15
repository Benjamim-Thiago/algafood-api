package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.api.assembler.ProductModelAssembler;
import br.com.btsoftware.algafood.api.assembler.input.ProductInputDisassembler;
import br.com.btsoftware.algafood.api.model.ProductModel;
import br.com.btsoftware.algafood.api.model.input.ProductInput;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.model.Restaurant;
import br.com.btsoftware.algafood.domain.repository.ProductRepository;
import br.com.btsoftware.algafood.domain.service.ProductService;
import br.com.btsoftware.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private ProductModelAssembler productModelAssembler;

	@Autowired
	private ProductInputDisassembler productInputDisassembler;

	@GetMapping
	public List<ProductModel> list(@PathVariable Long restaurantId,
			@RequestParam(required = false) boolean includeInactives) {
		
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);

		List<Product> allProducts = null;

		if (includeInactives) {
			allProducts = productRepository.findAllProductsByRestaurant(restaurant);
		} else {
			allProducts = productRepository.findActivesProductsByRestaurant(restaurant);
		}

		return productModelAssembler.toCollectionModel(allProducts);
	}

	@GetMapping("/{productId}")
	public ProductModel find(@PathVariable Long restaurantId, @PathVariable Long productId) {
		Product product = productService.findOrFail(restaurantId, productId);

		return productModelAssembler.toModel(product);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel save(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);

		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);

		product = productService.save(product);

		return productModelAssembler.toModel(product);
	}

	@PutMapping("/{productId}")
	public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);

		Product currentProduct = productService.findOrFail(restaurant.getId(), productId);

		productInputDisassembler.copyToDomainObject(productInput, currentProduct);

		currentProduct = productService.save(currentProduct);

		return productModelAssembler.toModel(currentProduct);
	}

	@PutMapping("/{productId}/activate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId, @PathVariable Long productId) {
		productService.activate(restaurantId, productId);
	}

	@DeleteMapping("/{productId}/activate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@PathVariable Long restaurantId, @PathVariable Long productId) {
		productService.inactivate(restaurantId, productId);
	}
}
