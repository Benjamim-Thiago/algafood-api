package br.com.btsoftware.algafood.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

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

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

}
