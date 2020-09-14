package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.ProductInput;
import br.com.btsoftware.algafood.domain.model.Product;

@Component
public class ProductInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;

	public Product toDomainObject(ProductInput productInput) {
		return modelMapper.map(productInput, Product.class);
	}

	public void copyToDomainObject(ProductInput productInput, Product product) {
		modelMapper.map(productInput, product);
	}
}
