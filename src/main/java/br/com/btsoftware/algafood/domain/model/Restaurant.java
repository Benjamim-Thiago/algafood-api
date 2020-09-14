package br.com.btsoftware.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

import br.com.btsoftware.algafood.core.validation.ValueZeroIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade Restaurante
 */
@Entity
@Table(name = "restaurants")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@ValueZeroIncludeDescription(valueField = "deliveryFee", descriptionField = "name", descriptionRequired = "Frete Grátis")
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	// @DeliveryFee
	// @Multiple(number = 5)
	@Column(name = "delivery_fee", nullable = false)
	private BigDecimal deliveryFee;

	// Properties("hibernateLazyInitializer")
	//
	// @Valid
	// @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@Embedded
	private Address address;

	private Boolean open = Boolean.FALSE;

	private Boolean active = Boolean.TRUE;

	@ManyToMany()
	@JoinTable(name = "restaurant_payment_mode", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_mode_id"))
	private Set<PaymentMode> paymentsMode = new HashSet<>();

	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurant_user_corporate_officer", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> corporate_officers = new HashSet<>();

	@CreationTimestamp
	private OffsetDateTime created;

	@UpdateTimestamp()
	private OffsetDateTime updated;

	public void activate() {
		setActive(true);
	}

	public void inactivate() {
		setActive(false);
	}

	public void open() {
		setOpen(true);
	}

	public void close() {
		setOpen(false);
	}

	public Boolean removePaymentMode(PaymentMode paymentMode) {
		return getPaymentsMode().remove(paymentMode);
	}

	public boolean addPaymentMode(PaymentMode paymentMode) {
		return getPaymentsMode().add(paymentMode);
	}
	
	public boolean removeCorporateOfficer(User user) {
	    return getCorporate_officers().remove(user);
	}

	public boolean addCorporateOfficer(User user) {
	    return getCorporate_officers().add(user);
	}
}
