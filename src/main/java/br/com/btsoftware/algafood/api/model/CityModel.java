package br.com.btsoftware.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityModel extends RepresentationModel<CityModel> {
	private Long id;
	private String name;
	private StateModel state;
}
