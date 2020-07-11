package br.com.btsoftware.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.btsoftware.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade Cozinha
 */

@JsonRootName("gatronomia")
@Entity
@Table(name = "kitchens")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Kitchen {

	@NotNull(groups = Groups.KitchenId.class)	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "kitchen")
	private List<Restaurant> restaurants = new ArrayList<>(); 

}
