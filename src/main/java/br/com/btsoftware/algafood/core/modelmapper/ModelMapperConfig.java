package br.com.btsoftware.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.btsoftware.algafood.api.model.AddressModel;
import br.com.btsoftware.algafood.domain.model.Address;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var addressToAddressModelTypeMap = modelMapper.createTypeMap(
				Address.class, AddressModel.class);
		
		addressToAddressModelTypeMap.<String>addMapping(
				addressSrc -> addressSrc.getCity().getState().getName(),
				(addressModelDest, value) -> addressModelDest.getCity().setState(value));
		
		return modelMapper;
	}
}
