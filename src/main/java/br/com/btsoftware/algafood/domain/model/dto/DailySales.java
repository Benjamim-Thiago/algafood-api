package br.com.btsoftware.algafood.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DailySales {
	private String date;
	private Long totalSales;
	private BigDecimal totalBilled;
	
}
