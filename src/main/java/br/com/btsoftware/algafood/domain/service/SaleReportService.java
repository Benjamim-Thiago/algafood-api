package br.com.btsoftware.algafood.domain.service;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;

public interface SaleReportService {
	byte[] findDailySales(DailySalesFilter filter, String timeOffset);
}
