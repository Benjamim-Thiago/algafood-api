package br.com.btsoftware.algafood.api.controller.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
	private LocalDateTime dateHour;
	private String message;
}
