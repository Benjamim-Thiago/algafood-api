package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="request_id",nullable = false)
	@JsonIgnore
	private Request request;
	
	@ManyToOne
    @JoinColumn(name="product_id",nullable = false)
	private Product product;
	
	private Integer amount;
	
	private BigDecimal price;
	
	@Column(name = "total")
	private BigDecimal priceTotal;
	
	private String comment;
	
	public void calculatePriceTotal() {
	    BigDecimal priceUnit = this.getPrice();
	    Integer amount = this.getAmount();

	    if (priceUnit == null) {
	    	priceUnit = BigDecimal.ZERO;
	    }

	    if (amount == null) {
	        amount = 0;
	    }

	    this.setPriceTotal(priceUnit.multiply(new BigDecimal(amount)));
	}
	
}
