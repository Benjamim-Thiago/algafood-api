package br.com.btsoftware.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.btsoftware.algafood.domain.model.Restaurant;

public abstract class KitchenMixin {
	@JsonIgnore
	private List<Restaurant> restaurants = new ArrayList<>(); 
}
