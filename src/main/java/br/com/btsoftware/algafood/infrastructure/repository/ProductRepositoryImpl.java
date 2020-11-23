package br.com.btsoftware.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional
	public PhotoProduct save(PhotoProduct photoProduct) {
		return manager.merge(photoProduct);
	}

	@Override
	public void delete(PhotoProduct photoProduct) {		
		manager.remove(photoProduct);
	}

}
