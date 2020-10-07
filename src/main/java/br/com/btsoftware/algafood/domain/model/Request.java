package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.btsoftware.algafood.domain.model.enumerable.RequestStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 * 
 * Entidade => Pedidos
 *
 */
@Entity
@Table(name = "requests")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Request {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private User client;
		
	@Embedded
    private Address deliveryAddress;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_mode_id")
	private PaymentMode paymentMode;

	@Column(name = "sub_total")
	private BigDecimal subTotal;
	
	@Column(name = "delivery_fee")
	private BigDecimal deliveryFee;
	
	@Column(name = "value_total")
	private BigDecimal valueTotal;
	
	@CreationTimestamp
	private OffsetDateTime created;

	@Column(name = "confirmation_date")
	private OffsetDateTime confirmationDate;
	
	@Column(name = "cancellation_date")
	private OffsetDateTime cancellationDate;
	
	@Column(name = "delivery_date")
	private OffsetDateTime deliveryDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private RequestStatus requestStatus = RequestStatus.CREATED;
	
	@JsonIgnore
	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
	private List<RequestItem> items = new ArrayList<>();
	
	public void calculatePriceTotal() {
		getItems().forEach(RequestItem::calculatePriceTotal);
	    
	    this.subTotal = getItems().stream()
	        .map(item -> item.getPriceTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valueTotal = this.subTotal.add(this.deliveryFee);
	    
	}
	
	/***
	 * Define frete
	 */
	public void defineShipping() {
		setDeliveryFee(getRestaurant().getDeliveryFee());
	}

	/***
	 *  Atribuição do pedido aos itens
	 */
	public void assignRequestToItems() {
	    getItems().forEach(item -> item.setRequest(this));
	}
}
