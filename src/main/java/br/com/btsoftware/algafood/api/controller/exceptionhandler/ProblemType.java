package br.com.btsoftware.algafood.api.controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	SYSTEM_ERROR("/system-error", "Erro de sistema"),
	INVALID_PARAM("/invalid-param-url", "Parametro do tipo inválido"),
	INCOMPATIBLE_BODY("/incompatible-body", "Corpo da mensagem JSON com erro"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
	ENTITY_IN_USE("/entity-in-use", "Entidade em uso"),
	BUSINESS_ERROR("/business-error", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://alga.com.br" + path;
		this.title = title;
	}
	
	
}
