package br.com.btsoftware.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.filter.DailySalesFilter;
import br.com.btsoftware.algafood.domain.service.SaleQueryService;
import br.com.btsoftware.algafood.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
public class PdfSaleReportServiceImpl implements SaleReportService {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private SaleQueryService saleQueryService;
	
	@Override
	public byte[] findDailySales(DailySalesFilter filter, String timeOffset) {
		// /reports/daily-sales.jasper
		try {
		var inputStream =  this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
		
		var params = new HashMap<String, Object>();
		params.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		var dailySales = saleQueryService.findDailySales(filter, timeOffset);
		var dataSource = new JRBeanCollectionDataSource(dailySales);
		
		var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
	
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}
