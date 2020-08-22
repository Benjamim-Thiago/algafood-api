package br.com.btsoftware.algafood.api.assembler.input;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.btsoftware.algafood.api.model.input.CityInput;
import br.com.btsoftware.algafood.domain.model.City;
import br.com.btsoftware.algafood.domain.model.State;

@Component
public class CityInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}
	
	public void copyToDomainObject(CityInput cityInput, City city) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.btsoftware.algafood.domain.model.State was altered from 1 to 2
		city.setState(new State());
		
		modelMapper.map(cityInput, city);
	}
}
