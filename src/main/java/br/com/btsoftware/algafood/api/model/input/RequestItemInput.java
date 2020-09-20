package br.com.btsoftware.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestItemInput {
	
	@NotNull
    private Long productId;
    
    @NotNull
    @PositiveOrZero
    private Integer amount;
    
    private String comment;   
}
