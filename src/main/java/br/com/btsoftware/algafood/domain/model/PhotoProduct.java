package br.com.btsoftware.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_photo")
public class PhotoProduct {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "product_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Product product;
	
	@Column(name = "file_name")
	private String fileName;
	
	private String description;
	
	@Column(name = "content_type")
	private String contentType;
	
	private Long size;
	
	public Long restaurantId () {
		if (getProduct() != null) {
			return getProduct().getRestaurant().getId();
		}
		
		return null;
	}

}
