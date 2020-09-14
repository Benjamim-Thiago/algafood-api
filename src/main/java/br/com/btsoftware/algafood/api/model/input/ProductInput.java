package br.com.btsoftware.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {
	@NotBlank
    private String name;
    
    @NotBlank
    private String description;
    
    @PositiveOrZero
    @NotNull
    private BigDecimal price;
    	
}
