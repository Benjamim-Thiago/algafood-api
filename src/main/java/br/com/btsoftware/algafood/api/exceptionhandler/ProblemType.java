package br.com.btsoftware.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	INVALID_DATA("/invalid-data", "Dados inválidos"),
	SYSTEM_ERROR("/system-error", "Erro de sistema"),
	INVALID_PARAM("/invalid-param-url", "Parametro do tipo inválido"),
	INCOMPATIBLE_BODY("/incompatible-body", "Corpo da mensagem JSON com erro"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
	ENTITY_IN_USE("/entity-in-use", "Entidade em uso"),
	BUSINESS_ERROR("/business-error", "Violação de regra de negócio"),
	ACCESS_DENIED("/access-denied", "Acesso negado");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://alga.com.br" + path;
		this.title = title;
	}
	
	
}
