package br.com.btsoftware.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.btsoftware.algafood.domain.model.PhotoProduct;
import br.com.btsoftware.algafood.domain.model.Product;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {
	
	@Query("from Product where restaurant.id = :restaurant and id = :product")
	Optional<Product> findByRestaurantAndProductId(@Param("restaurant") Long restauranteId,
			@Param("product") Long productId);

	List<Product> findAllProductsByRestaurant(Restaurant restaurant);

	@Query("from Product p where p.active = true and p.restaurant = :restaurant")
	List<Product> findActivesProductsByRestaurant(Restaurant restaurant);

	@Query("select ph from PhotoProduct ph join ph.product p "
			+ "where p.restaurant.id = :restaurantId and ph.product.id = :productId")
	Optional<PhotoProduct> findFotoById(Long restaurantId, Long productId);
}
