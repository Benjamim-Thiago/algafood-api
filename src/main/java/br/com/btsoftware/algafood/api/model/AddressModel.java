package br.com.btsoftware.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {
	
	private String zipcode;
	
	private String publicArea;
	
	private String number;
	
	private String complement;
	
	private String neighborhood;
	
	private CityResumeModel city;
}
