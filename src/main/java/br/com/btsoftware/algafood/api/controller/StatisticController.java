package br.com.btsoftware.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;
import br.com.btsoftware.algafood.domain.model.dto.DailySales;
import br.com.btsoftware.algafood.domain.service.SaleQueryService;
import br.com.btsoftware.algafood.domain.service.SaleReportService;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

	@Autowired
	private SaleQueryService saleQueryService;

	@Autowired
	private SaleReportService saleReportService;

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySales> consultarVendasDiarias(DailySalesFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
				
		return saleQueryService.findDailySales(filter, timeOffset);
	}

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(DailySalesFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		byte[] bytesPdf = saleReportService.findDailySales(filter, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
	}

}
