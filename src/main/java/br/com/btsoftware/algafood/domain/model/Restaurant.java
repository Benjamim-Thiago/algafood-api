package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade Restaurante
 */
@Entity
@Table(name = "restaurants")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "delivery_fee")
	private BigDecimal deliveryFee;

	//@JsonIgnoreProperties("hibernateLazyInitializer")
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@JsonIgnore
	@Embedded
	private Address address;
	
	//@JsonIgnore
	@ManyToMany()
	@JoinTable(name = "restaurant_payment_mode", 
			   joinColumns = @JoinColumn(name = "restaurant_id"), 
			   inverseJoinColumns = @JoinColumn(name = "payment_mode_id"))
	private List<PaymentMode> paymentsMode = new ArrayList<>();
		
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
	
	@CreationTimestamp
	private LocalDate created;
	
	@UpdateTimestamp()
	private LocalDate updated;
}
