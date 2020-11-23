package br.com.btsoftware.algafood.domain.repository;

import br.com.btsoftware.algafood.domain.model.PhotoProduct;

public interface ProductRepositoryQueries {
	PhotoProduct save(PhotoProduct photoProduct);
	
	void delete(PhotoProduct photoProduct);
}
