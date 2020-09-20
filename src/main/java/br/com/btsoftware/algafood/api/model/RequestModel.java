package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import br.com.btsoftware.algafood.domain.model.RequestItem;
import br.com.btsoftware.algafood.domain.model.enumerable.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModel {
	
	private Long id;
	private RestaurantResumeModel restaurant;
	private UserModel client;
	private AddressModel deliveryAddress;
	private PaymentModeModel paymentMode;
	private BigDecimal subTotal;
	private BigDecimal deliveryFee;
	private BigDecimal valueTotal;
	private OffsetDateTime created;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;
	private RequestStatus requestStatus;
	private List<RequestItem> items;
}
