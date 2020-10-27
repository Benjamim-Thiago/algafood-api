package br.com.btsoftware.algafood.domain.service;

import java.util.List;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;
import br.com.btsoftware.algafood.domain.model.dto.DailySales;

public interface SaleQueryService {
	List<DailySales> findDailySales(DailySalesFilter filter, String timeOffset);
}
