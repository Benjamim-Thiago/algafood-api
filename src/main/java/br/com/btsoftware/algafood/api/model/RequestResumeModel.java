package br.com.btsoftware.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.btsoftware.algafood.domain.model.enumerable.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResumeModel {
	
	private Long id;
	private RestaurantResumeModel restaurant;
	private UserModel client;
	private BigDecimal subTotal;
	private BigDecimal deliveryFee;
	private BigDecimal valueTotal;
	private OffsetDateTime created;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime cancellationDate;
	private OffsetDateTime deliveryDate;
	private RequestStatus requestStatus;
}
