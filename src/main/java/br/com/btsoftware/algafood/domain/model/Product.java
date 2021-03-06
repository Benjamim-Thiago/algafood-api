package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade Produto
 */
@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

    private BigDecimal price;
    
    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @JsonIgnore
	@JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;     
    
    public void activate() {
		setActive(true);
	}
	
	public void inactivate() {
		setActive(false);
	}
}
