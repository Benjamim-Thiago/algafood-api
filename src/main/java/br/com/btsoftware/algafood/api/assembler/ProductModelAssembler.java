package br.com.btsoftware.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.ProductModel;
import br.com.btsoftware.algafood.domain.model.Product;

@Component
public class ProductModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProductModel toModel(Product product) {
		return modelMapper.map(product, ProductModel.class);
	}
	
	public List<ProductModel> toCollectionModel(List<Product> products) {
		return products.stream()
				.map(product -> toModel(product))
				.collect(Collectors.toList());
	}
}
