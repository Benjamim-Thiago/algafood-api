package br.com.btsoftware.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {
	@Column(name = "address_zipcode")
	private String zipcode;
	
	@Column(name = "address_public_area")
	private String publicArea;
	
	@Column(name = "address_number")
	private String number;
	
	@Column(name = "address_complement")
	private String complement;
	
	@Column(name = "address_neighborhood")
	private String neighborhood;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_city_id")
	private City city;
}
