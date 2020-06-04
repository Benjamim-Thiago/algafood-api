package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "request_items")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestItem {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="request_id",nullable = false)
	private Request request;
	
	@ManyToOne
    @JoinColumn(name="product_id",nullable = false)
	private Product product;
	
	private Integer amount;
	
	private BigDecimal price;
	
	@Column(name = "total")
	private BigDecimal priceTotal;
	
	private String comment;
	
}
