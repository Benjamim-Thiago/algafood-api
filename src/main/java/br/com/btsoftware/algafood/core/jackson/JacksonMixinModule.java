package br.com.btsoftware.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.btsoftware.algafood.api.model.mixin.KitchenMixin;
import br.com.btsoftware.algafood.api.model.mixin.RestaurantMixin;
import br.com.btsoftware.algafood.domain.model.Kitchen;
import br.com.btsoftware.algafood.domain.model.Restaurant;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
		setMixInAnnotation(Kitchen.class, KitchenMixin.class);
	}
}
