package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.btsoftware.algafood.domain.event.RequestCanceledEvent;
import br.com.btsoftware.algafood.domain.event.RequestConfirmedEvent;
import br.com.btsoftware.algafood.domain.exception.BusinessException;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//AbstractAggregateRoot Laçar eventos
public class Request extends AbstractAggregateRoot<Request>{
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
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
	
	
	public void confirme() {
		setRequestStatus(RequestStatus.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
		
		registerEvent(new RequestConfirmedEvent(this));
	}
	
	public void deliver() {
		setRequestStatus(RequestStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		setRequestStatus(RequestStatus.CANCELED);
		setCancellationDate(OffsetDateTime.now());
		
		registerEvent(new RequestCanceledEvent(this));
	}
	
	private void setRequestStatus(RequestStatus newStatus) {
		if (getRequestStatus().canNotChangeTo(newStatus)) {
			throw new BusinessException(
					String.format("Status do pedido %s não pode ser alterado de %s para %s",
							getCode(), getRequestStatus().getDescription(), 
							newStatus.getDescription()));
		}
		
		this.requestStatus = newStatus;
	}
	
	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}
}
