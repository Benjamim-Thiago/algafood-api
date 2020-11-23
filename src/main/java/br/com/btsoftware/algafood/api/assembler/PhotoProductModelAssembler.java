package br.com.btsoftware.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.PhotoProductModel;
import br.com.btsoftware.algafood.domain.model.PhotoProduct;

@Component
public class PhotoProductModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PhotoProductModel toModel(PhotoProduct photoProduct) {
		return modelMapper.map(photoProduct, PhotoProductModel.class);
	}
	
}
