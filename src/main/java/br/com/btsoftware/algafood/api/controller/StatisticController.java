package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;
import br.com.btsoftware.algafood.domain.model.dto.DailySales;
import br.com.btsoftware.algafood.domain.service.SaleQueryService;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
	
	@Autowired
	private SaleQueryService saleQueryService;
	
	@GetMapping("/daily-sales")
	public List<DailySales> consultarVendasDiarias(DailySalesFilter filter) {
		
		
		return saleQueryService.findDailySales(filter);
	}

}
