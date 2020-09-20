package br.com.btsoftware.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestInput {
	
	@Valid
    @NotNull
    private RestaurantIdInput restaurant;
    
    @Valid
    @NotNull
    private AddressInput deliveryAddress;
    
    @Valid
    @NotNull
    private PaymentModeIdInput paymentMode;
    
    @Valid
    @Size(min = 1)
    @NotNull
    private List<RequestItemInput> items;
}
