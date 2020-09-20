package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestItemModel {
	private Integer productId;
	private String productName;
	private Integer amount;
	private BigDecimal price;
	private BigDecimal priceTotal;
	private String comment;
}
